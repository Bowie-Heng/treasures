package com.bowie.notes.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.junit.Test;

public class DocumentApis {

    @Test
    public void testGet(){
        GetResponse response = ESClient.CLIENT.transportClient.prepareGet("newproduct", "newproduct", "99876543").get();
        System.out.println(response.getSourceAsString());
    }

}
