package cn.org.orion.concurrent.program.Chapter06.basic;

public interface IDonkey {
	void eat();
	default void run() {
		System.out.println("Donkey run");
	}
}
