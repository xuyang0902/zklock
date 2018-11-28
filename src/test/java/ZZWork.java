//package com.yuren.zk.lock.core;
//
//import javax.xml.ws.FaultAction;
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author xu.qiang
// * @date 17/8/3
// */
//public class ZZWork {
//
////    @Autowired
//    private ZkDistributedLock zkDistributedLock;
//
//    /**
//     *
//     * @param userId 用户id
//     * @param redId 红包id
//     * @return
//     */
//    public Map<String,Object>   tryRobMoney(String userId,String redId,BigDecimal redRemainAmout){
//
//
//        Map<String,Object> map = new HashMap<String,Object>();
//        boolean acquire = false;
//
//        try {
//            acquire = zkDistributedLock.acquire(redId, 1, TimeUnit.SECONDS);
//            if(!acquire){
//                //快速失败 1秒还没有抢到红包锁
//                map.put("status",-1);
//                map.put("model","抢购的人数太多啦，挤爆了，再抢一次!");
//
//                /**
//                 * 因为http接口调用会有超时时间，这个地方你可以选择自旋 等待一直抢到锁。。但是客户端可能需要调整，看具体的交互
//                 */
//                return map;
//            }
//
//            //抢到锁了 随机生成一个
//            BigDecimal randomMoney = null;
//            if(redRemainAmout.compareTo(BigDecimal.TEN) < 0 ){
//                //红包小于10 尾单  最后一个
//                randomMoney = redRemainAmout;
//            }else {
//                //随机生成一个
//                randomMoney = new BigDecimal(new Random().nextInt(10));
//            }
//
//
//            //
//            /**
//             * 执行这段sql
//             *
//             * update a_table
//             * set reamin_money = remain_oney - #{randomMoney}
//             * where redId = #redId and remain_money >= #{randomMoney}
//             */
//
//
//            int updateNum = 1;
//
//            if(updateNum > 0){
//                //如果上一段创建userId的用户 抢红包记录
//
//
//                //查询。。。
//                map.put("status",1);
//                map.put("model","卧槽  恭喜你已经抢到XXX,共有XX人抢到，还剩XXX");
//
//                return map;
//            }else{
//
//                //如果上一段创建userId的用户 抢红包记录
//
//                map.put("status",-1);
//                map.put("model","抱歉，您的手比较慢，没有抢到啊 啊 啊啊   欢迎明天再来");
//
//                return map;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            log......
//        }finally {
//            zkDistributedLock.release();
//        }
//
//
//
//
//
//
//
//
//        return null;
//    }
//
//
//}
