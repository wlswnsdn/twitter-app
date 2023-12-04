package com.example.myapplication.post;



import com.example.myapplication.dto.PostDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Post {
	
	// create
	// insert할때는 preparestatement, select할때는 statement
	private int post_id;
	private int user_id;
	private int tag_user_id;
	private int postlike_cnt;
	private int comment_id;
	private int retweet_cnt;
	private Timestamp create_time;

	private String postcontent;

	private String post_img;

	List<PostDTO> postDTOList=null;
		 public void write(Connection con, PreparedStatement pstm, int user_id, int tag_user_id, String postcontent, String post_img ) {
			
			String sql;
			postlike_cnt=0;
			retweet_cnt=0;

			try {
				sql="INSERT INTO post (user_id, tag_user_id, postlike_cnt, create_time, retweet_cnt, postcontent, post_img) VALUES (?, ?, ?, CURRENT_TIME(), ?, ?, ?)";
				pstm= con.prepareStatement(sql);

				pstm.setInt(1, user_id);
				pstm.setInt(2, tag_user_id);
				pstm.setInt(3, postlike_cnt);
				pstm.setInt(4, retweet_cnt);
				pstm.setString(5, postcontent);
				pstm.setString(6, post_img);

				pstm.executeUpdate();


			}catch (SQLException e){
				e.printStackTrace();
			}
			
		}

		 //post_id, user_id, tag_user_id, postlike_cnt, comment_id, create_time, retweet_cnt, postcontent, post_img
//	// read
//		//read my post.post
//		public List<PostDTO> findMyPost(Statement stmt, int myId){
//			 String sql;
//			 try{
//				 sql="select * from post where user_id="+myId+" order by create_time desc";
//				 ResultSet rs=stmt.executeQuery(sql);
//				 return showPost(rs);
//
//
//			 }catch (SQLException e){
//				 e.printStackTrace();
//			 }
//			return null;
//		}
//
//
//		//read my friend's post (차단 안 한 애들만)
//
//		public List<PostDTO> findFriendsPost(Connection con,PreparedStatement pstm, int myId){
//			String sql;
//			ResultSet rs;
//			List<Integer> following = null;
//			try{
//				sql="select * from post where user_id in (select target_user_id from following " +
//						"where following_id=? )  and user_id not in (select youbanned from ban where iban=?) " +
//						"order by create_time desc";
//				pstm=con.prepareStatement(sql);
//				pstm.setInt(1,myId);
//				pstm.setInt(2,myId);
//				rs = pstm.executeQuery();
//				return showPost(rs);
//
//			}catch(SQLException e){
//				System.out.println("e.getMessage() = " + e.getMessage());
//			}
//			return null;
//		}

//	private List<PostDTO> showPost(ResultSet rs) throws SQLException {
//		postDTOList = new ArrayList<>();
//			 while(rs.next()) {
//			PostDTO postDTO=new PostDTO();
//			postDTO.setPost_id(rs.getInt(1));
//			postDTO.setUser_id(rs.getInt(2));
//			postDTO.setTag_user_id(rs.getInt(3));
//			postDTO.setPostlike_cnt(rs.getInt(4));
//			postDTO.setCreate_time(rs.getTimestamp(5));
//			postDTO.setRetweet_cnt(rs.getInt(6));
//			postDTO.setPostcontent(rs.getString(7));
//			postDTO.setPost_img(rs.getString(8));
//
//			postDTOList.add(postDTO);
//		}
//		return postDTOList;
//	}

	// update
		public void updatePost(Connection con, PreparedStatement pstm, int post_id ,int tag_user_id, String postcontent, String post_img){
			String sql;
			try {
				sql = "UPDATE post "
						+ "SET tag_user_id = ?, "
						+ "postcontent = ?, "
						+ "post_img = ?, "
						+ "create_time = CURRENT_TIME() where post_id=?";

				pstm = con.prepareStatement(sql);
				pstm.setInt(1, tag_user_id);
				pstm.setString(2, postcontent);
				pstm.setString(3, post_img);
				pstm.setInt(4,post_id);
				pstm.executeUpdate();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}


	// counting post_like which match with post.post id and update post.post like count in post.post relation
		public void updatePostLike(PreparedStatement pstm, Connection con, Statement stmt, int postID) {
			try {
				String sql = "select count(*) as like_count from post_like where post_id = " + postID;
				ResultSet rs = stmt.executeQuery(sql);
				
				int like_count;
				if(rs.next()) {
					like_count = rs.getInt("like_count");
				} else {
					like_count = 0;
				}
				
				//sql = "update post set postlike_cnt = " + like_count + "where post_id = " + postID;
				sql="update post set postlike_cnt=? where post_id=?";
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, like_count);
				pstm.setInt(2,postID);
				pstm.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("error during updating post_like\n" + e.getMessage());
			}
			
			
		}
	// delete
		public void deletePost(Connection con, PreparedStatement pstm, int post_id ){

			 String sql;

			try {

				sql = "delete from post where post_id=" + post_id;
				pstm = con.prepareStatement(sql);
				pstm.executeUpdate();

			}catch (SQLException e){
				e.printStackTrace();
			}
		}

	// 유저 차단 여부 확인
		// 별도 class에
	// 
	
	
	// 이하 좋아요 관련
	// c
	// create like in post_like relation
		public void createPostLike(int likerID, int postID, PreparedStatement pstm, Connection con) {
			try {
				String sql = "insert into post_like(liker_id, post_id) values(" + likerID + ", " + postID + ")";
				pstm = con.prepareStatement(sql);
				pstm.executeUpdate();
			} catch (Exception e) {
				System.out.println("error during creating post_like\n" + e.getMessage());
			}
		}
	// r
		// return post.post like count
		public int getLikeCountFromPost(int postID, Statement stmt) {
			ResultSet rs;
			 try {
				String sql = "select postlike_cnt from post where post_id = " + postID;
				rs = stmt.executeQuery(sql);
				 rs.next();
				int like_count = rs.getInt("postlike_cnt");
				return like_count;
			} catch (NullPointerException e) {
				return 0;
			} catch (Exception e) {
				System.out.println("error: " + e.getMessage());
				return 0;
			}
		}
		
	// u
		 
	// d
		 
	// liking post.post
	public void likePost(PreparedStatement pstm, Statement stmt, Connection con, int likerID, int postID ) {
		try {
			// check if user already like post.post
			String sql = "select * from post_like where liker_id=" + likerID + " and post_id=" + postID;
			ResultSet tmp_rs = stmt.executeQuery(sql);
			
			if(tmp_rs.next()) {
				System.out.println("user aleady like post.post");
				deletePostLike(pstm, con, likerID, postID);
				updatePostLike(pstm, con, stmt, postID);
				
			} else {
				System.out.println("do liking");
				createPostLike(likerID, postID, pstm, con);
				updatePostLike(pstm, con, stmt, postID);
			}
		} catch (Exception e) {
			System.out.println("error during liking post.post\n" + e.getMessage());
		}
		
		
	}
	
	
	
	
	
	// delete like in post_like relation
	public void deletePostLike(PreparedStatement pstm, Connection con, int likerID, int postID) {
		try {
			String sql = "delete from post_like where liker_id = " + likerID;
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
		} catch (Exception e) {
			System.out.println("error during deleting post_like\n" + e.getMessage());
		}
	}
	
	
}
