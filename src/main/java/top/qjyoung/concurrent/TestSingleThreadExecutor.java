package top.qjyoung.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestSingleThreadExecutor {
    public static void main(String[] args) throws InterruptedException {
        //创建一个可重用固定线程数的线程池
        // ExecutorService pool = Executors.newFixedThreadPool(2);
        
        ExecutorService pool = new ThreadPoolExecutor(
                2, 6, 20, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2),
                new ThreadPoolExecutor.CallerRunsPolicy());
        // new ThreadPoolExecutor();
        //创建实现了Runnable接口对象
        Thread tt1 = new MyThread("线程1：我由核心线程执行");
        Thread tt2 = new MyThread("线程2：我由核心线程执行");
        Thread tt3 = new MyThread("线程3：我进入缓存队列");
        Thread tt4 = new MyThread("线程4：我进入缓存队列");
        Thread tt5 = new MyThread("线程5：我由非核心线程执行");
        
//        Runnable task = () -> System.out.println("task .....");
    
        //将线程放入池中并执行
        pool.submit(tt1);
        pool.execute(tt2);
        pool.execute(tt3);
        pool.execute(tt4);
        pool.submit(tt5);
        
        // pool.execute(tt4);
        // System.out.println("6.....");
        // pool.execute(tt5);
        //关闭
        // TimeUnit.SECONDS.sleep(10);
        
        pool.shutdown();
        /*
         输出结果为
         pool-1-thread-2线程2 我由核心线程执行 is running.
         pool-1-thread-1线程1 我由核心线程执行 is running.
         pool-1-thread-3线程5 我由非核心线程执行 is running.
         pool-1-thread-2线程3 我进入缓存队列 is running.
         pool-1-thread-1线程4 我进入缓存队列 is running.
         
         pool-1-thread-1线程1 我由核心线程执行 is running.
         pool-1-thread-3线程5 我由非核心线程执行 is running.
         pool-1-thread-2线程2 我由核心线程执行 is running.
         pool-1-thread-2线程3 我进入缓存队列 is running.
         pool-1-thread-1线程4 我进入缓存队列 is running.
         
         */
    }
    
    public static class MyThread extends Thread {
        private String name;
        
        public MyThread(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " " + name + " is running.");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
线程池是怎样处理某一个运行任务的。
1、首先可以通过线程池提供的submit()方法或者execute()方法，要求线程池执行某个任务。线程池收到这个要求执行的任务后，会有几种处理情况：
1.1、如果当前线程池中运行的线程数量还没有达到corePoolSize大小时，线程池会创建一个新的线程运行你的任务，无论之前已经创建的线程是否处于空闲状态。
1.2、如果当前线程池中运行的线程数量已经达到设置的corePoolSize大小，线程池会把你的这个任务加入到等待队列中。直到某一个的线程空闲了，线程池会根据设置的等待队列规则，从队列中取出一个新的任务执行。
1.3、如果根据队列规则，这个任务无法加入等待队列。这时线程池就会创建一个“非核心线程”直接运行这个任务。注意，如果这种情况下任务执行成功，那么当前线程池中的线程数量一定大于corePoolSize。
1.4、如果这个任务，无法被“核心线程”直接执行，又无法加入等待队列，又无法创建“非核心线程”直接执行，且你没有为线程池设置RejectedExecutionHandler。这时线程池会抛出RejectedExecutionException异常，即线程池拒绝接受这个任务。（实际上抛出RejectedExecutionException异常的操作，是ThreadPoolExecutor线程池中一个默认的RejectedExecutionHandler实现：AbortPolicy，这在后文会提到）
2、一旦线程池中某个线程完成了任务的执行，它就会试图到任务等待队列中拿去下一个等待任务（所有的等待任务都实现了BlockingQueue接口，按照接口字面上的理解，这是一个可阻塞的队列接口），它会调用等待队列的poll()方法，并停留在哪里。
3、当线程池中的线程超过你设置的corePoolSize参数，说明当前线程池中有所谓的“非核心线程”。那么当某个线程处理完任务后，如果等待keepAliveTime时间后仍然没有新的任务分配给它，那么这个线程将会被回收。线程池回收线程时，对所谓的“核心线程”和“非核心线程”是一视同仁的，直到线程池中线程的数量等于你设置的corePoolSize参数时，回收过程才会停止。

注意 只有corePoolSize+队列长度<maximumPoolSize才会创建非核心线程

创建一个线程池需要输入几个参数：

corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。如果调用了线程池的prestartAllCoreThreads方法，线程池会提前创建并启动所有基本线程。
runnableTaskQueue（任务队列）：用于保存等待执行的任务的阻塞队列。 可以选择以下几个阻塞队列。
ArrayBlockingQueue：是一个基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO （先进先出） 排序元素，吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool()使用了这个队列。
SynchronousQueue：一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法Executors.newCachedThreadPool使用了这个队列。
PriorityBlockingQueue：一个具有优先级的无限阻塞队列。
maximumPoolSize（线程池最大大小）：线程池允许创建的最大线程数。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是如果使用了无界的任务队列这个参数就没什么效果。
ThreadFactory：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
RejectedExecutionHandler（饱和策略）：当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常。以下是JDK1.5提供的四种策略。
AbortPolicy：直接抛出异常。
CallerRunsPolicy：只用调用者所在线程来运行任务。
DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。
DiscardPolicy：不处理，丢弃掉。
当然也可以根据应用场景需要来实现RejectedExecutionHandler接口自定义策略。如记录日志或持久化不能处理的任务。
keepAliveTime（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。所以如果任务很多，并且每个任务执行的时间比较短，可以调大这个时间，提高线程的利用率。
TimeUnit（线程活动保持时间的单位）：可选的单位有天（DAYS），小时（HOURS），分钟（MINUTES），毫秒(MILLISECONDS)，微秒(MICROSECONDS, 千分之一毫秒)和毫微秒(NANOSECONDS, 千分之一微秒)。

线程池的优缺点
线程复用
控制最大并发数
管理线程
缺点、死锁、资源不足、线程泄露、并发错误、请求过载
使用线程池的风险
虽然线程池是构建多线程应用程序的强大机制，但使用它并不是没有风险的。用线程池构建的应用程序容易遭受任何其它多线程应用程序容易遭受的所有并发风险，诸如同步错误和死锁，它还容易遭受特定于线程池的少数其它风险，诸如与池有关的死锁、资源不足和线程泄漏。
1。死锁
任何多线程应用程序都有死锁风险。当一组进程或线程中的每一个都在等待一个只有该组中另一个进程才能引起的事件时，我们就说这组进程或线程死锁 了。死锁的最简单情形是：线程 A 持有对象 X 的独占锁，并且在等待对象 Y 的锁，而线程 B 持有对象 Y 的独占锁，却在等待对象 X 的锁。除非有某种方法来打破对锁的等待（Java 锁定不支持这种方法），否则死锁的线程将永远等下去。
虽然任何多线程程序中都有死锁的风险，但线程池却引入了另一种死锁可能，在那种情况下，所有池线程都在执行已阻塞的等待队列中另一任务的执行结果的任务， 但这一任务却因为没有未被占用的线程而不能运行。当线程池被用来实现涉及许多交互对象的模拟，被模拟的对象可以相互发送查询，这些查询接下来作为排队的任 务执行，查询对象又同步等待着响应时，会发生这种情况。
2.资源不足
线程池的一个优点在于：相对于其它替代调度机制（有些我们已经讨论过）而言，它们通常执行得很好。但只有恰当地调整了线程池大小时才是这样的。线程消耗包括内存和其它系统资源在内的大量资源。除了Thread 对象所需的内存之外，每个线程都需要两个可能很大的执行调用堆栈。除此以外，JVM 可能会为每个 Java 线程创建一个本机线程，这些本机线程将消耗额外的系统资源。最后，虽然线程之间切换的调度开销很小，但如果有很多线程，环境切换也可能严重地影响程序的性能。
如果线程池太大，那么被那些线程消耗的资源可能严重地影响系统性能。在线程之间进行切换将会浪费时间，而且使用超出比您实际需要的线程可能会引起资源匮乏 问题，因为池线程正在消耗一些资源，而这些资源可能会被其它任务更有效地利用。除了线程自身所使用的资源以外，服务请求时所做的工作可能需要其它资源，例 如 JDBC 连接、套接字或文件。这些也都是有限资源，有太多的并发请求也可能引起失效，例如不能分配 JDBC 连接。
3、并发错误
线程池和其它排队机制依靠使用wait() 和 notify() 方法，这两个方法都难于使用。如果编码不正确，那么可能丢失通知，导致线程保持空闲状态，尽管队列中有工作要处理。使用这些方法时，必须格外小心；即便是专家也可能在它们上面出错。而最好使用现有的、已经知道能工作的实现，例如在下面的 无需编写自己的线程池中讨论的util.concurrent 包。
4、线程泄漏
各种类型的线程池中一个严重的风险是线程泄漏，当从池中除去一个线程以执行一项任务，而在任务完成后该线程却没有返回池时，会发生这种情况。发生线程泄漏的一种情形出现在任务抛出一个RuntimeException 或一个 Error 时。如果池类没有捕捉到它们，那么线程只会退出而线程池的大小将会永久减少一个。当这种情况发生的次数足够多时，线程池最终就为空，而且系统将停止，因为没有可用的线程来处理任务。
有些任务可能会永远等待某些资源或来自用户的输入，而这些资源又不能保证变得可用，用户可能也已经回家了，诸如此类的任务会永久停止，而这些停止的任务也 会引起和线程泄漏同样的问题。如果某个线程被这样一个任务永久地消耗着，那么它实际上就被从池除去了。对于这样的任务，应该要么只给予它们自己的线程，要 么只让它们等待有限的时间。
请求过载
仅仅是请求就压垮了服务器，这种情况是可能的。在这种情形下，我们可能不想将每个到来的请求都排队到我们的工作队列，因为排在队列中等待执行的任务可能会 消耗太多的系统资源并引起资源缺乏。在这种情形下决定如何做取决于您自己；在某些情况下，您可以简单地抛弃请求，依靠更高级别的协议稍后重试请求，您也可 以用一个指出服务器暂时很忙的响应来拒绝请求。

有效使用线程池的准则
只要您遵循几条简单的准则，线程池可以成为构建服务器应用程序的极其有效的方法：
• 不要对那些同步等待其它任务结果的任务排队。这可能会导致上面所描述的那种形式的死锁，在那种死锁中，所有线程都被一些任务所占用，这些任务依次等待排队任务的结果，而这些任务又无法执行，因为所有的线程都很忙。
• 在为时间可能很长的操作使用合用的线程时要小心。如果程序必须等待诸如 I/O 完成这样的某个资源，那么请指定最长的等待时间，以及随后是失效还是将任务重新排队以便稍后执行。这样做保证了：通过将某个线程释放给某个可能成功完成的任务，从而将最终取得某些 进展。
• 理解任务。要有效地调整线程池大小，您需要理解正在排队的任务以及它们正在做什么。它们是 CPU 限制的（CPU-bound）吗？它们是 I/O 限制的（I/O-bound）吗？您的答案将影响您如何调整应用程序。如果您有不同的任务类，这些类有着截然不同的特征，那么为不同任务类设置多个工作队 列可能会有意义，这样可以相应地调整每个池。

问题
工作线程数是不是设置的越大越好？
调用sleep()函数的时候，线程是否一直占用CPU？ wait（）呢


单核CPU，设置多线程有意义么，是否能提高并发性能

线程数不是越多越好
sleep()不占用CPU
单核设置多线程不但能使得代码清晰，还能提高吞吐量

N核服务器，通过日志分析出任务执行过程中，本地计算时间为x，等待时间为y，则工作线程数（线程池线程数）设置为 N*(x+y)/x，能让CPU的利用率最大化


*/