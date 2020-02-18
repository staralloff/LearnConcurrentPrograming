package cn.org.orion.concurrent.program.Chapter05.future;

public class FutureData implements Data {
	protected RealData realdata = null;
	protected boolean isReady = false;
	
	public synchronized void setRealData(RealData realdata) {
		if(isReady) {
			return;
		}
		this.realdata = realdata;
		isReady = true;
		notifyAll();
	}

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
