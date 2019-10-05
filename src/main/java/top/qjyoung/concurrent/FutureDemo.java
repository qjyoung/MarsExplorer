package top.qjyoung.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 乔健勇
 * @date 19:53 2019/10/5
 * @email qjyoung@163.com
 */
public class FutureDemo {
    static class Client extends Thread {
        private CompletableFuture<Integer> future;
        private int seconds;
        
        private Client(String threadName, CompletableFuture<Integer> future, int seconds) {
            super(threadName);
            this.future = future;
            this.seconds = seconds;
        }
        
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(seconds);
                System.out.println(this.getName() + ": " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        
        new Client("Client1", future, 2).start();
        new Client("Client2", future, 3).start();
        
        System.out.println("waiting");
        //设置Future.get()获取到的值
        future.complete(1000);
        
        //以异常的形式触发计算
//        future.completeExceptionally(new Exception());
        
        TimeUnit.SECONDS.sleep(1);
        
        System.out.println("main end");
        thenApply();
    }
    
    private static void thenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> s + " world").join();
        System.out.println(result);
    }
}
