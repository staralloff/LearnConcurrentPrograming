package cn.org.orion.bxcxjc.Move;

public class Ellipse {
	private double x;
	private double y;
	private double a;
	private double b;
	private double c;
	
	public Ellipse(double x, double y, double a, double b, double c) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
	
	public boolean isEllipse(double x, double y, double a, double b, double c) {
		if(x*x/a+y*y/b == c*c) {
			return true;
		}else {
			return false;
		}
	}
	
}
