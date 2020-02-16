package cn.org.orion.concurrent.program.Chapter05.nonelockconandpro;

public class PCData {
	private long value;
	public void set(long value) {
		this.value = value;
	}
	public long get() {
		return value;
	}
}
