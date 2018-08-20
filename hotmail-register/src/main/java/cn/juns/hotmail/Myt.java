package cn.juns.hotmail;

import com.sun.deploy.net.HttpUtils;
import io.netty.handler.codec.http.HttpResponse;
import net.dongliu.requests.Requests;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Myt {
    public static void main(String[] args) throws IOException {
//        System.out.println(ImageToBase64ByLocal("/Users/juns/adt/a.jpeg"));
        capther("/Users/juns/adt/a.jpeg");
    }


    public static String capther(String imgFile){
        String host = "https://jisuyzmsb.market.alicloudapi.com";
        String path = "/captcha/recognize";
        String method = "POST";

        //AppCode：e5cce10439534d8b86043f732e665b9b
        String appcode = "e5cce10439534d8b86043f732e665b9b";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("type", "en");
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("pic", ImageToBase64ByLocal(imgFile));


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            String resp = Requests.post(host + path).headers(headers).params(querys).body(bodys).send().readToText();

            System.out.println(resp);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }


}
