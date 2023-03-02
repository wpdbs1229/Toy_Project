package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HistoryService {
	//경도와 위도를 매개변수로 받고, LocalDateTime을 통해 조회한 시간을 저장
	public void insertHistory(double lat, double lnt){
	 
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

            String sql = "insert into history (x, y, inquiry_date) VALUES (?,?,?)";
            

            preparedStatement = con.prepareStatement(sql);
            int affectedRows =0;
             
	            	
	        LocalDateTime datetime = LocalDateTime.now();
	        DateTimeFormatter formmatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setDouble(1,lnt); 
			preparedStatement.setDouble(2,lat);
			preparedStatement.setString(3,datetime.toString());
	        affectedRows = preparedStatement.executeUpdate();
	        
      
            if (affectedRows >0 ){
                System.out.println("history 저장성공");
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
	
	// history 조회
	public List<History> selectHistory(){
		
		List<History> historyList = new ArrayList();
		
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

            String sql = "select * from history";

            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
            	History history = new History();
                
            	int id = rs.getInt("id");
            	double x = rs.getDouble("y");
            	double y = rs.getDouble("x");
            	String time = rs.getString("inquiry_date");
                
                history.setX(x);
                history.setY(y);
                history.setTime(time);
                history.setId(id);
                
                historyList.add(history);
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
		
		return historyList;
	}
	
	//삭제버튼을 누르면 history 삭제되게끔 id를 매개변수로 받아와 해당 row를 삭제
	public void deleteHistory(String id) {
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

	            String sql = "DELETE from history where id = ?";
	            
	        
	            preparedStatement = con.prepareStatement(sql);
	            
	            preparedStatement.setString(1, id);
	            int affectedRows = preparedStatement.executeUpdate();

	            if (affectedRows >0 ){
	                System.out.println("삭제성공");
	            } else {
	                System.out.println("삭제실패");
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
