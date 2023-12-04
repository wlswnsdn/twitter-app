package com.example.myapplication.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	private String login_id;
	private String pw;
	private String username;
	private String profile_img;
	private String email;
	private String interesting;
	private String user_description;

	public User(String login_id, String pw, String username, String profile_img, String email, String interesting,
			String user_description) {
		this.login_id = login_id;
		this.pw = pw;
		this.username = username;
		this.profile_img = profile_img;
		this.email = email;
		this.interesting = interesting;
		this.user_description = user_description;
	}

	public User() {

	}

	public void create(Connection con, PreparedStatement pstm, String login_id, String pw, String username, String profile_img, String email, String interesting,
					   String user_description)  {
		String sql = "INSERT INTO user (login_id, pw, username, profile_img, email, interesting, user_description) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, login_id);
			pstm.setString(2, pw);
			pstm.setString(3, username);
			pstm.setString(4, profile_img);
			pstm.setString(5, email);
			pstm.setString(6, interesting);
			pstm.setString(7, user_description);
			pstm.executeUpdate();
		} catch(Exception e){
			System.out.println("e.getMessage() = " + e.getMessage());
		}
	} // create, 유저 개인 정보를 입력하면 SQL의 user 테이블에 스키마로 저장하고, user_id를 auto increment로
		// 생성합니다.

	public void read(Connection con, PreparedStatement pstm, int user_id)  {
		String sql = "SELECT * FROM user WHERE user_id = ?";
		try  {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, user_id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				String login_id = rs.getString("login_id");
				String pw = rs.getString("pw");
				String username = rs.getString("username");
				String profile_img = rs.getString("profile_img");
				String email = rs.getString("email");
				String interesting = rs.getString("interesting");
				String user_description = rs.getString("user_description");

				System.out.println("Login_id: " + login_id);
				System.out.println("Password: " + pw);
				System.out.println("Username: " + username);
				System.out.println("Profile Image: " + profile_img);
				System.out.println("Email: " + email);
				System.out.println("Interesting: " + interesting);
				System.out.println("User Description: " + user_description);
			} else {
				System.out.println("No user found with ID: " + user_id);
			}
		} catch(SQLException e){
			System.out.println("e.getMessage() = " + e.getMessage());
		}
	} // read, user_id를 입력하면 개인정보를 반환하는 방식입니다.

	public void update(Connection con, PreparedStatement pstm, int user_id, String Login_id, String Pw, String Username, String Profile_img, String Email,
			String Interesting, String User_description)  {
		String sql = "UPDATE user SET login_id = ?, pw = ?, username = ?, profile_img = ?, email = ?, interesting = ?, user_description = ? WHERE user_id = ?";
		try  {

			pstm = con.prepareStatement(sql);
			pstm.setString(1, Login_id);
			pstm.setString(2, Pw);
			pstm.setString(3, Username);
			pstm.setString(4, Profile_img);
			pstm.setString(5, Email);
			pstm.setString(6, Interesting);
			pstm.setString(7, User_description);
			pstm.setInt(8, user_id);
			pstm.executeUpdate();
		} catch(SQLException e){
			System.out.println("e.getMessage() = " + e.getMessage());
		}
	} // update, 매개변수로 수정 값을 받아올 수 있도록 수정.

	public void delete(Connection con, PreparedStatement pstm, int user_id, String pw) {
		String sql = "DELETE FROM user WHERE user_id = ? AND pw = ?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, user_id);
			pstm.setString(2, pw);
			int rowsAffected = pstm.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("User deleted successfully.");
			} else {
				System.out.println("No user found. Please try again.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // delete, user_id를 사용하는게 맞나 싶기는 한데 login_id로도 변경할 수 있습니다.

	public boolean login(Connection con, PreparedStatement pstm, String login_id, String pw) {
		String sql = "SELECT * FROM user WHERE login_id = ? AND pw = ?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, login_id);
			pstm.setString(2, pw);

			ResultSet rs = pstm.executeQuery();
			return rs.next();
		} catch(SQLException e){
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		return false;
	}
	// log in, 이제 login_id와 pw를 받아야합니다. 성공하면 true를 반환합니다.

	public void block(Connection con, PreparedStatement pstm, int myId, int ban_id) {
		String sql = "INSERT INTO ban (iban, youbanned) VALUES (?, ?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, myId);
			pstm.setInt(2, ban_id);
			pstm.executeUpdate();
		} catch(SQLException e){
			System.out.println("e.getMessage() = " + e.getMessage());
		}
	}

	public void changePW(Connection con, PreparedStatement pstm, int user_id, String changePW){
		String sql ="update user set pw=? where user_id=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,changePW);
			pstm.setInt(2, user_id);

		}catch(SQLException e){
			e.printStackTrace();
		}
	}
} // block, 유저 ID와 차단할 유저ID를 받아와서 DB에 저장함.