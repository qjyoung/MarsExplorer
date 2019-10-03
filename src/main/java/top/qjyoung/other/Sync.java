package top.qjyoung.other;

/**
 * @author QiaoJianYong
 * @date 10:51 PM 2018/12/8
 * @email chinaqiaojianyong@gmail.com
 */
public class Sync {
    public static Integer num = 0;
    
    public static void main(String[] args) {
        new Thread(new DecreaseTask()).start();
        new Thread(new DecreaseTask()).start();
        new Thread(new AddTask()).start();
        new Thread(new AddTask()).start();
    }
}

class DecreaseTask implements Runnable {
    
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
//            synchronized (Sync.num) {
                System.out.println(Sync.num + "-1=" + --Sync.num);
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class AddTask implements Runnable {
    
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
//            synchronized (Sync.num) {
                System.out.println(Sync.num + "+1=" + ++Sync.num);
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
