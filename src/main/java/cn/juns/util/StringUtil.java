package cn.juns.util;

import java.util.Objects;

/**
 * Created by liuyuanjun on 2017/12/23.
 */
public class StringUtil {

    public static boolean isBlank(String str) {
        if(str == null) {
            return true;
        } else {
            int len = str.length();

            for(int i = 0; i < len; ++i) {
                if(!Character.isWhitespace(str.codePointAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        if(str == null) {
            return false;
        } else {
            int len = str.length();

            for(int i = 0; i < len; ++i) {
                if(!Character.isWhitespace(str.codePointAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String upperFirstChar(String str) {
        assert Objects.nonNull(str);
        return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
    }
}
