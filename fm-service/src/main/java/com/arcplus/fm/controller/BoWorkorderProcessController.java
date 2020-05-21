package com.arcplus.fm.controller;

import com.arcplus.fm.entity.BoWorkorderProcess;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.BoWorkorderProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("boWorkorderProcess")
public class BoWorkorderProcessController  {
    @Autowired
    BoWorkorderProcessService instBoWorkorderProcessService;

    @PostMapping(value = "/add")
    public Result addBoWorkorderProcess(@RequestBody BoWorkorderProcess entity) throws Exception {
        return ResultGenerator.genSuccessResult(instBoWorkorderProcessService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateBoWorkorderProcess(@RequestBody BoWorkorderProcess entity) throws Exception {
        instBoWorkorderProcessService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delBoWorkorderProcessService(@PathVariable("id") int id) throws Exception {
        instBoWorkorderProcessService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "/page")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<BoWorkorderProcess> reslut = instBoWorkorderProcessService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }
}