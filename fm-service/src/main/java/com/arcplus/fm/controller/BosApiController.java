package com.arcplus.fm.controller;
import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.thirdparty.bos.BizServcie;
import com.arcplus.fm.thirdparty.bos.BosAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bos/api")
public class BosApiController {
  @Autowired
  BizServcie bizServcie;

  @Autowired
  BosAuthService bosAuthService;

  /*
  获取bos的token
 */
  @GetMapping(value = "/getBosToken")
  public Result getBosToken() throws Exception {
    return ResultGenerator.genSuccessResult(bosAuthService.getToken());
  }

  /*
  获取建筑列表
   */
  @GetMapping(value = "/getBuilding/{projectId}")
  public Result getBuilding(@PathVariable("projectId") Integer projectId,@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
    return bizServcie.getBuildings(projectId,page,limit);
  }

  /*
  根据建筑id，buildId获取楼层信息
   */
  @GetMapping(value = "/getStorey/{projectId}")
  public Result getStorey(@PathVariable("projectId") Integer projectId,@RequestParam(value="page") int page,@RequestParam(value="limit") int limit,@RequestParam(value="buildId") Integer buildId) throws Exception {
    return bizServcie.getStoreys(projectId,page,limit,buildId);
  }

  /*
  根据楼层code获取设备树
   */
  @GetMapping(value = "/getEquipment/{projectId}")
  public Result getEquipment(@PathVariable("projectId") Integer projectId,@RequestParam(value="code") String code) throws Exception {
    return bizServcie.getEquipment(projectId,code);
  }

  /*
  根据楼层号获取空间列表
   */
  @GetMapping(value = "/getSpaceInfos/{projectId}")
  public Result getSpaceInfos(@PathVariable("projectId") Integer projectId,@RequestParam(value="storyId") Integer storyId,@RequestParam(value="queryParam",required = false) String queryParam) throws Exception {
    return bizServcie.getSpaceInfos(projectId,storyId,queryParam);
  }

  /*
   根据楼层号以及空间名称获取空间列表
 */
  @GetMapping(value = "/getSpaceInfosPager/{projectId}")
  public Result getSpaceInfosPager(@PathVariable("projectId") Integer projectId,@RequestParam(value="storyId") Integer storyId,@RequestParam(value="queryParam",required = false) String queryParam,@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
    return bizServcie.getSpaceInfosPager(projectId,storyId,page,limit,queryParam);
  }

  /*
  获取模型列表
   */
  @GetMapping(value = "/modelList/{projectId}")//126
  public Result modelList(@PathVariable("projectId") Integer projectId, @RequestParam(value="buildId") Integer buildId,@RequestParam(value="storyId") Integer storyId) throws Exception {
    return bizServcie.modelList(projectId,buildId,storyId);
  }

  /*
  获取设备规格
   */
  @GetMapping(value = "/getSpecs/{projectId}")//126
  public Result getSpecs(@PathVariable("projectId") Integer projectId, @RequestParam(value="dev3Id") Integer dev3Id) throws Exception {
    return bizServcie.getSpecs(projectId,dev3Id);
  }

  /*
  获取模型统计
 */
  @GetMapping(value = "/getModelCount/{projectId}")//126
  public Result getModelCount(@PathVariable("projectId") Integer projectId, @RequestParam(value="buildId") Integer buildId, @RequestParam(value="storyId") Integer storyId) throws Exception {
    return bizServcie.getModelCount(projectId,buildId,storyId);
  }




  /*
  获取感知设备树
  */
  @GetMapping(value = "/device/manage/getDeviceTree")
  public Result getDeviceTree(
          @RequestParam(value="code") String code,
          @RequestParam(value="projectId") Integer projectId,
          @RequestParam(value="story") String story) {
    return bizServcie.getDeviceTree(code,projectId,story);
  }

  /*
  Redis获取设备实时数据
  */
  @GetMapping(value = "/device/sensor/getRealTimeData")
  public Result getSensorRealTimeData(
          @RequestParam(value="code") String code) {
    return bizServcie.getSensorRealTimeData(code);
  }

  /*
  获取设备历史数据
  */
  @GetMapping(value = "/device/sensor/getHistoryData")
  public Result getSensorHistoryData(
          @RequestParam(value="code") String code,
          @RequestParam(value="pageNum") Integer pageNum,
          @RequestParam(value="pageSize") Integer pageSize) {
    return bizServcie.getSensorHistoryData(code,pageNum,pageSize);
  }

}
