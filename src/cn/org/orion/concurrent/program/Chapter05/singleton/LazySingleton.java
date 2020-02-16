package cn.org.orion.concurrent.program.Chapter05.singleton;

public class LazySingleton {
	private LazySingleton() {
		System.out.println("LazySingleton is create");
	}
	private static LazySingleton instance = null;
	public static synchronized LazySingleton getInstance() {
		if(instance == null)
			instance = new LazySingleton();
		return instance;
	}
	
	public static void main(String[] args) {
		System.out.println(LazySingleton.getInstance());
	}

}
