package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit test for simple App.
 */
public class AppTest {
    static int count = 0;
    @BeforeClass
    public static void before(){
        Runtime.getRuntime().addShutdownHook(new Thread(()-> System.out.println("JVM exit.")));
    }


    public static void main(String[] argd) throws InterruptedException {
        Thread t = new Thread(()->{
            try {
                AppTest.test();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.setDaemon(false);
        t.start();
        System.exit(0);
    }
    @Test
    public void testJunitExitEvenIfThreadIsNotDeamon() throws InterruptedException {
        test();
    }
    public static void test() throws InterruptedException {
        Runnable r = () -> {
            System.out.println(Thread.currentThread() + ":start:"+Thread.currentThread().isDaemon());
            for (int i = 0; i < 1_000_000; i++) {
                count = count+1;
            }
            System.out.println(Thread.currentThread() + ":stop:"+Thread.currentThread().isDaemon());
        };
        System.out.println(Thread.currentThread().getName()+":"+Thread.currentThread().isDaemon());
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);
        //t1.setDaemon(true);
        //t2.setDaemon(true);
        //t3.setDaemon(true);
        t1.start();
        t2.start();
        t3.start();
        System.out.println(count);
    }
}
