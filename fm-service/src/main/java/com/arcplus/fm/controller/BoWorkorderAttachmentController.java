package com.arcplus.fm.controller;

import com.arcplus.fm.entity.BoWorkorderAttachment;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.service.BoWorkorderAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("boWorkorderAttachment")
public class BoWorkorderAttachmentController  {
    @Autowired
    BoWorkorderAttachmentService instBoWorkorderAttachmentService;

    @PostMapping(value = "/add")
    public Result addBoWorkorderAttachment(@RequestBody BoWorkorderAttachment entity) throws Exception {
        return ResultGenerator.genSuccessResult(instBoWorkorderAttachmentService.save(entity));
    }

    @PostMapping(value = "/update")
    public Result updateBoWorkorderAttachment(@RequestBody BoWorkorderAttachment entity) throws Exception {
        instBoWorkorderAttachmentService.update(entity);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping(value = "/del/{id}")
    public Result delBoWorkorderAttachmentService(@PathVariable("id") int id) throws Exception {
        instBoWorkorderAttachmentService.delete(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping(value = "")
    public Result selectAllPage(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
        PageInfo<BoWorkorderAttachment> reslut = instBoWorkorderAttachmentService.selectAllPage(page,limit);
        return ResultGenerator.genSuccessPageResult(reslut);
    }
}