package com.example.myapplication.banned;

import java.sql.*;
import java.util.List;

public class BanUser {


    List<Integer> banList;
    //c  차단 목록에 추가
    public void addBan(Connection con, PreparedStatement pstm, int user_id){
        String sql=null;

        try {
            sql = "INSERT INTO ban (user_id) VALUES ('" + user_id + "' )";
            pstm = con.prepareStatement(sql);
            pstm.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //r  차단 목록 조회
    public List<Integer> findAllBan(Statement stmt){
        String sql=null;
        ResultSet rs;
        try{
            sql="select user_id from ban";
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                banList.add(rs.getInt(1));
            }
            return banList;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    //d  차단 해제
    public void deleteBan(Connection con, PreparedStatement pstm, int user_id){
        String sql=null;
        try{
            sql="delete from ban where user_id="+user_id;
            pstm = con.prepareStatement(sql);
            pstm.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
