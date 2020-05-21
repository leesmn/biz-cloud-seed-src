package com.arcplus.fm.controller;

import com.arcplus.fm.common.ResultCode;
import com.arcplus.fm.entity.dto.BaseResponse;
import com.arcplus.fm.entity.dto.PageEntity;
import com.arcplus.fm.entity.dto.WorkorderLiteDto;
import com.arcplus.fm.entity.vo.RepairVo;
import com.arcplus.fm.entity.vo.RepairWoVo;
import com.arcplus.fm.entity.BoRepair;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.BoRepairService;
import com.arcplus.fm.service.BoWorkorderService;
import com.arcplus.fm.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("boRepair")
public class BoRepairController {
    @Autowired
    BoRepairService instBoRepairService;

    @Autowired
    BoWorkorderService instBoWorkorderService;

    @PostMapping(value = "/add")
    public Result addBoRepair(@RequestBody BoRepair entity) throws Exception {
        return ResultGenerator.genSuccessResult(instBoRepairService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateBoRepair(@RequestBody BoRepair entity) throws Exception {
        instBoRepairService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delBoRepairService(@PathVariable("id") int id) throws Exception {
        instBoRepairService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit) throws Exception {
        PageInfo<BoRepair> reslut = instBoRepairService.selectAllPage(page, limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }

    @GetMapping(value = "/{id}")
    public Result getById(@PathVariable("id") int id) throws Exception {
        return ResultGenerator.genSuccessResult(instBoRepairService.getById(id));
    }

    @GetMapping(value = "/bycode")
    public Result getByCode(@RequestParam("code") String code) throws Exception {
        return ResultGenerator.genSuccessResult(instBoRepairService.getRepairByCode(code));
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
            param.put("orderBy", "repair.repair_id desc");
        }
        PageInfo<RepairVo> reslut = instBoRepairService.search(param, page, limit);
        return ResultGenerator.genSucessPageResult(reslut);
    }


    private String convertModelColumn2SqlColumn(String modC) {
        if ("repairId".equals(modC))
            return "repair.repair_id";
        if ("repairCode".equals(modC))
            return "repair.repair_code";
        if ("ownerName".equals(modC))
            return "repair.owner_name";
        if ("contactPhone".equals(modC))
            return "repair.contact_phone";

        if ("failureTypeName".equals(modC))
            return "fail.failure_name";
        if ("repairDescribe".equals(modC))
            return "repair.repair_describe";
        if ("dealWith".equals(modC))
            return "repair.deal_with";

        if ("incidentBuildingName".equals(modC))
            return "repair.incident_building_name";
        if ("incidentFloorName".equals(modC))
            return "repair.incident_floor_name";


        return null;
    }

    private String convertModelColumn2SqlOrderColumn(String modC) {

        if ("repairId".equals(modC))
            return "repair.repair_id";
        if ("repairCode".equals(modC))
            return "repair.repair_code";
        if ("ownerName".equals(modC))
            return "CONVERT(repair.owner_name using gbk)";
        if ("contactPhone".equals(modC))
            return "repair.contact_phone";

        if ("failureTypeName".equals(modC))
            return "CONVERT(fail.failure_name using gbk)";
        if ("repairDescribe".equals(modC))
            return "CONVERT(repair.repair_describe using gbk)";
        if ("dealWith".equals(modC))
            return "CONVERT(repair.deal_with using gbk)";

        if ("incidentBuildingName".equals(modC))
            return "CONVERT(repair.incident_building_name using gbk)";
        if ("incidentFloorName".equals(modC))
            return "CONVERT(repair.incident_floor_name using gbk)";

        return null;
    }

    /*
    通过楼层id获取，报修列表(并通过工单编号，判断是否已经转工单处理了)
     */
    @GetMapping(value = "/floor")
    public Result selectByFloor(@RequestParam(value = "buildId") int buildId, @RequestParam(value = "floorId") int floorId, @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit) throws Exception {
        PageInfo<RepairWoVo> reslut = instBoRepairService.selectRepairWizWo(page, limit,buildId,floorId);
        return ResultGenerator.genSuccessPageResult(reslut);
    }

    /*
    获取楼层新报修数量
     */
    @GetMapping(value = "/num/floor")
    public Result selectTotal(@RequestParam(value = "buildId") int buildId) throws Exception {
        return ResultGenerator.genSuccessResult(instBoRepairService.countByFloor(buildId));
    }


    @PostMapping(value = "/deal/wo")
    public Result repairDealByWorkorder(@RequestBody WorkorderLiteDto workorderLiteDto) throws Exception {
        Map<String,Object> userMap = AuthUtil.getCurrentUser();
        if(userMap==null){
            return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
        }
        int uid =  Integer.parseInt(userMap.get("id").toString());
        String uName = userMap.get("username").toString();
        if(instBoWorkorderService.processWorkOrder(workorderLiteDto,1,uid,uName)>0){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("未完成处理", ResultCode.FAIL);
        }
    }

    @PostMapping(value = "/deal/direct")
    public Result repairDealRirect(@RequestBody BoRepair boRepair) throws Exception {
        if(instBoRepairService.updateByCode(boRepair)>0){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("未完成处理", ResultCode.FAIL);
        }
    }
}