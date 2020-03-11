package cn.org.orion.concurrent.program.Chapter06.paralstreamandsort;

import java.util.ArrayList;
import java.util.List;

class Student {
	private static int score;
	
	public Student(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}

public class GetParallelStream {
	public static void main(String[] args) {
		List<Student> ss = new ArrayList<Student>();
		GetParallelStream s = new GetParallelStream();
		/**
		 * this is a method to calculate one class's average,
		 * and then there must be a method to calculate the average of one major.
		 */
		// One class 40 Students,use ss.stream() put 40 Students' score into the stream,
		// Then use average() get One class's average score.
		// Then mapDouble ToInt
		//double ave = ss.stream().mapToInt(s->s.score().average().getAsDouble());
	}

}
