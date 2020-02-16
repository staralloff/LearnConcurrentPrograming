package cn.org.orion.concurrent.program.Chapter05.nonelockconandpro;

import com.lmax.disruptor.EventFactory;

public class PCDataFactory implements EventFactory<PCData> {
	@Override
	public PCData newInstance() {
		return new PCData();
	}
}
