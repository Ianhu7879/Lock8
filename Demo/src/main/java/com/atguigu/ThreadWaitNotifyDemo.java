package com.atguigu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author IanHu
 * @dare 2019/10/13 - 9:27
 */
class AirConditioner //资源类
{
    //判断
    private int number = 0;
    synchronized void increment() throws InterruptedException {
        while(number != 0){
            this.wait();
        }
        //干活
        number++;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        //执行
        this.notify();
    }
    synchronized void decrement() throws InterruptedException {
        //判断
        while(number == 0){
            this.wait();
        }
        //执行
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        //通知
        this.notify();
    }


}
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        //线程 控制 资源类
        AirConditioner airConditioner =  new AirConditioner();
        new Thread(() -> {
            for(int i = 1; i<=10; i++){
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"面包店").start();

        new Thread(() -> {
            for(int i = 1; i<=10; i++){
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"顾客1").start();

        new Thread(() -> {
            for(int i = 1; i<=10; i++){
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"面包店").start();

        new Thread(() -> {
            for(int i = 1; i<=10; i++){
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"顾客2").start();

    }
}
