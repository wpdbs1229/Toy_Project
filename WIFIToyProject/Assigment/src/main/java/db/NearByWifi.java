package db;

public class NearByWifi {
	
	// distanceWifi로 부터 계산된 거리를 받고 Wifi에서 각 속성값을 저장받는 객체
	public String getX_SWIFT_MGR_NO() {
		return X_SWIFT_MGR_NO;
	}
	public void setX_SWIFT_MGR_NO(String x_SWIFT_MGR_NO) {
		X_SWIFT_MGR_NO = x_SWIFT_MGR_NO;
	}
	public String getX_SWIFI_WRDOFC() {
		return X_SWIFI_WRDOFC;
	}
	public void setX_SWIFI_WRDOFC(String x_SWIFI_WRDOFC) {
		X_SWIFI_WRDOFC = x_SWIFI_WRDOFC;
	}
	public String getX_SWIFI_MAIN_NM() {
		return X_SWIFI_MAIN_NM;
	}
	public void setX_SWIFI_MAIN_NM(String x_SWIFI_MAIN_NM) {
		X_SWIFI_MAIN_NM = x_SWIFI_MAIN_NM;
	}
	public String getX_SWIFI_ADRES1() {
		return X_SWIFI_ADRES1;
	}
	public void setX_SWIFI_ADRES1(String x_SWIFI_ADRES1) {
		X_SWIFI_ADRES1 = x_SWIFI_ADRES1;
	}
	public String getX_SWIFI_ADRES2() {
		return X_SWIFI_ADRES2;
	}
	public void setX_SWIFI_ADRES2(String x_SWIFI_ADRES2) {
		X_SWIFI_ADRES2 = x_SWIFI_ADRES2;
	}
	public String getX_SWIFI_INSTL_FLOOR() {
		return X_SWIFI_INSTL_FLOOR;
	}
	public void setX_SWIFI_INSTL_FLOOR(String x_SWIFI_INSTL_FLOOR) {
		X_SWIFI_INSTL_FLOOR = x_SWIFI_INSTL_FLOOR;
	}
	public String getX_SWIFI_INSTL_TY() {
		return X_SWIFI_INSTL_TY;
	}
	public void setX_SWIFI_INSTL_TY(String x_SWIFI_INSTL_TY) {
		X_SWIFI_INSTL_TY = x_SWIFI_INSTL_TY;
	}
	public String getX_SWIFI_INSTL_MBY() {
		return X_SWIFI_INSTL_MBY;
	}
	public void setX_SWIFI_INSTL_MBY(String x_SWIFI_INSTL_MBY) {
		X_SWIFI_INSTL_MBY = x_SWIFI_INSTL_MBY;
	}
	public String getX_SWIFI_SVC_SE() {
		return X_SWIFI_SVC_SE;
	}
	public void setX_SWIFI_SVC_SE(String x_SWIFI_SVC_SE) {
		X_SWIFI_SVC_SE = x_SWIFI_SVC_SE;
	}
	public String getX_SWIFI_CMCWR() {
		return X_SWIFI_CMCWR;
	}
	public void setX_SWIFI_CMCWR(String x_SWIFI_CMCWR) {
		X_SWIFI_CMCWR = x_SWIFI_CMCWR;
	}
	public String getX_SWIFI_CNSTC_YEAR() {
		return X_SWIFI_CNSTC_YEAR;
	}
	public void setX_SWIFI_CNSTC_YEAR(String x_SWIFI_CNSTC_YEAR) {
		X_SWIFI_CNSTC_YEAR = x_SWIFI_CNSTC_YEAR;
	}
	public String getX_SWIFI_INOUT_DOOR() {
		return X_SWIFI_INOUT_DOOR;
	}
	public void setX_SWIFI_INOUT_DOOR(String x_SWIFI_INOUT_DOOR) {
		X_SWIFI_INOUT_DOOR = x_SWIFI_INOUT_DOOR;
	}
	public String getX_SWIFI_REMARS3() {
		return X_SWIFI_REMARS3;
	}
	public void setX_SWIFI_REMARS3(String x_SWIFI_REMARS3) {
		X_SWIFI_REMARS3 = x_SWIFI_REMARS3;
	}
	public String getLAT() {
		return LAT;
	}
	public void setLAT(String lAT) {
		LAT = lAT;
	}
	public String getLNT() {
		return LNT;
	}
	public void setLNT(String lNT) {
		LNT = lNT;
	}
	public String getWORK_DTTM() {
		return WORK_DTTM;
	}
	public void setWORK_DTTM(String wORK_DTTM) {
		WORK_DTTM = wORK_DTTM;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	private  String X_SWIFT_MGR_NO;
	private  String X_SWIFI_WRDOFC;
    private  String X_SWIFI_MAIN_NM;
    private  String X_SWIFI_ADRES1;
    private  String X_SWIFI_ADRES2;
    private  String X_SWIFI_INSTL_FLOOR;
    private  String X_SWIFI_INSTL_TY;
    private  String X_SWIFI_INSTL_MBY;
    private  String X_SWIFI_SVC_SE;
    private  String X_SWIFI_CMCWR;
    private  String X_SWIFI_CNSTC_YEAR;
    private  String X_SWIFI_INOUT_DOOR;
    private  String X_SWIFI_REMARS3;
    private  String LAT;
    private  String LNT;
    private  String WORK_DTTM;
    private  String count;
    private double distance;
}