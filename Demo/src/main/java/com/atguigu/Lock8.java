package com.atguigu;

import org.omg.Messaging.SyncScopeHelper;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author IanHu
 * @dare 2019/10/12 - 21:23
 *  * 题目：多线程8锁
 *  * 1  标准访问，请问先打印邮件还是短信？
 *  * 2  邮件新增暂停4秒钟的方法，请问先打印邮件还是短信？
 *  结论:先打印邮件,某一时刻内,只要有一个线程调用了synchronize方法,锁是当前对象,被锁定后,其他线程都不能进入其他synchronize方法
 *  * 3  新增普通的hello方法，请问先打印邮件还是hello
 *  结论:先打印hello,普通方法和同步锁没有关系
 *  * 4  有两部手机，请问先打印邮件还是短信？
 *  结论:先发送短信,两个手机相当于两个实例对象,对象不同,线程互不影响
 *  * 5  两个静态同步方法，同一部手机，请问先打印邮件还是短信？
 *  结论:先发邮件,静态方法锁锁的是class的本身,由于在调用sendemail时,整个类都被锁住了,所以sendMSM需要等待
 *  * 6  两个静态同步方法，2部手机，请问先打印邮件还是短信？
 *  结论:先发邮件,线程一锁住了Phone类,所以线程二需要调用新的对象Phone对象时需要等待线程一释放锁后才可以使用
 *  * 7  1个静态同步方法,1个普通同步方法，1部手机，请问先打印邮件还是短信？
 *  结论:先打印普通方法,线程一static锁的是class锁, 普通的同步方法锁的是This实例对象,根本不相干
 *  * 8  1个静态同步方法,1个普通同步方法，2部手机，请问先打印邮件还是短信？
 *  结论:先送短信,静态锁住的是class,普通方法用的是对象实例的锁
 *
 */
class Phone{
    public static synchronized void sendEmail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("----sendEmail");
    }
    public synchronized void sendSMS(){
        System.out.println("----sendSMS");
    }
    public void sayHello(){
        System.out.println("----Hello");
    }
}
public class Lock8 {
    public static void main(String[] args) {
        //1  标准访问，请问先打印邮件还是短信？
/*        Phone phone1 = new Phone();
        new Thread(() -> {
            phone1.sendEmail();
        },"线程一").start();
        new Thread(() -> {
            phone1.sendSMS();
        },"线程二").start();
    }*/
        //2  邮件新增暂停4秒钟的方法，请问先打印邮件还是短信？
 /*       Phone phone1 = new Phone();
        new Thread(() -> {
            try {
                phone1.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程一").start();
        // 3  新增普通的hello方法，请问先打印邮件还是hello
        new Thread(() -> {
            phone1.sayHello();
        }, "线程二").start();
*/      // 4  有两部手机，请问先打印邮件还是短信？
        /* Phone phone1 = new Phone();
         Phone phone2 = new Phone();
         new Thread(() ->{
             try {
                 phone1.sendEmail();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         },"手机一").start();
         new Thread(() ->{phone2.sendSMS();},"手机二").start();*/
         //5  两个静态同步方法，同一部手机，请问先打印邮件还是短信？
        //6  两个静态同步方法，2部手机，请问先打印邮件还是短信？
      /*  Phone phone2 = new Phone();
        Phone phone1 = new Phone();
        new Thread(() -> {
            try {
                phone1.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程一").start();

        new Thread(() -> {
            phone2.sendSMS();
        },"线程二").start();
*/  //7  1个静态同步方法,1个普通同步方法，1部手机，请问先打印邮件还是短信？
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(()->{
            try {
                phone1.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程一").start();

        new Thread(()->{phone2.sendSMS();} ,"线程二").start();
    }
}
