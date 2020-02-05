package cc.staralloff.bxcxjc.thread.Chapter04;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Atomic Reference Demo
 */
public class AtomicReferenceDemo {
	
	static AtomicReference<Integer> money = new AtomicReference<Integer>();
	public static void main(String[] args) {
		//set account initial value less than 20,obviously it's an account need be recharged.
		money.set(19);
		
		//monitor many threads update database backend at the same,to recharge the account.
		for(int i = 0; i < 3; i++) {
			new Thread() {
				public void run() {
					while(true) {
						while(true) {
							Integer m = money.get();
							if(m<20) {
								if(money.compareAndSet(m, m+20)) {
									System.out.println("余额小于20元，充值成功，余额："+money.get()+"元");
									break;
								}
							}else {
								//System.out.println("余额大于20元，无须充值");
								break;
							}
						}
					}
				}
			}.start();
		}
		
		new Thread() {
			public void run() {
				for(int i = 0; i < 100; i++) {
					while(true) {
						Integer m = money.get();
						if(m>10) {
							System.out.println("大于10元");
							if(money.compareAndSet(m, m-10)) {
								System.out.println("成功消费10元，余额："+money.get());
								break;
							}
						}else {
							System.out.println("没有足够的金额");
							break;
						}
					}
					try {
						Thread.sleep(100);
					}catch(InterruptedException e) {
						
					}
				}
			}
		}.start();
	}
	
	

}
