/**
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 容易产生脏读问题（dirtyRead）
 */

package yxxy.c_008;

import java.util.concurrent.TimeUnit;

public class Account {
	String name;
	String balance="0";
	
	public synchronized void set(String name, String balance,Account a) {
		this.name = name;

		//region 程金鹏改动一

//		System.out.println(a.getBalance("zhangsan123"));

		//endregion
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.balance = balance;
		this.name = name;

		//region 程金鹏改动二
//		System.out.println("我已经设置好了余额");
//		System.out.println(a.getBalance("zhangsan123456"));
		//endregion

		//region 程金鹏改动三
		// 改动三：增加睡眠是为了证明，在没有释放第一个锁的时候，依旧可以通过重入锁来读取余额
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		//endregion

	}
	
	public /*synchronized*/ String getBalance(String name) {
		return name+":" + this.balance;
	}
	
	
	public static void main(String[] args) {
		Account a = new Account();
		// 程金鹏将创建的a对象传入了线程中，为了获取同一个类中的对象
		new Thread(()->a.set("zhangsan", "100",a)).start();

		//region 程金鹏改动四
		//	如果将本注释下面紧挨着的sleep内容注释掉，那么会先打印zhangsan898989898用户的内容，因为如果注释掉后，
//		那么线程启动过程中，还没有获取synchronized锁，
//		但是被zhangsan898989898代码行获取了synchronize的
//		锁，所以会先打印zhangsan898989898：0
		//endregion
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
*
*
* 为了演示：
* 注意：我们将创建的a创建的对象传入了线程中，用来标识他们用的是一个同一个a对象中的balance。如果不传入a对象，改动一无法执行
* 1、我们将get方面中的synchronized 先注释。此时执行有脏数据，这是因为zhangsan898989898还没有set数据，就get 了数据。
* 打印结果：
zhangsan898989898:0
zhangsan:100
* 2、去掉get方法中的synchronized注释，删除改动一的注释，可以发现在set方法已经获取锁的时候，可以通过获取重入锁从而获取get方法的执行权力，此时balance依旧为0
* 打印结果：
zhangsan123:0
zhangsan898989898:100
zhangsan:100
* 3、此时打开改动二和改动三，用来标识set方法已经获取锁的时候，可以通过获取重入锁从而获取get方法的执行权力，此时balance为100
* 打印结果：
zhangsan123:0
我已经设置好了余额
zhangsan123456:100
zhangsan898989898:100
zhangsan:100

4、此时，按照改动四的内容操作：
打印结果：
zhangsan898989898:0
zhangsan123:0
我已经设置好了余额
zhangsan123456:100
zhangsan:100


最后，我们已经证明了重入锁。和类中同一把锁的问题
* */
