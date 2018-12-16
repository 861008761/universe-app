package com.mylovin.utils.bigdata;

import com.mylovin.web.model.Comment;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElasticSearchUtil {


    public static Client client = null;

    /**
     * 获取操作连接
     *
     * @return
     */
    public static TransportClient getTransportClient() {
        String[] IP = {"127.0.0.1"};
        // 集群设置
        Settings settings = Settings.builder()
                .put("cluster.name", "es")//集群名称
                //.put("client.transport.sniff", true)
                .put("client.transport.ping_timeout", "60s")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
        //添加集群地址和tcp服务端口 IP是由集群的各个node的ip组成的数组
        try {
            for (String ip : IP) {
                InetSocketTransportAddress address = new InetSocketTransportAddress(InetAddress.getByName(ip), 9300);
                client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * 取得实例
     * @return
     */
    public static TransportClient getClient() {
        TransportClient client = null ;
        try {
            Settings settings = Settings.builder().put("cluster.name", "es")
                    .put("client.transport.ping_timeout", "30s").build();
            client = new PreBuiltTransportClient(settings);
            String[] ips = "localhost".split(",");
            for (int i = 0; i < ips.length; i++) {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ips[i]), 9300));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    public static String addIndex(String index, String type, Comment comment) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("rowkey", comment.getRowKey());
        hashMap.put("title", comment.getTitle());
        hashMap.put("star", comment.getStar());
        hashMap.put("comment", comment.getComment());

        IndexResponse response = getTransportClient().prepareIndex(index, type).setSource(hashMap).execute().actionGet();
        return response.getId();
    }


    public static Map<String, Object> search(String key, String index, String type, int start, int row) {
        SearchRequestBuilder builder = getTransportClient().prepareSearch(index);
        builder.setTypes(type);
        builder.setFrom(start);
        builder.setSize(row);
        //设置高亮字段名称

        /* builder.addHighlightedField("title");
        builder.addHighlightedField("describe");
        //设置高亮前缀
        builder.setHighlighterPreTags("<font color='red' >");
        //设置高亮后缀
        builder.setHighlighterPostTags("</font>");*/

        builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        if (StringUtils.isNotBlank(key)) {
//			builder.setQuery(QueryBuilders.termQuery("title",key));
            builder.setQuery(QueryBuilders.multiMatchQuery(key, "title", "comment"));
        }
        builder.setExplain(true);
        SearchResponse searchResponse = builder.get();

        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits();
        Map<String, Object> map = new HashMap<String, Object>();
        SearchHit[] hits2 = hits.getHits();
        map.put("count", total);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit searchHit : hits2) {
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

            HighlightField highlightField = highlightFields.get("title");
            Map<String, Object> source = searchHit.getSource();
            if (highlightField != null) {
                Text[] fragments = highlightField.fragments();
                String name = "";
                for (Text text : fragments) {
                    name += text;
                }
                System.out.println(name);
                source.put("title", name);
            }

            HighlightField highlightField2 = highlightFields.get("comment");
            if (highlightField2 != null) {
                Text[] fragments = highlightField2.fragments();
                String comment = "";
                for (Text text : fragments) {
                    comment += text;
                }
                System.out.println(comment);
                source.put("comment", comment);
            }

            list.add(source);
        }
        map.put("dataList", list);
        return map;
    }

	public static void main(String[] args) {
		Map<String, Object> search = ElasticSearchUtil.search("好", "comments", "comment", 0, 10);
		List<Map<String, Object>> list = (List<Map<String, Object>>) search.get("dataList");
	}


}
