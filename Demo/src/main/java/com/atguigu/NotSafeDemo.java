package com.atguigu;

import org.omg.Messaging.SyncScopeHelper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author IanHu
 * @dare 2019/10/12 - 19:08
 *

 * 1    故障现象
         *java.util.ConcurrentModificationException
         * 2    导致原因
         *
         * 3    解决方法
         *  3.1 Vector()
         *  3.2 Collections.synchronizedList();
         *  3.3 CopyOnWriteArrayList ---->  读写分离，写时复制
 */
public class NotSafeDemo {

    public static void main(String[] args) {
        //ArrayList线程是不安全的 异常ConcurrentModificationException
 /*        final List list = new ArrayList();
       for (int i = 1 ; i <= 3 ; i++){
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,7));
                System.out.println(list);
            },String.valueOf(i)).start();
        }*/
        //安全线程,读写分离
/*        List list = new CopyOnWriteArrayList();
        for(int i = 1 ; i <= 30 ; i ++){
            //多线程操作集合,生成随机数字
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }*/

        //通过集合的工具类,把三种不安全的集合包装成安全的
        //第一种:包装ArrayList集合
/*        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for(int i = 1; i <= 30 ; i++){
            list.add(UUID.randomUUID().toString().substring(0,6));
            System.out.println(list);
        }*/
        //第二种:包装Set集合
/*        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        for(int i = 1; i <= 300 ; i++){
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println(set);
            },"set集合").start();

        }*/
        //第三种:包装Map集合
       Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 1; i <=30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,6));
                System.out.println(map);
            },String.valueOf(i)).start();

        }

    }
}
