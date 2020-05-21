package com.arcplus.fm.controller;


import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultCode;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.entity.dto.BoWorkorderCodeDto;
import com.arcplus.fm.entity.dto.LoginUserDto;
import com.arcplus.fm.entity.dto.WorkorderDealAfterDto;
import com.arcplus.fm.entity.dto.WorkorderDealDto;
import com.arcplus.fm.entity.vo.UserLiteDto;
import com.arcplus.fm.entity.vo.WorkorderVo;
import com.arcplus.fm.entity.BoWorkorder;
import com.arcplus.fm.feign.UserClient;
import com.arcplus.fm.service.BoScheduledMaintainService;
import com.arcplus.fm.service.BoWorkorderService;
import com.arcplus.fm.thirdparty.bos.BosAuthService;
import com.arcplus.fm.util.AuthUtil;
import com.github.pagehelper.PageInfo;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("fm/api")
public class AppController {
  @Autowired
  BoWorkorderService boWorkorderService;

  @Autowired
  BoScheduledMaintainService boScheduledMaintainService;

  @Autowired
  UserClient userClient;

  @Autowired
  BosAuthService bosAuthService;

//  /*
//    app接口，获取执行人是我的定期维保列表
//  */
//  @GetMapping(value = "/sm/list")
//  public Result selectApiComplexList(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit) throws Exception {
//    Map<String,Object> userMap = AuthUtil.getCurrentUser();
//    if(userMap==null){
//      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
//    }
//    int uid =  Integer.parseInt(userMap.get("id").toString());
//    PageInfo<BoScheduledMaintain> result = boScheduledMaintainService.selectByAssignment(page,limit,uid);
//    return ResultGenerator.genSuccessPageResult(result);
//  }


//  @PostMapping(value = "/login")
//  public Result getBosToken(@RequestBody LoginUserDto loginUserDto) throws Exception {
//    Map<String, Object> result = userClient.login(loginUserDto);
//    result.put("bos_token",bosAuthService.getToken());
//    return ResultGenerator.genSuccessResult(result);
//  }

  @GetMapping(value = "/current/userinfo")
  public Result getUserInfo() {
     Map<String,Object> userMap = AuthUtil.getCurrentUser();
    userMap.remove("merchantInfo");
    userMap.remove("sysRoles");
    userMap.remove("permissions");
    userMap.remove("userInfoAndPermissions");
    userMap.remove("accountNonExpired");
    userMap.remove("credentialsNonExpired");
    userMap.remove("accountNonLocked");
    return ResultGenerator.genSuccessResult(userMap);
  }


