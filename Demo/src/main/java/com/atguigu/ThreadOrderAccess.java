package com.atguigu;

import org.omg.Messaging.SyncScopeHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author IanHu
 * @dare 2019/10/13 - 12:25
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 *
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ......来10轮
 *
 * 1    高聚低合前提下，线程操作资源类
 * 2    判断/干活/通知
 * 3    多线防止多线程的虚假唤醒，也即（判断只用while，不能用if）
 * 4    注意判断标志位的更新
 *
 */
class ShareResource
{
    private int flag = 1; // A , B ,C
    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();
    //1.判断
    public void APrint5() throws InterruptedException {
        lock.lock();
        while(flag !=1){
            c1.await();
        }
        //2.干活
        for(int i = 1 ; i <= 5 ; i++){
            System.out.println(Thread.currentThread().getName() + "\t"+ i );
        }
        //3.通知
        flag = 2;
        c2.signal();
        lock.unlock();
    }

    public void APrint10() throws InterruptedException {
        lock.lock();
        //1.判断
        while(flag != 2){
            c2.await();
        }
        //2.干活
        for(int i = 1 ; i <= 10 ; i++){
            System.out.println(Thread.currentThread().getName() + "\t"+ i );
        }
        //3.通知
        flag = 3;
        c3.signal();
        lock.unlock();
    }
    public void APrint15() throws InterruptedException {
        lock.lock();
        //1.判断
        while(flag != 3){
            c3.await();
        }
        //2.干活
        for(int i = 1 ; i <= 15 ; i++){
            System.out.println(Thread.currentThread().getName() + "\t"+ i );
        }
        //3.通知
        flag = 1;
        c1.signal();
        lock.unlock();
    }





}
public class ThreadOrderAccess {
    public static void main(String[] args) {
        //线程 操作 资源类
        ShareResource shareResource = new ShareResource();
        new Thread(()->{
            try {
                for (int i = 1 ; i <= 10 ; i++){
                    shareResource.APrint5();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(()->{
            try {
                for (int i = 1 ; i <= 10 ; i++){
                    shareResource.APrint10();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        new Thread(()->{
            try {
                for (int i = 1 ; i <= 10 ; i++){
                    shareResource.APrint15();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();
    }

}
