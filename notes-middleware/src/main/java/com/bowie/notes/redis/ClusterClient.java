package com.bowie.notes.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class ClusterClient {

    JedisCluster clusterClient;

    public ClusterClient(){
        HostAndPort hostAndPort = new HostAndPort("redis1.stage.com", 6001);
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        hostAndPortSet.add(hostAndPort);
        clusterClient = new JedisCluster(hostAndPortSet);
    }
}
