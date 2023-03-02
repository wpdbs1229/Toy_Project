package db;

public class DistanceWifi {
	
	// DistanceWifi란? 거리와 와이파이 관리번호를 가지고있음 와이파이 관리번호를 통해 wifi 테이블과 join해 근처 와이파이를 보여줌
	public String getX_SWIFI_MGR_NO() {
		return X_SWIFI_MGR_NO;
	}
	public void setX_SWIFI_MGR_NO(String x_SWIFI_MGR_NO) {
		X_SWIFI_MGR_NO = x_SWIFI_MGR_NO;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	private double distance;
	private String X_SWIFI_MGR_NO;
		
}
