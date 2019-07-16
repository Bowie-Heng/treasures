package com.bowie.notes.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum ESClient {

    CLIENT("172.16.111.59","172.16.111.60","172.16.111.61");

    TransportClient transportClient;

    ESClient(String... hosts){
        Settings esSetting = Settings.builder()
                .put("cluster.name", "b5c")
                //In order to enable sniffing,
                .put("client.transport.sniff", true)
                .put("thread_pool.search.size", 5)
                .build();

        TransportClient transportClient = new PreBuiltTransportClient(esSetting);
        try {
            for (String host : hosts) {
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.transportClient = transportClient;
    }
}
