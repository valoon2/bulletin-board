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
			// ClassŬ������ forName�޼ҵ带 ����Ͽ� ����̹��� �ε�  **����̹� �ε��� ���α׷� ���� �� �ѹ��� �ʿ��ϴ�.
			// ����̹��� �����⸸ �ϸ� �ڵ����� DriverManager�� ��ϵȴ�.
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "select userPassword from user where userID = ?";
		try{
			pstmt = conn.prepareStatement(SQL); //SQL������ �����ϴ� ����
			pstmt.setString(1, userID); // ù��° ? �� userID ����
			rs = pstmt.executeQuery(); // ������ ������ ����� ResultSet �� ����
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // �α��� ����
				}
				else {
					return 0; // ��й�ȣ ����ġ
				}
			}
			rs.close();
			pstmt.close();
			conn.close();
			
			return -1; //���̵� ����
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return -2; // �����ͺ��̽� ����
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
		return -1; // �����ͺ��̽� ����
	}
	
	public User search(String userID) {
		String SQL = "select * from user where userID = ?";
		try{
			pstmt = conn.prepareStatement(SQL); //SQL������ �����ϴ� ����
			pstmt.setString(1, userID); // ù��° ? �� userID ����
			rs = pstmt.executeQuery(); // ������ ������ ����� ResultSet �� ����
			
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
