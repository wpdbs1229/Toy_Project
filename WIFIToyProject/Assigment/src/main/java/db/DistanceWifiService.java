package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class DistanceWifiService {
	
	// jsp에서 계산되어진 거리와 wifi 테이블로부터 와이파이 관리번호을 받아 DistanceWifi로 저장
	 public void insertDistanceWifi(List<Double> distanceList){
		 	
		 	WifiService wifiservice = new WifiService();
		 	java.util.List<Wifi> wifiList = new ArrayList<>();
		 	wifiList = wifiservice.selectDB();
		 	
		 	
		 	
	        
	        String url =  url = "jdbc:sqlite:" + "C:\\dev\\sqlite-tools-win32-x86-3410000\\wifi.db";
	        try {
	            Class.forName("org.sqlite.JDBC");



	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        
	        
	        
	        Connection con = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            con = DriverManager.getConnection(url);

	            String sql = "insert into distancewifi(WIFI_MGR_NO, distance) VALUES (?,?)";
	            

	            preparedStatement = con.prepareStatement(sql);
	            int affectedRows =0;
	             
	     
	            for (int i = 0; i<wifiList.size(); i++) {
		            	
		            	Wifi wifi = wifiList.get(i);
		            	double distance = distanceList.get(i);
		            	preparedStatement.setString(1,wifi.getX_SWIFT_MGR_NO());
						preparedStatement.setDouble(2,distance); 
		            	affectedRows = preparedStatement.executeUpdate();
		        }
	      
	            if (affectedRows >0 ){
	                System.out.println("저장성공");
	            } else {
	                System.out.println("저장실패");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {

	            try {
	                if (preparedStatement != null && !preparedStatement.isClosed()){
	                    preparedStatement.close();
	                }
	            } catch (SQLException e) {
	                throw new RuntimeException(e);
	            }
	            try {
	                if (con != null && !con.isClosed()){
	                    con.close();
	                }
	            } catch (SQLException e) {
	                throw new RuntimeException(e);
	            }
	        }
	        
	       
	    }
	 
	 // DistanceWifi의 값과 distnace(거리)를 계산해 각 속성 NearByWifiList로 저장해 반환  
	 public List<NearByWifi> selectDistanceWifi() {
		 	List<NearByWifi> nearbywifiList = new ArrayList<>();
	        String url =  url = "jdbc:sqlite:" + "C:\\dev\\sqlite-tools-win32-x86-3410000\\wifi.db";

	        try {
	            Class.forName("org.sqlite.JDBC");



	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        Connection con = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet rs = null;

	        try {
	            con = DriverManager.getConnection(url);

	            String sql = "select x_swifi_mgr_no,"
	            		+"x_swifi_wrdofc,"
	            		+"x_swifi_main_nm,"
	            		+"x_swifi_adres1,"
	            		+"x_swifi_adres2,"
	            		+"x_swifi_instl_floor,"
	            		+"x_swifi_instl_ty,"
	            		+"x_swifi_instl_mby,"
	            		+"x_swifi_svc_se," 
	            		+"x_swifi_cmcwr,"
	            		+"x_swifi_cnstc_year,"
	            		+"x_swifi_inout_door,"
	            		+"x_swifi_remars3,"
	            		+"lat,"
	            		+"lnt,"
	            		+"work_dttm,"
	            		+"distance"
	            		+" from wifi w"
	            	    +" join distancewifi d on w.X_SWIFI_MGR_NO = d.WIFI_MGR_NO"
	            	    +" order by d.distance"
	            	    +" limit 1, 20";

	            preparedStatement = con.prepareStatement(sql);
	            rs = preparedStatement.executeQuery();

	            while (rs.next()){
	                NearByWifi nearbywifi = new NearByWifi();
	                
	                String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
	                String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
	                String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
	                String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
	                String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_MGR_NO");
	                String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
	                String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
	                String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY");
	                String X_SWIFI_CMCWR= rs.getString("X_SWIFI_MGR_NO");
	                String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
	                String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
	                String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
	                String LAT = rs.getString("LAT");
	                String LNT = rs.getString("LNT");
	                String WORK_DTTM = rs.getString("WORK_DTTM");
	                double distance = rs.getDouble("distance");
	                nearbywifi.setX_SWIFT_MGR_NO(X_SWIFI_MGR_NO);
	                nearbywifi.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);;
	                nearbywifi.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
	                nearbywifi.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
	                nearbywifi.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
	                nearbywifi.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
	                nearbywifi.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
	                nearbywifi.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
	                nearbywifi.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
	                nearbywifi.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
	                nearbywifi.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
	                nearbywifi.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
	                nearbywifi.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
	                nearbywifi.setLAT(LAT);
	                nearbywifi.setLNT(LNT);
	                nearbywifi.setWORK_DTTM(WORK_DTTM);
	                nearbywifi.setDistance(distance);
	                
	                nearbywifiList.add(nearbywifi);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {
	        	try {
	        		if(!rs.isClosed()) {
	        			rs.close();
	        		}
	        	}catch (SQLException e) {
	        		throw new RuntimeException(e);
	        	}
	            try {
	                if (preparedStatement != null && !preparedStatement.isClosed()){
	                    preparedStatement.close();
	                }
	            } catch (SQLException e) {
	                throw new RuntimeException(e);
	            }
	            try {
	                if (con != null && !con.isClosed()){
	                    con.close();
	                }
	            } catch (SQLException e) {
	                throw new RuntimeException(e);
	            }
	        }
	        return nearbywifiList;
	 }
	 
	 //distanceWifi에 들어있는 row들을 모두 삭제
	 public  void deleteDistanceWifi() {
	        String url =  url = "jdbc:sqlite:" + "C:\\dev\\sqlite-tools-win32-x86-3410000\\wifi.db";
	        try {
	            Class.forName("org.sqlite.JDBC");



	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        Connection con = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            con = DriverManager.getConnection(url);

	            String sql = "DELETE from distancewifi";
	            
	        
	            preparedStatement = con.prepareStatement(sql);
	            
	          
	            int affectedRows = preparedStatement.executeUpdate();

	            if (affectedRows >0 ){
	                System.out.println("distanceWifi삭제성공");
	            } else {
	                System.out.println("distanceWifi삭제실패");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {

	            try {
	                if (preparedStatement != null && !preparedStatement.isClosed()){
	                    preparedStatement.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            try {
	                if (con != null && !con.isClosed()){
	                    con.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    
	}

}
