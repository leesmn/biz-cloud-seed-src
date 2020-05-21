package com.arcplus.fm.controller;

import com.arcplus.fm.common.ResultCode;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.entity.dto.BaseResponse;
import com.arcplus.fm.entity.dto.PageEntity;
import com.arcplus.fm.entity.vo.CoVehicleVo;
import com.arcplus.fm.entity.CoVehicle;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.mapper.CoVehicleMapper;
import com.arcplus.fm.service.CoVehicleService;
import com.arcplus.fm.util.AuthUtil;
import com.arcplus.fm.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("coVehicle")
public class CoVehicleController  {
    @Autowired
    CoVehicleService instCoVehicleService;
    @Autowired
    CoVehicleMapper instCoVehicleMapper;

    @PostMapping(value = "/add")
    public Result addCoVehicle(@RequestBody CoVehicle entity) throws Exception {
        //检查重复
        List<CoVehicle> dbmodels =  instCoVehicleMapper.checkIsExist(entity.getCarNum());
        if(dbmodels != null && !dbmodels.isEmpty()) {
            return ResultGenerator.genFailResult("车牌号码已经存在", ResultCode.FAIL);
        }

        entity.setCreateBy(AuthUtil.GetCurrentUID());
        return ResultGenerator.genSuccessResult(instCoVehicleService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateCoVehicle(@RequestBody CoVehicle entity) throws Exception {

        List<CoVehicle> dbmodels = instCoVehicleMapper.checkIsExist(entity.getCarNum());
        if (dbmodels != null && !dbmodels.isEmpty()) {
            {
                for (CoVehicle item : dbmodels) {
                    if ("N".equals(item.getDataStatus())
                            && !entity.getVehicleId().equals(item.getVehicleId())) {
                        return ResultGenerator.genFailResult("车牌号码已经存在", ResultCode.FAIL);
                    }
                }
            }
        }
        entity.setLastModifierUid(AuthUtil.GetCurrentUID());
        entity.setLastModifierTime(new Date());
        instCoVehicleService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    /***
     * 分配车位
     * @param entity
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/givespace")
    public Result givespaceCoVehicle(@RequestBody CoVehicle entity) throws Exception {

        //检查车位是否已经被其他车辆占用
        int counts = instCoVehicleMapper.checkIsUsed(entity.getVehicleId(), entity.getBuildingId(), entity.getFloorId(), entity.getSpace());
        if (counts > 0) {
            return ResultGenerator.genFailResult("当前车位已经被其他车辆占用", ResultCode.FAIL);
        }

        CoVehicle nCoVehicle = new CoVehicle();
        nCoVehicle.setSpace(entity.getSpace());
        nCoVehicle.setSpaceName(entity.getSpaceName());
        nCoVehicle.setBuildingId(entity.getBuildingId());
        nCoVehicle.setBuildingName(entity.getBuildingName());
        nCoVehicle.setFloorId(entity.getFloorId());
        nCoVehicle.setFloorName(entity.getFloorName());
        nCoVehicle.setVehicleId(entity.getVehicleId());
        nCoVehicle.setLastModifierUid(AuthUtil.GetCurrentUID());
        nCoVehicle.setLastModifierTime(new Date());
        nCoVehicle.setDealStatus( EDealStatus.P);

        instCoVehicleService.update(nCoVehicle);
        return ResultGenerator.genSuccessResult();
    }

    /***
     * 清除车位
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/clearspace/{id}")
    public Result clearspaceCoVehicle(@PathVariable("id") int id) throws Exception {


        CoVehicle nCoVehicle = new CoVehicle();
        nCoVehicle.setSpace("");
        nCoVehicle.setSpaceName("");
        nCoVehicle.setBuildingId(0);
        nCoVehicle.setBuildingName("");
        nCoVehicle.setFloorId(0);
        nCoVehicle.setFloorName("");
        nCoVehicle.setVehicleId(id);
        nCoVehicle.setDealStatus( EDealStatus.W);
        nCoVehicle.setLastModifierUid(AuthUtil.GetCurrentUID());
        nCoVehicle.setLastModifierTime(new Date());

        instCoVehicleService.update(nCoVehicle);
        return ResultGenerator.genSuccessResult();
    }



    @PostMapping(value = "/del/{id}")
    public Result delCoVehicleService(@PathVariable("id") int id) throws Exception {

        instCoVehicleService.delete(id,AuthUtil.GetCurrentUID());
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<CoVehicle> reslut = instCoVehicleService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }


    @GetMapping(value = "/{id}")
    public Result getById(@PathVariable("id") int id) throws Exception {
        return ResultGenerator.genSuccessResult(instCoVehicleService.getById(id));
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
                String sqlCloumnN = StringHelper.HumpToUnderline(key);
                if (null != sqlCloumnN) {
                    inSearch.put(sqlCloumnN, value.substring(4).split(","));
                }
                param.remove(key);
                continue;
            }

            // $like
            if (value.startsWith("$like.")) {
                String sqlCloumnN = StringHelper.HumpToUnderline(key);
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
                                        kv[0] = StringHelper.HumpToUnderline(kv[0]);
                                        return String.join(" ", kv);
                                    }))
                                    .toArray(String[]::new)));
        } else {
            param.put("orderBy", "vehicle_id desc");
        }
        PageInfo<CoVehicleVo> reslut = instCoVehicleService.search(param, page, limit);
        return ResultGenerator.genSucessPageResult(reslut);
    }




    /*
    通过楼层id 获取占用车位列表
     */
    @GetMapping(value = "/floor")
    public Result selectCoVehicleVo(@RequestParam(value = "buildingId") int buildingId, @RequestParam(value = "floorId") int floorId, @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit) throws Exception {
        PageInfo<CoVehicleVo> reslut = instCoVehicleService.selectCoVehicleVo(page, limit,buildingId,floorId);
        return ResultGenerator.genSuccessPageResult(reslut);
    }



    /*
    通过楼层id 查找当前楼层已经占用了多少车位
     */
    @GetMapping(value = "/floorcount")
    public Result selectCoVehicleVoCount(@RequestParam(value = "buildingId") int buildingId, @RequestParam(value = "floorId") int floorId) throws Exception {
        int counts = instCoVehicleMapper.selectCoVehicleVoCount(buildingId,floorId);
        Map mp = new HashMap<String,Object>();
        mp.put("total",counts);
        //new{counts=counts};
        return ResultGenerator.genSuccessResult(mp);
    }
}