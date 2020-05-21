package com.arcplus.fm.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author jv_team
 * @date 2019/12/11 11:04
 */
public class ListUtils<T> {
    public  void copyList(Object obj, List<T> list2, Class<T> classObj) {
        if ((!Objects.isNull(obj)) && (!Objects.isNull(list2))) {
            List list1 = (List) obj;
            list1.forEach(item -> {
                try {
                    T data = classObj.newInstance();
                    BeanUtils.copyProperties(item, data);
                    list2.add(data);
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                }

            });
        }
    }
}
