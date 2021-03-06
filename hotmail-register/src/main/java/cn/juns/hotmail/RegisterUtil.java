package cn.juns.hotmail;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import net.dongliu.requests.StatusCodes;

import java.util.Random;

/**
 * Created by 01380763 on 2018/8/17.
 */
public class RegisterUtil {

    private static final String[] UPPER_LETTERS = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final String[] LOWER_LETTERS = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final String[] NUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String[] SYMBOLS = {
            "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/",
            ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~"};

    private static final int UPPER_LETTERS_CNT = UPPER_LETTERS.length;
    private static final int LOWER_LETTERS_CNT = LOWER_LETTERS.length;
    private static final int NUMBERS_CNT = NUMBERS.length;
    private static final int SYMBOLS_CNT = SYMBOLS.length;

    private static final int YEAR_MIN = 1905;
    private static final int YEAR_MAX = 1999;

//    private static final String GET_PROXY_URL = "http://ip2.wpic.top:801/email/api/gets/table/gj001/pcname/D001/num/%d";
//    private static final String GET_PROXY_URL = "http://ip.yw365.vip/ip/api/tq/orderid/1ea343d955104c315d709f269f4ddebd/num/%d";
//    private static final String GET_PROXY_URL = "http://www.xsdaili.com/get?orderid=197206003640306&num=%d";
    private static final String GET_PROXY_URL = "http://csip.wpic.top:801/email/api/gets/table/fdsdedsdf/pcname/aerfa87/num/%d/pre/1";

    private static Random random = new Random();

    public enum Type {
        UPPER, LOWER, NUMBER, SYMBOL
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getMemberName() + "  " + getPassword() + "  " + getLastName() + "  " + getFirstName() + "  " + getBirthDate());
        }
    }

    /**
     * 获取代理IP
     *
     * @param num
     * @return
     */
    public static String[] getProxyIPs(int num) {
        String url = String.format(GET_PROXY_URL, num);
        RawResponse response = Requests.get(url).send();
        if (response.getStatusCode() == StatusCodes.OK) {
            String text = response.readToText();
            System.out.println(text);
            if (!Character.isDigit(text.charAt(0))) {
                return null;
            }
            return text.split("\\r\\n");
        }
        return null;
    }

    /**
     * 随机生成一串字符
     *
     * @param length 字符串长度
     * @param types  字符类型
     * @return
     */
    private static String getStringRandom(int length, Type[] types) {
        StringBuilder sb = new StringBuilder();
        if (types == null) {
            types = Type.values();
        }
        int typeCnt = types.length;

        for (int i = 0; i < length; i++) {
            Type type = types[random.nextInt(typeCnt)];
            switch (type) {
                case LOWER:
                    sb.append(LOWER_LETTERS[random.nextInt(LOWER_LETTERS_CNT)]);
                    break;
                case UPPER:
                    sb.append(UPPER_LETTERS[random.nextInt(UPPER_LETTERS_CNT)]);
                    break;
                case NUMBER:
                    sb.append(NUMBERS[random.nextInt(NUMBERS_CNT)]);
                    break;
                case SYMBOL:
                    sb.append(SYMBOLS[random.nextInt(SYMBOLS_CNT)]);
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 字母开头，字母或数字
     *
     * @return
     */
    public static String getMemberName() {
        return getStringRandom(1, new Type[]{Type.LOWER}) +
                getStringRandom(9, new Type[]{Type.LOWER, Type.UPPER, Type.NUMBER})
                + "@hotmail.com";
    }

    public static String getPassword() {
        return getStringRandom(10, null);
    }

    public static String getLastName() {
        return getStringRandom(4, new Type[]{Type.LOWER, Type.UPPER});
    }

    public static String getFirstName() {
        return getStringRandom(4, new Type[]{Type.LOWER, Type.UPPER});
    }

    public static BirthDate getBirthDate() {
        int year = random.nextInt(YEAR_MAX - YEAR_MIN) + YEAR_MIN;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(month == 2 ? 28 : 30) + 1;
        return new BirthDate(year, month, day);
    }

    public static int getSleepTime() {
        return random.nextInt(5) + 1;
    }

    public static class BirthDate {
        public String year;
        public String month;
        public String day;

        public BirthDate(int year, int month, int day) {
            this.year = String.valueOf(year);
            this.month = String.valueOf(month);
            this.day = String.valueOf(day);
        }

        @Override
        public String toString() {
            return year + "-" + month + "-" + day;
        }
    }
}
