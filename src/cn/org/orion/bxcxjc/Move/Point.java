package cn.org.orion.bxcxjc.Move;

import cn.org.orion.bxcxjc.VirusBroadcast.MoveTarget;

public class Point {
	private int x;
	private int y;
	private MoveTarget moveTarget;
    int sig = 1;

    
    double targetXU;
    double targetYU;
    double targetSig = 50;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
