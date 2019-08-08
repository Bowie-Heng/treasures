package com.bowie.notes.redis;

import org.junit.Test;

public class ClusterClientTest {

    @Test
    public void testGet() {
        ClusterClient clusterClient = new ClusterClient();
        System.out.println(clusterClient.clusterClient.ttl("stage_GetProductSkuStockJob_lock_key"));
        System.out.println(clusterClient.clusterClient.get("stage_GetProductSkuStockJob_lock_key"));
        System.out.println(clusterClient.clusterClient.del("stage_GetProductSkuStockJob_lock_key"));

    }
}
