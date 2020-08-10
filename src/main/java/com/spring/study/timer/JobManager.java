package com.spring.study.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Java 原生定时任务的一些学习总结归纳
 */
public class JobManager {


    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    static ScheduledExecutorService scheduledExecutorService2 = Executors.newScheduledThreadPool(3);



    static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式


    /*
     * 每隔15秒运行一次
     *
     * */
    public static void doDealTimer() {

        scheduledExecutorService.scheduleWithFixedDelay(() -> {

            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("打印时间：" + df.format(new Date()));

        }, 0, 5, TimeUnit.SECONDS);

    }

    /*
     * 每隔十秒运行一次
     * */
    public static void doDealTimer2() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("打印时间：" + df.format(new Date()));

        }, 0, 5, TimeUnit.SECONDS);
    }


    /*
     * 多线程调用方式
     *
     * */
    public static void doDealTimer3() {

        scheduledExecutorService2.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("第1份线程");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "11111" + "打印时间：" + df.format(new Date()));
        }, 0, 3, TimeUnit.SECONDS);

        scheduledExecutorService2.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("第2份线程");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "22222" + "打印时间：" + df.format(new Date()));
        }, 0, 3, TimeUnit.SECONDS);

    }

    /*
     *
     *  runtimeException 打断定时任务执行
     *  cancle 方法
     * ]*/
    public static void dealTimer4() {

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + "44444" + "打印时间：" + df.format(new Date()));
        }, 0, 3, TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 9) {
                System.out.println("打断线程");

                //true 强行打断  false 等待执行完强行打断
                scheduledFuture.cancel(false);

            }
        }
    }
/*
*  runtimerException
* 会打断异常
*
*
* try catch 保证定时任务继续执行
* */
    public  static  void dealTimer5(){
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + "44444" + "打印时间：" + df.format(new Date()));
            int i =  new Random().nextInt(100);

            try {
                if (i % 5 == 0) {
                    throw new RuntimeException("整除5 异常");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {

        JobManager.dealTimer5();

    }


}
