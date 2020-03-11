package cn.org.orion.concurrent.program.Chapter06.stampedlock;

import java.util.concurrent.locks.StampedLock;

public class Point {
	private double x, y;
	private final StampedLock sl = new StampedLock();
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	void move(double deltaX, double deltaY) {
		// this is a exclusive lock
		long stamp = sl.writeLock();
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			sl.unlockWrite(stamp);
		}
	}
	
	double distanceFromOrigin() {
		long stamp = sl.tryOptimisticRead();
		double currentX = x, currentY = y;
		if (!sl.validate(stamp)) {
			stamp = sl.readLock();
			try {
				currentX = x;
				currentY = y;
			} finally {
				sl.unlockRead(stamp);
			}
		}
		return Math.sqrt(currentX * currentX + currentY * currentY); 
	}
	
	//calculate current point to O(0,0)
	public static void main(String[] args) {
		Point p = new Point(3.00, 4.00);
		p.move(3.00, 4.00);
		System.out.println(p.distanceFromOrigin());
	}

}
