package cn.org.orion.concurrent.program.Chapter05.nonelockconandpro;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class Producer {
	private final RingBuffer<PCData> ringBuffer;
	
	public Producer(RingBuffer<PCData> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	
	public void pushData(ByteBuffer bytebuf) {
		long sequence = ringBuffer.next();    // Grab the next sequence
		try {
			PCData event = ringBuffer.get(sequence);  // Get the entry in the Disruptor
			                                  // for the sequence
			event.set(bytebuf.getLong(0));  // Fill with data
		}finally {
			ringBuffer.publish(sequence);
		}
	}

}