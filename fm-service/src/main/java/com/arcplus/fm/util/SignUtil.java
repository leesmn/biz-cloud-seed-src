package com.arcplus.fm.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jv_team
 * @date 2019/11/15 9:39
 */
public class SignUtil {
    private static String key_secret = "078f2ae59a7d4471a4c2a72aaebbe779";
    /**
     * 生成签名
     *  @return md5加密签名
     */
    public static String generateSign(Map<String, Object> bodyMap,Long currentTimestamp) {
        Map<String, Object> resultMap = sortMap(bodyMap);
        String sortResult = JSON.toJSONString(resultMap);

        String sign = DigestUtils.md5(sortResult + key_secret + currentTimestamp).toString();
        return sign;
    }

    private static Map<String, Object> sortMap(Map<String, Object> map) {
	        if (map == null || map.isEmpty()) {
	            return null;
	        }
	        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapComparator());
	        sortMap.putAll(map);
	        return sortMap;
    }

    private static class MapComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            // TODO Auto-generated method stub
            return str1.compareTo(str2);
        }
    }

}
