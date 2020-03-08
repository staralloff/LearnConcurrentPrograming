package cn.org.orion.concurrent.program.Chapter06.basic;

public interface IAnimal {
	default void breath() {
		System.out.println("breath");
	}
}
