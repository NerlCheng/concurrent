/**
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 容易产生脏读问题（dirtyRead）
 */

package yxxy.c_008;

import java.util.concurrent.TimeUnit;

public class Account {
	String name;
	String balance;
	
	public synchronized void set(String name, String balance,Account a) {
		this.name = name;
		/*******************程金鹏添加改动1************************/
		System.out.println(a.getBalance("zhangsan123"));
		/*******************************************/
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.balance = balance;

		this.name = name;
		/*******************程金鹏添加改动2************************/
		System.out.println("我已经设置好了余额");
		System.out.println(a.getBalance("zhangsan123456"));
// 增加睡眠是为了证明，在没有释放第一个锁的时候，依旧可以通过重入锁来读取余额
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*******************************************/
	}
	
	public synchronized String getBalance(String name) {
		return name + this.balance;
	}
	
	
	public static void main(String[] args) {
		Account a = new Account();
		new Thread(()->a.set("zhangsan", "100",a)).start();
		/************程金鹏改动4 ***********************/
//		如果改动4中的内容注释掉，那么会先打印zhangsan898989898用户的内容，因为如果注释掉后，
//		那么线程启动过程中，还没有获取synchronized锁，
//		但是被zhangsan898989898代码行获取了synchronize的
//		锁，所以会先打印zhangsan898989898null
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/************程金鹏改动4 ***********************/
		System.out.println(a.getBalance("zhangsan898989898"));
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(a.getBalance("zhangsan"));
	}
}


/*
* 如果对set方法和get方法都加入synchronized 锁的话，那么在一个类中，这这两个方法中的锁是一个锁。
* 如果set方法中获取了这个锁，那么get方法就不能获取这个锁等待。类似厕所的问题，只能有一个锁。
* 但是，set方法中获取了锁，并调用get方法，那么是可以获取锁的，这叫重入锁，依旧可以使用厕所问题解释。
* */
