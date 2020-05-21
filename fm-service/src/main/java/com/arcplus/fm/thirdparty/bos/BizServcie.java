package com.arcplus.fm.thirdparty.bos;


import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultCode;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.common.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@PropertySource("classpath:app.properties")
public class BizServcie {
  @Value("${bos.url}")
  private String baseUrl;

  @Autowired
  BosAuthService bosAuthService;

  @Autowired
  private RestTemplate restTemplate;

  public Result getBuildings(Integer projectId,Integer pageNum,Integer pageSize){
    String bos_url = baseUrl + "/model/model/getBuildings?access_token="+ bosAuthService.getToken()+"&pageNum="+ pageNum +"&pageSize="+ pageSize +"&projectId="+ projectId;
    return requestBos(bos_url);
  }


  public Result getStoreys(Integer projectId,Integer pageNum,Integer pageSize,Integer buildId){
    String bos_url = baseUrl + "/model/model/getStoreys?access_token="+ bosAuthService.getToken()+"&pageNum="+ pageNum +"&pageSize="+ pageSize +"&projectId="+ projectId;
    if(buildId!=null){
      bos_url+="&buildId="+buildId;
    }
    return requestBos(bos_url);
  }


  public Result getEquipment(Integer projectId,String floorCode){
    String bos_url = baseUrl + "/model/classify/getEquipment?access_token="+ bosAuthService.getToken()+"&code="+floorCode+"&projectId="+ projectId;
    return requestBos(bos_url);
  }


  public Result getSpaceInfos(Integer projectId,Integer storyId,String queryParam){
    String bos_url = baseUrl + "/model/model/getSpaceInfos?access_token="+ bosAuthService.getToken()+"&storyId="+storyId+"&projectId="+projectId;
    if(queryParam!=null){
      bos_url+=bos_url+"&queryParam="+queryParam;
    }
    return requestBos(bos_url);
  }

  public Result getSpaceInfosPager(Integer projectId,Integer storyId,int page,int limit,String queryParam){
    String bos_url = baseUrl + "/model/model/getSpaceInfosPage?access_token="+ bosAuthService.getToken()+"&storyId="+storyId+"&projectId="+projectId+"&pageNum="+page+"&pageSize"+limit;
    if(queryParam!=null){
      bos_url+=bos_url+"&queryParam="+queryParam;
    }
    return requestBos(bos_url);
  }


  public Result modelList(Integer projectId,Integer buildId,Integer storyId){
    String bos_url = baseUrl + "/model/model/modelList?access_token="+ bosAuthService.getToken()+"&projectId="+projectId +"&buildId="+buildId +"&storyId="+storyId;
    return requestBos(bos_url);
  }


  public Result getSpecs(Integer projectId,Integer dev3Id){
    String bos_url = baseUrl + "/model/classify/getSpecs?access_token="+ bosAuthService.getToken()+"&dev3Id="+dev3Id+"&projectId="+projectId;
    return requestBos(bos_url);
  }

  public Result getModelCount(Integer projectId,Integer buildId,Integer storyId){
    String bos_url = baseUrl + "/model/model/count?access_token="+ bosAuthService.getToken()+"&buildId="+buildId+"&storyId="+storyId+"&projectId="+projectId;
    return requestBos(bos_url);
  }


  public Result getDeviceTree(String code,Integer projectId,String story){
    return requestBos(baseUrl+"/device/manage/getDeviceTree" +
            "?access_token="+ bosAuthService.getToken()
            +"&code="+code
            +"&projectId="+projectId
            +"&story="+story);
  }

  public Result getSensorRealTimeData(String code){
    return requestBos(baseUrl+"/device/sensor/getRealTimeData" +
            "?access_token="+ bosAuthService.getToken()
            +"&code="+code);
  }

  public Result getSensorHistoryData(String code,Integer pageNum,Integer pageSize){
    return requestBos(baseUrl+"/device/sensor/getHistoryData"
            +"?access_token="+ bosAuthService.getToken()
            +"&iotCode="+code
            +"&pageNum="+pageNum
            +"&pageSize="+pageSize);
  }

  private Result requestBos(String url){
   // String bos_url = baseUrl + url;
    log.info(url);
    try{
      ResponseEntity<Map> response  = restTemplate.getForEntity(url, Map.class);
      if(response.getStatusCode().is2xxSuccessful()){
        Map<String,Object> result = response.getBody();
        if(result.get("code")!=null){
          Integer respCode = Integer.parseInt(result.get("code").toString());
          if(!respCode.equals(200)){
            String msg = result.get("message").toString();
            throw new ServiceException(respCode,"bos接口返回错误,"+msg);
          }
        }
        return ResultGenerator.genSuccessResult(result.get("data"));
      }
    }catch (Exception e){
       throw new ServiceException(500,"bos接口内部错误");
    }

    return ResultGenerator.genFailResult("bos接口返回非成功状态码", ResultCode.NODATA);
  }
}
