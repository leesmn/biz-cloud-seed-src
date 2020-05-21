package com.arcplus.fm.dynamicds;

/**
 * @author jv_team
 * @date 2019/12/13 10:11
 */
public class DSContextHolder {

    private static ThreadLocal<String> nodeThreadLocal = new ThreadLocal<String>();

    public static final void setNode(String scheme) {
        nodeThreadLocal.set(scheme);
    }

    public static final String getNode() {
        String scheme = nodeThreadLocal.get();
        if (scheme == null) {
            scheme = "";
        }
        return scheme;
    }

    public static final void remove() {
        nodeThreadLocal.remove();
    }

}

