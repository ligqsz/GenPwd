package com.pax.genpwd.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ligq
 * @date 2018/5/17
 */

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Utils {
    private static final int EIGHT = 8;
    private static final int TWO = 2;
    private static final String TAG = Utils.class.getSimpleName();
    private static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    private static final int FOUR = 4;

    private Utils() {
        throw new IllegalArgumentException();
    }

    public static String genData(String date, String sn) {
        if (date == null || date.length() < EIGHT) {
            return "";
        }
        String year = getYear(date);
        String month = getMonth(date);
        String day = getDay(date);
        return "P" + year + "A" + month + "X" +
                day + "SZ" + sn;
    }

    public static String getReversalData(String data) {
        try {
            return new StringBuilder(data).reverse().toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFinalPwd(int[] sha1, String date) {
        if (TextUtils.isEmpty(date) || date.length() < EIGHT) {
            return "";
        }
        return Utils.getFromSha1(sha1, getYear(date))
                + Utils.getFromSha1(sha1, getMonth(date))
                + Utils.getFromSha1(sha1, getDay(date));
    }

    private static String getFromSha1(int[] sha1, String value) {
        String result = String.valueOf(sha1[parseIntSafe(value) % 20] % 100);
        if (result.length() < TWO) {
            result = "0" + result;
        }
        return result;
    }

    private static String getDay(String date) {
        try {
            return date.substring(6, 8);
        } catch (Exception e) {
            return "";
        }
    }

    private static String getMonth(String date) {
        try {
            return date.substring(4, 6);
        } catch (Exception e) {
            return "";
        }
    }

    private static String getYear(String date) {
        try {
            return date.substring(0, 4);
        } catch (Exception e) {
            return "";
        }
    }

    public static byte[] sha1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();

            for (byte i : messageDigest) {
                String shaHex = Integer.toHexString(i & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().getBytes();

        } catch (NoSuchAlgorithmException e) {
            Log.w(TAG, "sha1:", e);
        }
        return new byte[0];
    }

    private static int parseIntSafe(String str) {
        return parseIntSafe(str, 0);
    }

    @SuppressWarnings("SameParameterValue")
    private static int parseIntSafe(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * get current date & time : 20180504093324
     *
     * @return current date & time
     */
    public static String formatDate(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat destDateFormat = new SimpleDateFormat(FORMAT_YYYYMMDD);
        return destDateFormat.format(date);
    }

    public static String formatDate(CalendarDay calendarDay) {
        return SimpleDateFormat.getDateInstance().format(calendarDay.getDate());
    }

    public static String formatDate(Calendar calendar) {
        return SimpleDateFormat.getDateInstance().format(calendar.getTime());
    }

    public static boolean checkSn(String sn) {
        return !TextUtils.isEmpty(sn) && sn.length() >= EIGHT;
    }

    /**
     * int array to byte array
     *
     * @param intArr int array
     * @return byte array
     */
    public static byte[] intArr2ByteArr(int[] intArr) {
        //长度
        int byteLength = intArr.length * 4;
        //开辟数组
        byte[] bt = new byte[byteLength];
        int curInt;
        for (int j = 0, k = 0; j < intArr.length; j++, k += FOUR) {
            curInt = intArr[j];
            //右移4位，与1作与运算
            bt[k] = (byte) ((curInt >> 24) & 0b1111_1111);
            bt[k + 1] = (byte) ((curInt >> 16) & 0b1111_1111);
            bt[k + 2] = (byte) ((curInt >> 8) & 0b1111_1111);
            bt[k + 3] = (byte) ((curInt) & 0b1111_1111);
        }
        return bt;
    }

    /**
     * byte array to int array
     *
     * @param btArr byte array
     * @return int array
     */
    public static int[] byteArr2IntArr(byte[] btArr) {
        if (btArr == null || btArr.length == 0) {
            return new int[0];
        }
        int[] result = new int[btArr.length];
        for (int i = 0; i < btArr.length; i++) {
            result[i] = btArr[i] & 0xff;
        }
        return result;
    }

    public static void hideSystemKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
