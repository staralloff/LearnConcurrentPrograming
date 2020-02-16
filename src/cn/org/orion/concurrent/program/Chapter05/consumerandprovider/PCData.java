package cn.org.orion.concurrent.program.Chapter05.consumerandprovider;

public final class PCData {
	private final int intData;
	
	public PCData(int d) {
		this.intData = d;
	}
	
	public PCData(String d) {
		intData=Integer.valueOf(d);
	}

	public int getData() {
		return intData;
	}
	
	@Override
	public String toString() {
		return "data:"+intData;
	}
}
