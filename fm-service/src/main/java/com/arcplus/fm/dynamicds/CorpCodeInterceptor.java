//package com.arcplus.fm.dynamicds;
//
//import com.aifmworld.cloud.common.utils.AppUserUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Set;
//
///**
// * @author jv_team
// * @date 2019/12/13 11:26
// */
//@Slf4j
//@Component
//public class CorpCodeInterceptor implements HandlerInterceptor {
//    private String orgCodeHeaderName = "orgCode";
////    private Set<String> validOrgCodes;
//
////    public void setValidOrgCodes(Set<String> validOrgCodes) {
////        this.validOrgCodes = validOrgCodes;
////    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        System.out.printf("preHandle被调用");
//
//        String orgCodeVal = AppUserUtil.getLoginAppUser().getMerchantInfo().getMerchantCode();//httpServletRequest.getHeader(orgCodeHeaderName);
//        if(orgCodeVal == null) {
//            log.error("The request without a header named as " + orgCodeHeaderName);
//            return false;
//        }
////        if(!validOrgCodes.contains(orgCodeVal)) {
////            log.error(String.format(" the orgCode %s is not valid.", orgCodeVal));
////            return false;
////        }
//        DSContextHolder.setNode(orgCodeVal);
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle被调用");
//        DSContextHolder.remove();
//    }
//}
