/**
 * ��ҵ��д��������
 * ��ҵ�������������
 * ���ײ���������⣨dirtyRead��
 */

package yxxy.c_008;

import java.util.concurrent.TimeUnit;

public class Account {
	String name;
	String balance;
	
	public synchronized void set(String name, String balance,Account a) {
		this.name = name;
		/*******************�̽�����ӸĶ�1************************/
		System.out.println(a.getBalance("zhangsan123"));
		/*******************************************/
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.balance = balance;

		this.name = name;
		/*******************�̽�����ӸĶ�2************************/
		System.out.println("���Ѿ����ú������");
		System.out.println(a.getBalance("zhangsan123456"));
// ����˯����Ϊ��֤������û���ͷŵ�һ������ʱ�����ɿ���ͨ������������ȡ���
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
		/************�̽����Ķ�4 ***********************/
//		����Ķ�4�е�����ע�͵�����ô���ȴ�ӡzhangsan898989898�û������ݣ���Ϊ���ע�͵���
//		��ô�߳����������У���û�л�ȡsynchronized����
//		���Ǳ�zhangsan898989898�����л�ȡ��synchronize��
//		�������Ի��ȴ�ӡzhangsan898989898null
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/************�̽����Ķ�4 ***********************/
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
* �����set������get����������synchronized ���Ļ�����ô��һ�����У��������������е�����һ������
* ���set�����л�ȡ�����������ôget�����Ͳ��ܻ�ȡ������ȴ������Ʋ��������⣬ֻ����һ������
* ���ǣ�set�����л�ȡ������������get��������ô�ǿ��Ի�ȡ���ģ���������������ɿ���ʹ�ò���������͡�
* */
