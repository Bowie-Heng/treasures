package com.bowie.notes.elasticsearch;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.junit.Test;

import java.util.Iterator;

public class SearchApis {

    SearchRequestBuilder searchRequestBuilder = ESClient.CLIENT.transportClient.prepareSearch("newproduct").setTypes("newproduct");

    @Test
    public void testGet() {
        GetResponse response = ESClient.CLIENT.transportClient.prepareGet("newproduct", "newproduct", "99876543").get();
        System.out.println(response.getSourceAsString());
    }

    @Test
    public void testPrepareSearch() {
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //1.查询全部 match All
        queryByBuilder(matchAllQueryBuilder);
    }

    @Test
    public void testFullTextSearch() {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("descriptionType.name", "品牌");

        //2.full text queries - match query
        queryByBuilder(matchQueryBuilder);

        //3.full text queries - multi match query
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("品牌", "descriptionType.name", "descriptionType.name2");
        queryByBuilder(multiMatchQueryBuilder);

        //4.Common Terms Query
        CommonTermsQueryBuilder commonTermsQueryBuilder = QueryBuilders.commonTermsQuery("descriptionType.name", "品牌");
        queryByBuilder(commonTermsQueryBuilder);

    }

    @Test
    public void testTermSearch() {
        //term query
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("categoryDetail.catId", "4");
        queryByBuilder(termQueryBuilder);

        //terms query
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("categoryDetail.catId", "4", "5");
        queryByBuilder(termsQueryBuilder);

    }

    @Test
    public void testCompoundSearch() {
        //bool query
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("categoryDetail.catId", "4"));
        boolQueryBuilder.must(QueryBuilders.termQuery("skuPriceList.countryId", "1"));
        queryByBuilder(boolQueryBuilder);
    }

    @Test
    public void testJoinQuery() {
        //has child Query
        HasChildQueryBuilder hasChildQueryBuilder = JoinQueryBuilders.hasChildQuery("skuPriceList", QueryBuilders.termQuery("countryId", "1"), ScoreMode.Max);
        queryByBuilder(hasChildQueryBuilder);
    }

    @Test
    public void testAggregations() {

        //聚合查询的相对来说比较麻烦
        SearchRequestBuilder aggregationRequestBuild = ESClient.CLIENT.transportClient.prepareSearch("newproduct").setTypes("skuStock");

        //构造where语句
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("skuId", "8000000301"));

        //构造group by语句
        TermsAggregationBuilder groupByBuilder = AggregationBuilders.terms("skuIds")
                .field("skuId").order(Terms.Order.aggregation("skuStock_sum", true)).size(100);

        //构造sum函数
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("skuStock_sum").field("skuStock");

        //group by 和sum的关联关系在这里赋值
        groupByBuilder.subAggregation(sumAggregationBuilder);

        //构造请求
        SearchRequestBuilder searchRequestBuilder = aggregationRequestBuild.setQuery(boolQueryBuilder)
                .addAggregation(groupByBuilder);

        //打印请求的json
        System.out.println(searchRequestBuilder.toString());

        //执行请求
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        //打印返回内容的json
        System.out.println(searchResponse.toString());

        //聚合的时候,是聚合成一个个的skuId
        Terms terms = searchResponse.getAggregations().get("skuIds");

        //获取每一个聚合出来的内容,打印他里面sum函数出来的内容
        for (Terms.Bucket bucket : terms.getBuckets()) {
            System.out.println(String.format("聚合出的key = %s", bucket.getKeyAsString()));
            //获取sum函数出来的内容
            Sum sum = bucket.getAggregations().get("skuStock_sum");
            System.out.println(String.format("聚合出的skuStock_sum结果为:%s", sum.getValue()));
        }
    }


    private void queryByBuilder(AbstractQueryBuilder builder) {
        searchRequestBuilder.setQuery(builder);
        System.out.println(String.format("请求参数为:%s", searchRequestBuilder.toString()));
        SearchResponse searchResponse = searchRequestBuilder.get();
        printResult(searchResponse);
    }

    private void printResult(SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        System.out.println(String.format("查询结果总共有:%s条", hits.getTotalHits()));
        System.out.println("查询结果如下");
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }


}