  /*
      app接口，根据状态获取工单列表
  */
  @GetMapping(value = "/wo/list")
  public Result selectApiComplexList(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit,@RequestParam(value="status") int status) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int adminRole = Integer.parseInt(userMap.get("jobType").toString());
    int uid =  Integer.parseInt(userMap.get("id").toString());
    PageInfo<WorkorderVo> result = boWorkorderService.selectWorkorderComplex(page,limit,adminRole,uid,status);
    return ResultGenerator.genSuccessPageResult(result);
  }

  /*
    app接口，班组指派维修工
 */
  @PostMapping(value = "/wo/admin/assign")
  public Result assignWorker(@RequestBody BoWorkorder workorder) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(workorder,2,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
    app接口，班组拒绝
 */
  @PostMapping(value = "/wo/admin/decline")
  public Result adminDecline(@RequestBody BoWorkorderCodeDto woCode) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(woCode,-2,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
  app接口，维修工已读
*/
  @PostMapping(value = "/wo/worker/read")
  public Result workerRead(@RequestBody  BoWorkorderCodeDto woCode) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(woCode,3,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
    app接口，维修工接收
 */
  @PostMapping(value = "/wo/worker/receive")
  public Result workerAgree(@RequestBody  BoWorkorderCodeDto woCode) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(woCode,4,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
    app接口，维修工拒绝
 */
  @PostMapping(value = "/wo/worker/decline")
  public Result workerDecline(@RequestBody BoWorkorderCodeDto woCode) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(woCode,-4,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
    app接口，维修工处理前
 */
  @PostMapping(value = "/wo/before/deal")
  public Result beforeDeal(@RequestBody WorkorderDealDto workorder) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(workorder,5,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
    app接口，维修工处理后
  */
  @PostMapping(value = "/wo/after/deal")
  public Result afterDeal(@RequestBody WorkorderDealAfterDto workorder) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(workorder,6,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
    app接口，班组审核通过
  */
  @PostMapping(value = "/wo/admin/agree")
  public Result adminAgree(@RequestBody  BoWorkorderCodeDto woCode) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(woCode,7,uid,uName);
    return ResultGenerator.genSuccessResult();
  }

  /*
    app接口，班组拒绝
  */
  @PostMapping(value = "/wo/admin/reject")
  public Result adminReject(@RequestBody  BoWorkorderCodeDto woCode) throws Exception {
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    String uName = userMap.get("username").toString();
    boWorkorderService.processWorkOrder(woCode,-7,uid,uName);
    return ResultGenerator.genSuccessResult();
  }



  /*
    app接口，获取包含报修和处理过程记录等工单详细信息
   */
  @GetMapping(value = "/wo/detail/{id}")
  public Result getDetailById(@PathVariable("id") int id) throws Exception {
    return ResultGenerator.genSuccessResult(boWorkorderService.getDetailById(id));
  }

  /*
  app接口，获取定期任务的列表
 */
  @GetMapping(value = "/sm/list")
  public Result getScheduledMaintainList(@RequestParam(value="page") int page,@RequestParam(value="limit") int limit,@RequestParam(value="status") int status,
      HttpSession session) throws Exception {
    log.info(session.toString());
    Map<String,Object> userMap = AuthUtil.getCurrentUser();
    if(userMap==null){
      return ResultGenerator.genFailResult("用户未登录", ResultCode.FAIL);
    }
    int uid =  Integer.parseInt(userMap.get("id").toString());
    return ResultGenerator.genSuccessPageResult(boScheduledMaintainService.selectScheduledMaintainComplex(page,limit,uid,status));
  }

  /*
  app接口，维修工处理
 */
  @PostMapping(value = "/sm/worker/deal/{id}")
  public Result workerDeal(@PathVariable("id") int id) throws Exception {
    return ResultGenerator.genSuccessResult(boScheduledMaintainService.workerDeal(id));
  }

  @GetMapping(value = "/getAdmin")
  public Result getAdmin() throws Exception {
    List<Map> maps =  (List<Map>)userClient.getUserByType(1);
    if(maps==null){
      return ResultGenerator.genFailResult("FeignClient调用失败",ResultCode.FAIL);
    }
    List<UserLiteDto> result = new ArrayList<>();
    UserLiteDto userLiteDto = null;
    for(Map item:maps){
      userLiteDto = new UserLiteDto();
      userLiteDto.setId(Long.parseLong(item.get("id").toString()));
      userLiteDto.setUsername(item.get("username").toString());
      userLiteDto.setNickname(item.get("nickname").toString());
      userLiteDto.setJobType(Integer.parseInt(item.get("jobType").toString()));
      result.add(userLiteDto);
    }


    return ResultGenerator.genSuccessResult(result);
  }

  @GetMapping(value = "/getWorker")
  public Result getWorker(HttpServletRequest request) throws Exception {
    List<Map> maps = userClient.getUserByType(3);
    if(maps==null){
      return ResultGenerator.genFailResult("FeignClient调用失败",ResultCode.FAIL);
    }
    List<UserLiteDto> result = new ArrayList<>();
    UserLiteDto userLiteDto = null;
    for(Map item:maps){
      userLiteDto = new UserLiteDto();
      userLiteDto.setId(Long.parseLong(item.get("id").toString()));
      userLiteDto.setUsername(item.get("username").toString());
      userLiteDto.setNickname(item.get("nickname").toString());
      userLiteDto.setJobType(Integer.parseInt(item.get("jobType").toString()));
      result.add(userLiteDto);
    }

    return ResultGenerator.genSuccessResult(result);
  }

  /**
   * 取认证用户信息
   * @return
   */
  @RequestMapping({ "/client/userinfo" })
  public Principal user(Principal user) {
    return user;
  }

  @RequestMapping({ "/test" })
  public Result test() {
    return ResultGenerator.genFailResult("FeignClient调用失败",ResultCode.FAIL);
  }

}
