package cn.org.orion.concurrent.program.Chapter07.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutableMessage {
	private final int sequenceNumber;
	
	private final List<String> values;
	
	public ImmutableMessage(int sequenceNumber, List<String> values) {
		this.sequenceNumber = sequenceNumber;
		this.values = Collections.unmodifiableList(new ArrayList<String>(values));
	}
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	public List<String> getValues() {
		return values;
	}

}
