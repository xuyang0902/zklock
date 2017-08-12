import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xu.qiang
 * @date 17/6/21
 */
public class CuratorTest {

    CuratorFramework curatorFramework;

    @Before
    public void init() {
        //1 重试策略：重试时间为0s 重试10次  [默认重试策略:无需等待一直抢，抢10次］
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(0, 10);

        //2 通过工厂创建连接
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.110:2181")
                .sessionTimeoutMs(1000) //会话超时时间
                .retryPolicy(retryPolicy)
                .namespace("curator")
                .build();
        //3 开启连接
        curatorFramework.start();

        System.out.println("curatorFramework start ...");
    }

    @Test
    public void testCreate() throws Exception {
        //创建节点 带数据 默认是永久的节点
        //curatorFramework.create().forPath("/test01","test01".getBytes());

        //创建永久的有序节点
        //curatorFramework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/test02","with mode persistent seq02".getBytes());

        //创建临时节点
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/test03", "haha".getBytes());

        Thread.sleep(Integer.MAX_VALUE);
        /**
         * 临时节点的存活时间
         * 会话存活时间
         */


        //

    }

    @Test
    public void setData() throws Exception {
        //更新节点数据
        curatorFramework.setData().forPath("/test01", "test01_update".getBytes());
    }

    @Test
    public void delete() throws Exception {
        curatorFramework.delete().forPath("/test020000000003");
    }


    @Test
    public void nodeCache() throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        final NodeCache nodeCache = new NodeCache(curatorFramework, "/test01", false);

        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点数据变化 --->" + new String(nodeCache.getCurrentData().getData()));

            }
        }, executorService);


        Thread.sleep(Integer.MAX_VALUE);
    }


    @Test
    public void test() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/", false);

        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);

        pathChildrenCache.getCurrentData();
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {

                System.out.println("－－－》" + event.toString());


            }
        }, executorService);


        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testList() throws Exception {
        List<String> strings = curatorFramework.getChildren().forPath("/");

        for(String s : strings){
            System.out.println(s);
        }
    }


    @After
    public void after() {
        curatorFramework.close();

        System.out.println("curatorFramework cloese ...");
    }





}