package com.mvmap.news.utils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Environment;
import android.text.TextUtils;

public class Utils {

    /**
     * get the external storage file
     * 
     * @return the file
     */
    public static File getExternalStorageDir() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * get the external storage file path
     * 
     * @return the file path
     */
    public static String getExternalStoragePath() {
        return getExternalStorageDir().getAbsolutePath();
    }

    /**
     * get the external storage state
     * 
     * @return
     */
    public static String getExternalStorageState() {
        return Environment.getExternalStorageState();
    }

    /**
     * check the usability of the external storage.
     * 
     * @return enable -> true, disable->false
     */
    public static boolean isExternalStorageEnable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;

    }

    /**
     * judge the list is null or isempty
     * 
     * @param list
     * @return
     */
    public static boolean isEmpty(final List<? extends Object> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(final Set<? extends Object> sets) {
        if (sets == null || sets.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(
            final Map<? extends Object, ? extends Object> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the string is null or 0-length.
     * 
     * @param text
     * @return
     */
    public static boolean isEmpty(final String text) {
        return TextUtils.isEmpty(text);
    }
}
