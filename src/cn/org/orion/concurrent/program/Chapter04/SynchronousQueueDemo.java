package cn.org.orion.concurrent.program.Chapter04;

import java.util.concurrent.SynchronousQueue;

/*
 * @Chinese 利用SynchronousQueue同步队列实现线程间通信
 */
public class SynchronousQueueDemo {
	
	static class Person {
		String name;
		Integer age;
		
		public Person(String name, Integer age) {
			this.name = name;
			this.age = age;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		final SynchronousQueue<Object> queue = new SynchronousQueue<Object>();
		
		Thread putThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("put thread start");
				try {
					queue.put(new Person("某某某", 16));
				}catch(InterruptedException e) {
					
				}
				System.out.println("put thread end");
			}
		});
		
		Thread takeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("take thread start");
				try {
					System.out.println("take from putThread:" + queue.take());
				}catch(InterruptedException e) {
					
				}
				System.out.println("take thread end");
			}
		});
		
		putThread.start();
		Thread.sleep(1000);
		takeThread.start();
	}
}
