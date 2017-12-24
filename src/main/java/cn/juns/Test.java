package cn.juns;

import net.dongliu.requests.Requests;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 水信玄饼 on 2017/12/22.
 */
public class Test {
    public static void main(String[] args) {
        String url = "http://localhost:9200";
        String text = Requests.get(url).send().readToText();
        System.out.println(text);
    }
}
