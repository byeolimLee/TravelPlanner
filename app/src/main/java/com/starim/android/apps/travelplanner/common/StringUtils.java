package com.starim.android.apps.travelplanner.common;

import java.util.regex.Pattern;

/**
 * Created by starim on 14. 7. 9..
 */
public class StringUtils {
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isExist(CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        final Pattern BLANK = Pattern.compile("^\\s*$");
        return (str == null || BLANK.matcher(str).find());
    }

    public static String defaultIfEmpty(String str, String defaultStr) {
        if (isEmpty(str)) {
            return defaultStr;
        }

        return str;
    }

    public static boolean isEmpty(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return true;
        }

        return false;
    }
}
