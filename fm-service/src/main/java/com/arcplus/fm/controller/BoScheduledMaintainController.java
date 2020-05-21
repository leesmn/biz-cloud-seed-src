package com.arcplus.fm.controller;

import com.arcplus.fm.entity.dto.BaseResponse;
import com.arcplus.fm.entity.dto.PageEntity;
import com.arcplus.fm.entity.vo.BoScheduledMaintainMonthVo;
import com.arcplus.fm.entity.vo.ScheduledMaintainStaticVo;
import com.arcplus.fm.entity.BoScheduledMaintain;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.BoScheduledMaintainService;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("boScheduledMaintain")
public class BoScheduledMaintainController  {
    @Autowired
    BoScheduledMaintainService instBoScheduledMaintainService;

    @PostMapping(value = "/add")
    public Result addBoScheduledMaintain(@RequestBody BoScheduledMaintain entity) throws Exception {
        return ResultGenerator.genSuccessResult(instBoScheduledMaintainService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateBoScheduledMaintain(@RequestBody BoScheduledMaintain entity) throws Exception {
        return ResultGenerator.genSuccessResult(instBoScheduledMaintainService.updateByGroupUuid(entity));
    }

    @PostMapping(value = "/del/{groupUuid}")
    public Result delBoScheduledMaintainService(@PathVariable(value="groupUuid") String groupUuid) throws Exception {
        return ResultGenerator.genSuccessResult(instBoScheduledMaintainService.deleteByGroupUuid(groupUuid));
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<BoScheduledMaintain> reslut = instBoScheduledMaintainService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }

    @GetMapping(value = "/detail/{id}")
    public Result detailById(@PathVariable(value="id") Integer id) throws Exception {
        return ResultGenerator.genSuccessResult( instBoScheduledMaintainService.getDetail(id));
    }

    @GetMapping(value = "/detailByUuid/{groupUuid}")
    public Result detailByUuid(@PathVariable(value="groupUuid") String groupUuid) throws Exception {
        return ResultGenerator.genSuccessResult( instBoScheduledMaintainService.getByGroupUuid(groupUuid));
    }

    @GetMapping(value = "/month")
    public Result selectMonth(@RequestParam(value="year") int year,@RequestParam(value="month") int month) throws Exception {
        Map<String,List<BoScheduledMaintainMonthVo>> reslut = instBoScheduledMaintainService.selectMonth(year,month);
        return ResultGenerator.genSuccessResult(reslut);
    }

    @PostMapping("/search")
    public BaseResponse selectAllPage(
            @RequestBody PageEntity<Map<String, Object>> obj
    ) {

        LocalDateTime dateTime=LocalDateTime.now();

        Map<String, Object> param = new HashMap<>();
        param.put("page", obj.getPageIndex());
        param.put("limit", obj.getPageSize());
        if (!CollectionUtils.isEmpty(obj.getCheckModelData())) {
            for (Map.Entry<String, Object> item : obj.getCheckModelData().entrySet()) {
                if ("dealStatus".equals(item.getKey())) {
                    param.put(item.getKey(),  item.getValue());
                    continue;
                }
                if ("year".equals(item.getKey())) {
                    dateTime= dateTime.withYear((int)item.getValue());
                    continue;
                }
                if ("month".equals(item.getKey())) {
                    dateTime= dateTime.withMonth((int)item.getValue());
                    continue;
                }
                param.put(item.getKey(), "$like." + item.getValue());
            }
        }

        param.put("fromDate", dateTime.withDayOfMonth(1));
        param.put("toDate", dateTime.withDayOfMonth(1).plusMonths(1).plusDays(-1));


        if (obj.getOrderModelData().length > 0 && obj.getOrderModelData()[0].getMethod() != null) {
            List<String> list = new ArrayList<>();
            for (PageEntity.OrderObj x : obj.getOrderModelData()) {
                String end = x.getField() + "." + x.getMethod().replaceAll("end", "");
                list.add(end);
            }
            String orderStr = StringUtils.arrayToCommaDelimitedString(
                    list.toArray()
            );
            if (!StringUtils.isEmpty(orderStr)) {
                param.put("orderBy", orderStr);
            }
        }

        // Pagable
        Integer page = Integer.parseInt(param.getOrDefault("page", "1").toString());
        Integer limit = Integer.parseInt(param.getOrDefault("limit", "100").toString());
        param.remove("page");
        param.remove("limit");


        Map<String, String[]> inSearch = new HashMap<>();
        Map<String, String> fuzzySearch = new HashMap<>();

        String[] keys = new String[]{};
        keys = param.keySet().toArray(keys);
        for (String key : keys) {

            if (StringUtils.isEmpty(param.getOrDefault(key, "").toString())) {
                param.remove(key);
                continue;
            }
            String value = param.get(key).toString();

            // $in
            if (value.startsWith("$in.")) {
                String sqlCloumnN = convertModelColumn2SqlColumn(key);
                if (null != sqlCloumnN) {
                    inSearch.put(sqlCloumnN, value.substring(4).split(","));
                }
                param.remove(key);
                continue;
            }

            // $like
            if (value.startsWith("$like.")) {
                String sqlCloumnN = convertModelColumn2SqlColumn(key);
                if (null != sqlCloumnN) {
                    fuzzySearch.put(sqlCloumnN, String.format("%%%s%%", value.substring(6)));
                }
                param.remove(key);
                continue;
            }
        }
        if (!fuzzySearch.isEmpty()) {
            param.put("fuzzySearch", fuzzySearch);
        }
        if (!inSearch.isEmpty()) {
            param.put("inSearch", inSearch);
        }

        // Order
        if (param.containsKey("orderBy")) {
            param.replace(
                    "orderBy",
                    String.join(
                            ",",
                            Arrays.stream(param.get("orderBy").toString().split(","))
                                    .map((e -> {
                                        String[] kv = e.split("\\.");
                                        kv[0] = convertModelColumn2SqlOrderColumn(kv[0]);
                                        return String.join(" ", kv);
                                    }))
                                    .toArray(String[]::new)));
        } else {
            param.put("orderBy", "sm.scheduled_id desc");
        }
        PageInfo<ScheduledMaintainStaticVo> reslut = instBoScheduledMaintainService.search(param, page, limit);
        return ResultGenerator.genSucessPageResult(reslut);
    }


    private String convertModelColumn2SqlColumn(String modC) {

        if ("scheduledId".equals(modC))
            return "sm.scheduled_id";
        if ("maintainContent".equals(modC))
            return "sm.maintain_content";
        if ("maintainDescribe".equals(modC))
            return "sm.maintain_describe";
        if ("maintainStartAt".equals(modC))
            return "sm.maintain_start_at";

        if ("scheduledRule".equals(modC))
            return "sm.scheduled_rule";

        return null;
    }

    private String convertModelColumn2SqlOrderColumn(String modC) {

        if ("scheduledId".equals(modC))
            return "sm.scheduled_id";
        if ("maintainContent".equals(modC))
            return "CONVERT(sm.maintain_content using gbk)";
        if ("maintainDescribe".equals(modC))
            return "CONVERT(sm.maintain_describe using gbk)";
        if ("maintainStartAt".equals(modC))
            return "sm.maintain_start_at";

        if ("scheduledRule".equals(modC))
            return "sm.scheduled_rule";

        return null;
    }


}