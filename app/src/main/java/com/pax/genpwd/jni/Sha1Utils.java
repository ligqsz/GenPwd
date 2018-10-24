package com.pax.genpwd.jni;

/**
 * @author ligq
 * @date 2018/5/22
 */

public class Sha1Utils {
    static {
        System.loadLibrary("sha1");
    }

    public static native byte[] calcSha1(String data, int len, String data2, int len2);
}
