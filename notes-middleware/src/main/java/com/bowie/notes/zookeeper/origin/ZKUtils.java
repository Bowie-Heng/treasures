package com.bowie.notes.zookeeper.origin;

import org.apache.zookeeper.*;

/**
 * Created by Bowie on 2019/4/8 14:55
 **/
public class ZKUtils {
    // create static instance for zookeeper class.
    private ZooKeeper zk;

    // Method to create znode in zookeeper ensemble
    public void create(String path, byte[] data) throws
            KeeperException, InterruptedException {
        //path是要创建的地址
        //data是地址中要存放的数据
        //ZooDefs.Ids.OPEN_ACL_UNSAFE表示的是当前节点的权限（world）
        //CreateMode.PERSISTENT是节点类型，这里是临时节点的意思
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    public void exists(String path) throws
            KeeperException, InterruptedException {
        zk.exists(path, true);
    }

    public void delete(String path) throws
            KeeperException, InterruptedException {
        zk.delete(path,zk.exists(path,true).getVersion());
    }


    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
