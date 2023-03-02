package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class WifiService {
	
	//openAPI의 URL을 받아 JSONString 생성하는 함수
	public String getWifiString(int i) throws IOException {
		
        String start = String.valueOf(i);
        String end = String.valueOf(i+999);
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" +  URLEncoder.encode("694d524845646c773538536955756e","UTF-8") );
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") );
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode(start,"UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode(end,"UTF-8"));

        try{
            URL url = new URL (urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if (responseCode == 200){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(),"UTF-8"));
            }
            String inputLine;
            StringBuilder reponse = new StringBuilder();
            while ((inputLine = br.readLine()) != null){
                reponse.append(inputLine);
            }
            br.close();
            connection.disconnect();
            return reponse.toString();
        } catch (Exception e){
            return "failed to get response";
        }
    }
	
	//Wifi 테이블의 전체 row수를 출력하는 함수
	public int countDB() {
		int rowcount = 0;
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
            	
            String sql = "select count(1) from wifi";
            	
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            
            
            if(rs.next()) {
            	rowcount = rs.getInt(1);
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
        
		return rowcount;
	}
    
	//받은 JSonString을 파싱하고 이를 WIFI에 테이블 삽입하는 함수
	public void insertDB(String jsonString){

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject)jsonParser.parse(jsonString);
        }catch (ParseException e){
            throw new RuntimeException();
        }
        Map<String, Object> resultMap = new HashMap<>();

        JSONObject TbPublicWifiInfoData = (JSONObject)jsonObject.get("TbPublicWifiInfo");
        JSONArray jsonArray = (JSONArray) TbPublicWifiInfoData.get("row");

        for (int i = 0; i <jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);

        }

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

            String sql = "insert into wifi ("
                    + "X_SWIFI_MGR_NO,"
                    + "X_SWIFI_WRDOFC,"
                    + "X_SWIFI_MAIN_NM,"
                    + "X_SWIFI_ADRES1,"
                    + "X_SWIFI_ADRES2,"
                    + "X_SWIFI_INSTL_FLOOR,"
                    + "X_SWIFI_INSTL_TY,"
                    + "X_SWIFI_INSTL_MBY,"
                    + "X_SWIFI_SVC_SE,"
                    + "X_SWIFI_CMCWR,"
                    + "X_SWIFI_CNSTC_YEAR,"
                    + "X_SWIFI_INOUT_DOOR,"
                    + "X_SWIFI_REMARS3,"
                    + "LAT,"
                    + "LNT,"
                    + "WORK_DTTM)"
                    + "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            

            preparedStatement = con.prepareStatement(sql);
            int affectedRows =0;
            if (jsonArray.size() > 0){
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject  jsonObject1 = (JSONObject) jsonArray.get(i);
                    preparedStatement.setString(1,(String)jsonObject1.get("X_SWIFI_MGR_NO"));
                    preparedStatement.setString(2,(String)jsonObject1.get("X_SWIFI_WRDOFC"));
                    preparedStatement.setString(3,(String) jsonObject1.get("X_SWIFI_MAIN_NM"));
                    preparedStatement.setString(4,(String) jsonObject1.get("X_SWIFI_ADRES1"));
                    preparedStatement.setString(5,(String) jsonObject1.get("X_SWIFI_ADRES2"));
                    preparedStatement.setString(6,(String) jsonObject1.get("X_SWIFI_INSTL_FLOOR"));
                    preparedStatement.setString(7,(String) jsonObject1.get("X_SWIFI_INSTL_TY"));
                    preparedStatement.setString(8,(String) jsonObject1.get("X_SWIFI_INSTL_MBY"));
                    preparedStatement.setString(9,(String) jsonObject1.get("X_SWIFI_SVC_SE"));
                    preparedStatement.setString(10,(String) jsonObject1.get("X_SWIFI_CMCWR"));
                    preparedStatement.setString(11,(String) jsonObject1.get("X_SWIFI_CNSTC_YEAR"));
                    preparedStatement.setString(12,(String) jsonObject1.get("X_SWIFI_INOUT_DOOR"));
                    preparedStatement.setString(13,(String) jsonObject1.get("X_SWIFI_REMARS3"));
                    preparedStatement.setString(14,(String) jsonObject1.get("LAT"));
                    preparedStatement.setString(15,(String) jsonObject1.get("LNT"));
                    preparedStatement.setString(16,(String) jsonObject1.get("WORK_DTTM"));
                    affectedRows = preparedStatement.executeUpdate();
                }
            }

            		

            if (affectedRows >0 ){
                System.out.println("wifi저장성공");
            } else {
                System.out.println("wifi저장실패");
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
    
    //Wifi 테이블을 조회해 List형태로 반환
    public List<Wifi> selectDB(){

        List<Wifi> wifiList = new ArrayList<>();
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

            String sql = "SELECT * from wifi";

            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Wifi wifi = new Wifi();
                
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
                
                wifi.setX_SWIFT_MGR_NO(X_SWIFI_MGR_NO);
                wifi.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);;
                wifi.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
                wifi.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
                wifi.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
                wifi.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
                wifi.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
                wifi.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
                wifi.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
                wifi.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
                wifi.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
                wifi.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
                wifi.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
                wifi.setLAT(LAT);
                wifi.setLNT(LNT);
                wifi.setWORK_DTTM(WORK_DTTM);
                
                wifiList.add(wifi);
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
        return wifiList;
    }

 
}
