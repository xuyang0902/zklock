import com.yuren.zk.lock.core.ZkDistributedLock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xu.qiang
 * @date 17/6/13
 */
public class ZkLockTest {


    public static void main(String[] args) {

        final ZkDistributedLock lock = new ZkDistributedLock("192.168.1.110:2181", 5000, "zk_lock");

        ExecutorService executorService = Executors.newFixedThreadPool(8);

        final  AtomicInteger integer = new AtomicInteger(0);
        for (int index = 0; index < 16; index++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    try {

                        int i = integer.incrementAndGet();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
                        boolean acquire = lock.acquire("1992", 5, TimeUnit.SECONDS);

                        if (acquire) {

                            System.out.println(Thread.currentThread().getName() + "-->获取到zk锁 -->"+dateFormat.format(new Date()) + " || "+ i);

                            Thread.sleep(1000 * 2);
                        } else {
                            System.out.println(Thread.currentThread().getName() + "-->没有获取到zk锁 -->"+dateFormat.format(new Date())+ " || "+ i);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.release();
                    }

                }
            });
        }


    }
}
