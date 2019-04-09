package com.bowie.notes.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Bowie on 2019/4/8 18:06
 * 监听器的使用
 **/
public class WatcherTest {

    @Test
    public void nodeCacheTest() throws Exception {

        CuratorFramework client = CuratorUtils.INSTANCE.getClient("localhost");

        final NodeCache watcher = new NodeCache(client, "/Korea");

        watcher.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                System.out.println(Arrays.toString(watcher.getCurrentData().getData()));
            }
        });

        try {
            watcher.start(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(500000);
    }


    @Test
    public void pathChildrenCacheTest() throws Exception {

        CuratorFramework client = CuratorUtils.INSTANCE.getClient("localhost");

        final PathChildrenCache cache = new PathChildrenCache(client, "/Korea", true);

        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {

                switch (event.getType()) {

                    case CHILD_ADDED:

                        System.out.println("CHILD_ADDED," + event.getData().getPath());

                        break;

                    case CHILD_UPDATED:

                        System.out.println("CHILD_UPDATED," + event.getData().getPath());

                        break;

                    case CHILD_REMOVED:

                        System.out.println("CHILD_REMOVED," + event.getData().getPath());

                        break;

                    default:

                        break;

                }
            }
        });

        Thread.sleep(500000);
    }


}
