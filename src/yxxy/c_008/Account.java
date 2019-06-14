/**
 * ��ҵ��д��������
 * ��ҵ�������������
 * ���ײ���������⣨dirtyRead��
 */

package yxxy.c_008;

import java.util.concurrent.TimeUnit;

public class Account {
	String name;
	String balance="0";
	
	public synchronized void set(String name, String balance,Account a) {
		this.name = name;

		//region �̽����Ķ�һ

//		System.out.println(a.getBalance("zhangsan123"));

		//endregion
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.balance = balance;
		this.name = name;

		//region �̽����Ķ���
//		System.out.println("���Ѿ����ú������");
//		System.out.println(a.getBalance("zhangsan123456"));
		//endregion

		//region �̽����Ķ���
		// �Ķ���������˯����Ϊ��֤������û���ͷŵ�һ������ʱ�����ɿ���ͨ������������ȡ���
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
		// �̽�����������a���������߳��У�Ϊ�˻�ȡͬһ�����еĶ���
		new Thread(()->a.set("zhangsan", "100",a)).start();

		//region �̽����Ķ���
		//	�������ע����������ŵ�sleep����ע�͵�����ô���ȴ�ӡzhangsan898989898�û������ݣ���Ϊ���ע�͵���
//		��ô�߳����������У���û�л�ȡsynchronized����
//		���Ǳ�zhangsan898989898�����л�ȡ��synchronize��
//		�������Ի��ȴ�ӡzhangsan898989898��0
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
* �����set������get����������synchronized ���Ļ�����ô��һ�����У��������������е�����һ������
* ���set�����л�ȡ�����������ôget�����Ͳ��ܻ�ȡ������ȴ������Ʋ��������⣬ֻ����һ������
* ���ǣ�set�����л�ȡ������������get��������ô�ǿ��Ի�ȡ���ģ���������������ɿ���ʹ�ò���������͡�
*
*
* Ϊ����ʾ��
* ע�⣺���ǽ�������a�����Ķ��������߳��У�������ʶ�����õ���һ��ͬһ��a�����е�balance�����������a���󣬸Ķ�һ�޷�ִ��
* 1�����ǽ�get�����е�synchronized ��ע�͡���ʱִ���������ݣ�������Ϊzhangsan898989898��û��set���ݣ���get �����ݡ�
* ��ӡ�����
zhangsan898989898:0
zhangsan:100
* 2��ȥ��get�����е�synchronizedע�ͣ�ɾ���Ķ�һ��ע�ͣ����Է�����set�����Ѿ���ȡ����ʱ�򣬿���ͨ����ȡ�������Ӷ���ȡget������ִ��Ȩ������ʱbalance����Ϊ0
* ��ӡ�����
zhangsan123:0
zhangsan898989898:100
zhangsan:100
* 3����ʱ�򿪸Ķ����͸Ķ�����������ʶset�����Ѿ���ȡ����ʱ�򣬿���ͨ����ȡ�������Ӷ���ȡget������ִ��Ȩ������ʱbalanceΪ100
* ��ӡ�����
zhangsan123:0
���Ѿ����ú������
zhangsan123456:100
zhangsan898989898:100
zhangsan:100

4����ʱ�����ոĶ��ĵ����ݲ�����
��ӡ�����
zhangsan898989898:0
zhangsan123:0
���Ѿ����ú������
zhangsan123456:100
zhangsan:100


��������Ѿ�֤������������������ͬһ����������
* */
