package com.example.demo.util.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.alibaba.fastjson.JSON;
import com.example.demo.entity.IndexSearch;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ElasticsearchDemo {

    private final static String serverUrl = "http://123.207.210.78:9200";

    public static void main(String[] args) throws Exception {
        // Create the low-level client
        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl)).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        BooleanResponse booleanResponse = esClient.indices().exists(c -> c
                .index("customer"));
        //创建索引
        if(!booleanResponse.value()){
            esClient.indices().create(c -> c
                    .index("customer")
            );
        }

        //新增文档
        IndexSearch indexSearch = new IndexSearch(1, "李", "文武");
        IndexSearch indexSearch2 = new IndexSearch(2, "孙", "文武");
        IndexSearch indexSearch3 = new IndexSearch(3, "张", "文武");
        IndexResponse response = esClient.index(i -> i
                .index("customer")
                .id(indexSearch.getId().toString())
                .document(indexSearch)
        );
        esClient.index(i -> i
                .index("customer")
                .id(indexSearch2.getId().toString())
                .document(indexSearch2)
        );
        esClient.index(i -> i
                .index("customer")
                .id(indexSearch3.getId().toString())
                .document(indexSearch3)
        );
        log.info("Indexed with version " + response.version());
        //根据id查询
        GetResponse<IndexSearch> response2 = esClient.get(g -> g
                        .index("customer")
                        .id("1"),
                IndexSearch.class
        );
        if (response2.found()) {
            IndexSearch product = response2.source();
            log.info("Product name " + product.getFirstname());
        } else {
            log.info ("Product not found");
        }


        //查询
        String searchText = "武";
        SearchResponse<IndexSearch> response3 = esClient.search(s -> s
                        .index("customer")
                        .query(q -> q
                                .match(t -> t
                                        .field("lastname")
                                        .query(searchText)
                                )
                        ),
                IndexSearch.class
        );
        log.info("response3:" + Arrays.toString(response3.hits().hits().toArray()));
        List<Hit> result = new ArrayList<>(response3.hits().hits());
        for(Hit hit:result){
            System.out.println(JSON.parseObject(JSON.toJSONString(hit.source())));
        }
        //更新文档
        IndexSearch product = new IndexSearch(1, "孙", "武");
        esClient.update(u -> u
                        .index("customer")
                        .id("1").doc(product)
                        .upsert(product),
                IndexSearch.class
        );
        //删除文档
        esClient.delete(d -> d.index("customer").id("1"));
        //删除索引
//        esClient.indices().delete(c -> c
//                .index("customer")
//        );
    }

}
