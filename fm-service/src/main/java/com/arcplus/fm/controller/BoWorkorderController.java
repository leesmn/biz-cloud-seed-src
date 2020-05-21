package com.arcplus.fm.controller;

import com.arcplus.fm.common.ResultCode;
import com.arcplus.fm.entity.dto.BaseResponse;
import com.arcplus.fm.entity.dto.PageEntity;
import com.arcplus.fm.entity.dto.WorkorderLiteDto;
import com.arcplus.fm.entity.BoWorkorder;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.BoWorkorderService;
import com.arcplus.fm.util.AuthUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.arcplus.fm.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("boWorkorder")
public class BoWorkorderController  {
    @Autowired
    BoWorkorderService instBoWorkorderService;

    @PostMapping(value = "/add")
    public Result addBoWorkorder(@RequestBody BoWorkorder entity) throws Exception {
        return ResultGenerator.genSuccessResult(instBoWorkorderService.save(entity,"CM"));
    }

    @PostMapping(value = "/update")
    public Result updateBoWorkorder(@RequestBody BoWorkorder entity) throws Exception {
        instBoWorkorderService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delBoWorkorderService(@PathVariable("id") int id) throws Exception {
        instBoWorkorderService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "/{id}")
    public Result getById(@PathVariable("id") int id) throws Exception {
        return ResultGenerator.genSuccessResult(instBoWorkorderService.getById(id));
    }

    @GetMapping(value = "/detail/{id}")
    public Result getDetailById(@PathVariable("id") int id) throws Exception {
        return ResultGenerator.genSuccessResult(instBoWorkorderService.getDetailById(id));
    }

    @GetMapping(value = "/page")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<BoWorkorder> reslut = instBoWorkorderService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }

    @PostMapping(value = "/visit")
    public Result visitCustom(@RequestBody BoWorkorder entity) throws Exception {
        Map<String,Object> userMap = AuthUtil.getCurrentUser();
        if(userMap==null){
            return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
        }
        int uid =  Integer.parseInt(userMap.get("id").toString());
        String uName = userMap.get("username").toString();
        instBoWorkorderService.processWorkOrder(entity,8,uid,uName);
        return ResultGenerator.genSuccessResult();
    }



    @GetMapping(value = "/floor")
    public Result selectByFloor(@RequestParam(value = "buildId") int buildId, @RequestParam(value = "floorId") int floorId,@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<BoWorkorder> reslut = instBoWorkorderService.selectByFloor(page,limit,buildId,floorId);
        return ResultGenerator.genSuccessPageResult(reslut);
    }

    /*
    获取楼层新工单数量
    */
    @GetMapping(value = "/num/floor")
    public Result selectTotal(@RequestParam(value = "buildId") int buildId) throws Exception {
        return ResultGenerator.genSuccessResult(instBoWorkorderService.countByFloor(buildId));
    }



  /*
  管理后台接口，通过参数模糊查询工单列表
 */
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
                }else if("dealStatus".equals(item.getKey())){
                    List<String> statusList = new ArrayList<>();
                    String statusVal = item.getValue().toString();
                    if(statusVal.equals("0")){
                        statusList.add("CR");
                    }else  if(statusVal.equals("5")){
                        statusList.add("AD");
                        statusList.add("AM");
                        statusList.add("MR");
                        statusList.add("MD");
                    }else  if(statusVal.equals("10")){
                        statusList.add("MA");
                        statusList.add("MB");
                        statusList.add("AR");
                    }else  if(statusVal.equals("20")){
                        statusList.add("MO");
                    }else  if(statusVal.equals("30")){
                        statusList.add("AC");
                    }else  if(statusVal.equals("40")){
                        statusList.add("CL");
                    }
                    if(!statusVal.equals("-99")){
                        param.put("statusArray",statusList);
                    }
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
            String value = param.get(key).toString();
//            if(key.equals("dealStatus")){
//
//            }

            if (StringUtils.isEmpty(param.getOrDefault(key, "").toString())) {
                param.remove(key);
                continue;
            }


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
            param.put("orderBy", "wo_id desc");
        }
        PageInfo<BoWorkorder> reslut = instBoWorkorderService.search(param, page, limit);
        return ResultGenerator.genSucessPageResult(reslut);
    }
}