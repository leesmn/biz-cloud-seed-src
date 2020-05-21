package com.arcplus.fm.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NumberProduceUtil {
  private static Integer seq = 1;

  public synchronized static String getNum(String prefix,int length) {
    if(seq.toString().length() + 1>length){
      seq = 0;
    }
    Format dateformat = new SimpleDateFormat("yyyyMMddHHmm");
    String ymd = dateformat.format(new Date());
    return prefix + ymd + String.format("%0"+length+"d", seq++);
  }
}
