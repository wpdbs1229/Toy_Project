package db;


import java.time.LocalDateTime;

public class History {
	
	// 근처 Wifi를 조회할 때 마다 각 속성이 삽입됨 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	private int id;
	private double x;
	private double y;
	private String time;
	
	
	
}
