package com.arcplus.fm.controller;

import com.arcplus.fm.entity.dto.BaseResponse;
import com.arcplus.fm.entity.dto.PageEntity;
import com.arcplus.fm.entity.vo.BillVo;
import com.arcplus.fm.entity.CoBill;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.CoBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("coBill")
public class CoBillController  {
    @Autowired
    CoBillService instCoBillService;

    @PostMapping(value = "/add")
    public Result addCoBill(@RequestBody CoBill entity) throws Exception {
        return ResultGenerator.genSuccessResult(instCoBillService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateCoBill(@RequestBody CoBill entity) throws Exception {
        instCoBillService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delCoBillService(@PathVariable("id") int id) throws Exception {
        instCoBillService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<CoBill> reslut = instCoBillService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
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
                if ("fromDate".equals(item.getKey()) || "toDate".equals(item.getKey())) {
                    param.put(item.getKey(),  LocalDateTime.parse(item.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    continue;
                }
                param.put(item.getKey(), "$like." + item.getValue());
            }
        }
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
            param.put("orderBy", "bill.bill_period desc");
        }
        PageInfo<BillVo> reslut = instCoBillService.search(param, page, limit);
        return ResultGenerator.genSucessPageResult(reslut);
    }


    private String convertModelColumn2SqlColumn(String modC) {
        if ("billId".equals(modC))
            return "bill.bill_id";
        if ("billPeriod".equals(modC))
            return "bill.bill_period";
        if ("customerName".equals(modC))
            return "org.org_name";
        if ("billStatus".equals(modC))
            return "bill.invoice_status";
        if ("remark".equals(modC))
            return "bill.remark";

        return null;
    }

    private String convertModelColumn2SqlOrderColumn(String modC) {

        if ("billId".equals(modC))
            return "bill.bill_id";
        if ("billPeriod".equals(modC))
            return "bill.bill_period";
        if ("customerName".equals(modC))
            return "CONVERT(org.org_name using gbk)";
        if ("billStatus".equals(modC))
            return "bill.invoice_status";

        if ("remark".equals(modC))
            return "CONVERT(bill.remark using gbk)";

        return null;
    }
}