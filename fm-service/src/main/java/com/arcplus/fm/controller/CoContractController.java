package com.arcplus.fm.controller;

import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultCode;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.entity.dto.BaseResponse;
import com.arcplus.fm.entity.dto.PageEntity;
import com.arcplus.fm.entity.vo.ContractVo;
import com.arcplus.fm.entity.CoContract;
import com.arcplus.fm.service.CoContractService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("coContract")
public class CoContractController {
    @Autowired
    CoContractService instCoContractService;

    @PostMapping(value = "/add")
    public Result addCoContract(
            @Valid @RequestBody CoContract entity,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultGenerator.genFailResult(
                    StringUtils.arrayToCommaDelimitedString(
                            bindingResult.getAllErrors()
                                    .stream()
                                    .map(e -> e.getDefaultMessage())
                                    .toArray()),
                    ResultCode.FAIL);
        }

        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        Authentication authenticationToken = authentication.getUserAuthentication();
        Map map = (Map) ((Map) authenticationToken.getDetails()).get("principal");

        int userId = (int) map.get("id");
        String username = map.get("username").toString();

        entity.setCreateBy(userId);
        entity.setCreatorName(username);

        return ResultGenerator.genSuccessResult(instCoContractService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateCoContract(@RequestBody CoContract entity) throws Exception {
        instCoContractService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delCoContractService(@PathVariable("id") int id) throws Exception {
        instCoContractService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit) throws Exception {
        PageInfo<CoContract> reslut = instCoContractService.selectAllPage(page, limit);
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
                    param.put(item.getKey(), LocalDateTime.parse(item.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    continue;
                }
                if ("contractStatus".equals(item.getKey())) {
                    param.put(item.getKey(), item.getValue().toString());
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
            param.put("orderBy", "ct.contract_id desc");
        }
        PageInfo<ContractVo> reslut = instCoContractService.search(param, page, limit);
        return ResultGenerator.genSucessPageResult(reslut);
    }


    private String convertModelColumn2SqlColumn(String modC) {
        if ("contractId".equals(modC))
            return "ct.contract_id";
        if ("contractName".equals(modC))
            return "ct.contract_name";
        if ("contractCode".equals(modC))
            return "ct.contract_code";
        if ("contractTargetName".equals(modC))
            return "org.org_name";

        if ("fromDate".equals(modC))
            return "ct.from_date";
        if ("contractArea".equals(modC))
            return "ct.contract_area";
        if ("contractCost".equals(modC))
            return "ct.contract_cost";
        if ("contractStatus".equals(modC))
            return "ct.contract_status";
        if ("creatorName".equals(modC))
            return "ct.creator_name";
        return null;
    }

    private String convertModelColumn2SqlOrderColumn(String modC) {

        if ("contractId".equals(modC))
            return "ct.contract_id";
        if ("contractName".equals(modC))
            return "CONVERT(ct.contract_name using gbk)";
        if ("contractCode".equals(modC))
            return "ct.contract_code";
        if ("contractTargetName".equals(modC))
            return "CONVERT(org.org_name using gbk)";

        if ("fromDate".equals(modC))
            return "ct.from_date";
        if ("contractArea".equals(modC))
            return "ct.contract_area";
        if ("contractCost".equals(modC))
            return "ct.contract_cost";
        if ("contractStatus".equals(modC))
            return "ct.contract_status";
        if ("creatorName".equals(modC))
            return "ct.creator_name";

        if ("settlementMethodName".equals(modC))
            return "ct.settlement_method";


        return null;
    }
}