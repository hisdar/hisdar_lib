package cn.hisdar.lib.ui;

public class HRect {

	public int x;
	public int y;
	public int width;
	public int height;
	
	public HRect() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
	}
	
	public HRect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setRect(HRect rect) {
		this.x = rect.x;
		this.y = rect.y;
		this.width = rect.width;
		this.height = rect.height;
	}
	
	public void setRect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getEndX() {
		return x + width;
	}
	
	public int getEndY() {
		return y + height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "HRect [x=" + x + ", y=" + y + ", width=" + width + ", height="
				+ height + "]";
	}
	
	public String getRectString() {
		return "HRect [x=" + x + ", y=" + y + ", endX=" + getEndX() + ", endY="
				+ getEndY() + "]";
	}
}

