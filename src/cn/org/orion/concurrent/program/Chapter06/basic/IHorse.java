package cn.org.orion.concurrent.program.Chapter06.basic;

public interface IHorse {
	void eat();
	default void run() {
		System.out.println("horse run");
	}
}
