package ltd.icecold.orange.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;

/**
 * utils
 *
 * @author ice-cold
 */
public class Utils {
    /**
     * 格式化Json
     * @param json json数据
     * @return 格式化数据
     */
    public static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /**
     * 获取随机字符串
     * @param length 位数
     * @return 随机字符串
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * byte[]转16进制字符串
     * @param bytes byte
     * @return 16进制数据
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTmp;

        for (int i = 0; i < bytes.length; i++) {
            sTmp = Integer.toHexString(0xFF & bytes[i]);
            if (sTmp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTmp);
        }

        return sb.toString();
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 补齐不足长度
     * @param length 长度
     * @param number 数字
     * @return result
     */
    public static String padStart(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }

    /**
     * 对应js中的encodeURIComponent
     * @param data 编码数据
     * @return result
     */
    public static String encodeURIComponent(String data) {
        String result = null;
        try {
            result = URLEncoder.encode(data, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = data;
        }
        return result;
    }

    /**
     * 顾名思义
     * @param cookie map
     * @return cookie
     */
    public static String cookieMap2String(Map<String, String> cookie) {
        StringBuilder stringBuilder = new StringBuilder();
        cookie.forEach((key, value) -> {
            stringBuilder.append(key).append("=").append(value).append(";");
        });
        return stringBuilder.toString();
    }
}
