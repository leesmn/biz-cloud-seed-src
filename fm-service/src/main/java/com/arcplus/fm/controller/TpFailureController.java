package com.arcplus.fm.controller;

import com.arcplus.fm.entity.dto.BaseResponse;
import com.arcplus.fm.entity.dto.PageEntity;
import com.arcplus.fm.entity.TpFailure;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.TpFailureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("tpFailure")
public class TpFailureController  {
    @Autowired
    TpFailureService instTpFailureService;

    @PostMapping(value = "/add")
    public Result addTpFailure(@RequestBody TpFailure entity) throws Exception {
        return ResultGenerator.genSuccessResult(instTpFailureService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateTpFailure(@RequestBody TpFailure entity) throws Exception {
        instTpFailureService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delTpFailureService(@PathVariable("id") int id) throws Exception {
        instTpFailureService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<TpFailure> reslut = instTpFailureService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }

    @GetMapping(value = "/{id}")
    public Result getById(@PathVariable("id") int id) throws Exception {
        return ResultGenerator.genSuccessResult(instTpFailureService.getById(id));
    }

    @PostMapping("/search")
    public BaseResponse selectAllPage(
            @RequestBody PageEntity<Map<String, Object>> obj
    ) {

        Map<String, Object> param = new HashMap<>();
        param.put("page", obj.getPageIndex());
        param.put("limit", obj.getPageSize());
        if (!CollectionUtils.isEmpty(obj.getCheckModelData())) {
            for (Map.Entry<String, Object> item : obj.getCheckModelData().entrySet()) {
                param.put(item.getKey(), "$like." + item.getValue());
            }
        }
        if (obj.getOrderModelData().length > 0&&obj.getOrderModelData()[0].getMethod()!=null) {
            String orderStr = StringUtils.arrayToCommaDelimitedString(
                    Arrays.stream(obj.getOrderModelData())
                            .map(x -> x.getField() + "." + x.getMethod().replaceAll("end", "")).toArray()
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
            param.put("orderBy", "fail.index desc");
        }
        PageInfo<TpFailure> reslut = instTpFailureService.search(param, page, limit);
        return ResultGenerator.genSucessPageResult(reslut);
    }

    private String convertModelColumn2SqlColumn(String modC) {
        if ("failureId".equals(modC))
            return "fail.failure_id";
        if ("failureName".equals(modC))
            return "fail.failure_name";
        if ("failureDescribe".equals(modC))
            return "fail.failure_describe";

        return null;
    }

    private String convertModelColumn2SqlOrderColumn(String modC) {

        if ("failureId".equals(modC))
            return "fail.failure_id";
        if ("failureName".equals(modC))
            return "CONVERT(fail.failure_name using gbk)";
        if ("failureDescribe".equals(modC))
            return "CONVERT(fail.failure_describe using gbk)";

        return null;
    }
}