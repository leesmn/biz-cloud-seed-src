package com.arcplus.fm.controller;

import com.arcplus.fm.entity.TpAsset;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.TpAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("tpAsset")
public class TpAssetController  {
    @Autowired
    TpAssetService instTpAssetService;

    @PostMapping(value = "/add")
    public Result addTpAsset(@RequestBody TpAsset entity) throws Exception {
        return ResultGenerator.genSuccessResult(instTpAssetService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateTpAsset(@RequestBody TpAsset entity) throws Exception {
        instTpAssetService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delTpAssetService(@PathVariable("id") int id) throws Exception {
        instTpAssetService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<TpAsset> reslut = instTpAssetService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }
}