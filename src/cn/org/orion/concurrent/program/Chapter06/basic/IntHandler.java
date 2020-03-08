package cn.org.orion.concurrent.program.Chapter06.basic;

@FunctionalInterface
public interface IntHandler {
	void handle(int i);
	boolean equals(Object obj);
}
