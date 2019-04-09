package com.bowie.notes.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by Bowie on 2019/4/8 16:27
 **/
public enum CuratorUtils {

    INSTANCE();

    CuratorFramework client;

    public synchronized CuratorFramework getClient(String address) {

        if (client == null) {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

            CuratorFramework client = CuratorFrameworkFactory.builder()
                    .connectString(address)
                    .sessionTimeoutMs(3000)
                    .connectionTimeoutMs(5000)
                    .retryPolicy(retryPolicy)
                    .build();

            client.start();

            this.client = client;
        }

        return this.client;
    }
}
