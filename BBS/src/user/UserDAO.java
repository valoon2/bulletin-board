package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bbs.BbsDAO;

public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver"); 
			// Class클래스의 forName메소드를 사용하여 드라이버를 로드  **드라이버 로딩은 프로그램 수행 시 한번만 필요하다.
			// 드라이버가 읽히기만 하면 자동으로 DriverManager에 등록된다.
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "select userPassword from user where userID = ?";
		try{
			pstmt = conn.prepareStatement(SQL); //SQL구문을 실행하는 역할
			pstmt.setString(1, userID); // 첫번째 ? 에 userID 삽입
			rs = pstmt.executeQuery(); // 쿼리를 실행한 결과를 ResultSet 에 삽입
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				}
				else {
					return 0; // 비밀번호 불일치
				}
			}
			rs.close();
			pstmt.close();
			conn.close();
			
			return -1; //아이디가 없음
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "insert into USER values(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public User search(String userID) {
		String SQL = "select * from user where userID = ?";
		try{
			pstmt = conn.prepareStatement(SQL); //SQL구문을 실행하는 역할
			pstmt.setString(1, userID); // 첫번째 ? 에 userID 삽입
			rs = pstmt.executeQuery(); // 쿼리를 실행한 결과를 ResultSet 에 삽입
			
			User user = new User();
			if(rs.next()) {
				user.setUserID(rs.getString(1));
				user.setUserPassword(rs.getString(2));
				user.setUserName(rs.getString(3));
				user.setUserGender(rs.getString(4));
				user.setUserEmail(rs.getString(5));
				
				return user;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(User user, String userID) {
		String SQL = "update user set userID = ?, userPassword = ?, userName = ?, userGender = ?, userEmail = ? where userID = ?";
		try{
			
			if(!user.getUserID().equals(userID)) {
				BbsDAO bbsDAO = new BbsDAO();
				bbsDAO.updateUser(userID, user.getUserID());
			}
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			pstmt.setString(6, userID);
			
			return pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}

}
