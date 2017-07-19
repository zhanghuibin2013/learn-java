package com.zhhb.es;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhanghuibin on 2017/6/5.
 */
public class ElasticSearchTest {
    @Test
    public void TransportClientTest() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "o2o-elk-cluster")
                .build();
        TransportClient client = TransportClient.builder()
                .settings(settings)
                .build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.185.32.44"), 9300));

        Calendar calendar = Calendar.getInstance();
        Date dtNow = calendar.getTime();
        calendar.add(Calendar.MINUTE, -1);
        Date dtTo = calendar.getTime();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.add(Calendar.MINUTE, -1);
        calendar.add(Calendar.SECOND, -5);
        Date dtFrom = calendar.getTime();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp").from(dtFrom).to(dtTo);
        String strDate = new SimpleDateFormat("yyyy.MM.dd").format(dtNow);
        String[] strIndexPrefixs = {"logstash-neingx", "logstash-tomcat", "logstash-waingx"};
        String[] strIndexes = new String[strIndexPrefixs.length];
        for (int i = 0; i < strIndexPrefixs.length; i++) {
            strIndexes[i] = strIndexPrefixs[i] + "-" + strDate;
        }
        SearchResponse scrollResp = client.prepareSearch(strIndexes)
                .setScroll(new TimeValue(60000))
                .setSize(100)
                .setPostFilter(rangeQueryBuilder)
                .execute().actionGet();
        int count = 0;
        while (true) {
            for (SearchHit hit : scrollResp.getHits()) {
                System.out.print("row : " + (++count) + ";");
                System.out.print("id : " + hit.getId() + ";");
                System.out.print("index : " + hit.getIndex() + ";");
                System.out.print("type : " + hit.getType() + ";");
                for (Map.Entry<String, Object> rpEnt : hit.getSource().entrySet()) {
                    System.out.print(rpEnt.getKey() + " : " + rpEnt.getValue() + ";");
                }
                System.out.println();
            }
            //Break condition: No hits are returned
            if (scrollResp.getHits().hits().length == 0) {
                break;
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        }
    }



}
