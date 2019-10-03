package top.qjyoung.concurrent;

import java.util.concurrent.TimeUnit;

public class TimeUnitTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main...begin");
        //睡眠13分钟
        // TimeUnit.MINUTES.sleep(13);
        //Thread.sleep(780000);//这样写你知道是多久吗？
        //Thread.sleep(13*60*1000);//这样写会稍微好些
        System.out.println(TimeUnit.SECONDS.toMinutes(3600));
        //睡眠1小时
        // TimeUnit.HOURS.sleep(1);
        //Thread.sleep(3600000);
        TimeUnit.SECONDS.sleep(5);
        
        TimeUnitTest test = new TimeUnitTest();
        
        Thread thread = new Thread(test::work);
        thread.start();
        //10秒内Join
        TimeUnit.SECONDS.timedJoin(thread, 5);
        //thread.join(10000);
        // LRUCache
        System.out.println("main .. end");
    }
    
    public synchronized void work() {
        System.out.println("Begin Work");
        try {
            //等待30秒后，自动唤醒继续执行
            TimeUnit.SECONDS.timedWait(this, 5);
            //wait(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Work End");
    }
}