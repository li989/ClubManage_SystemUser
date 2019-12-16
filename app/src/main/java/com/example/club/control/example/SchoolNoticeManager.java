package com.example.club.control.example;

import com.example.club.model.Beanschoolnotice;
import com.example.club.util.BaseException;
import com.example.club.util.DBUtil;
import com.example.club.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchoolNoticeManager{
    public List<Beanschoolnotice> getallnotice() throws BaseException {
        List<Beanschoolnotice> result=new ArrayList<Beanschoolnotice>();
        Connection conn=null;
        try{
            conn= DBUtil.getConnection();
            String sql="select schoolnotice_id,schoolnotice_title,schoolnotice_start_time,schoolnotice_picture from schoolnotice ORDER BY schoolnotice_start_time DESC ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                Beanschoolnotice nt=new Beanschoolnotice();
                nt.setSchoolnotice_ID(rs.getInt(1));
                nt.setSchoolnotice_title(rs.getString(2));
                nt.setSchoolnotice_start_time(rs.getTimestamp(3));
                nt.setSchoolnotice_picture(rs.getBytes(4));
                result.add(nt);
            }
            pst.close();
            rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public Beanschoolnotice select_aSchoolnotice(int ID) throws BaseException {
        Beanschoolnotice a=new Beanschoolnotice();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from schoolnotice where schoolnotice_ID=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, ID);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()) {
                a.setSchoolnotice_ID(rs.getInt(1));
                a.setSchoolnotice_title(rs.getString(2));
                a.setSchoolnotice_content(rs.getString(3));
                a.setSchoolnotice_start_time(rs.getDate(4));
                a.setSchoolnotice_picture(rs.getBytes(5));
                a.setSchoolnotice_number(rs.getInt(6));
            }
            rs.close();
            pst.close();
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
    public List<Beanschoolnotice> getsixhot() throws BaseException {
        List<Beanschoolnotice> result=new ArrayList<Beanschoolnotice>();
        Connection conn=null;
        try{
            conn= DBUtil.getConnection();
            String sql="select schoolnotice_id,schoolnotice_title,schoolnotice_start_time,schoolnotice_picture from schoolnotice ORDER BY schoolnotice_number,schoolnotice_start_time DESC";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                Beanschoolnotice nt=new Beanschoolnotice();
                nt.setSchoolnotice_ID(rs.getInt(1));
                nt.setSchoolnotice_title(rs.getString(2));
                nt.setSchoolnotice_start_time(rs.getTimestamp(3));
                nt.setSchoolnotice_picture(rs.getBytes(4));
                result.add(nt);
            }
            pst.close();
            rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Beanschoolnotice> deletenotice(int ID) throws BaseException {
        List<Beanschoolnotice> result=new ArrayList<Beanschoolnotice>();
        Connection conn=null;
        try{
            conn= DBUtil.getConnection();
            String sql="delete from schoolnotice where schoolnotice_ID = ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, ID);
            pst.execute();
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public List<Beanschoolnotice> selectNotice(String name) throws BaseException {
        List<Beanschoolnotice> result=new ArrayList<Beanschoolnotice>();
        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            String sql="select * from schoolnotice where schoolnotice_title like? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, "%"+name+"%");
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                Beanschoolnotice a=new Beanschoolnotice();
                a.setSchoolnotice_ID(rs.getInt(1));
                a.setSchoolnotice_title(rs.getString(2));
                a.setSchoolnotice_content(rs.getString(3));
                a.setSchoolnotice_start_time(rs.getTimestamp(4));
                a.setSchoolnotice_picture(rs.getBytes(5));
                a.setSchoolnotice_number(rs.getInt(6));
                result.add(a);
            }
            rs.close();
            pst.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public Beanschoolnotice selectNotice(int noticeid) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from schoolnotice where schoolnotice_id =? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, noticeid);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BaseException("id传递错误");
            Beanschoolnotice a = new Beanschoolnotice();
            a.setSchoolnotice_ID(rs.getInt(1));
            a.setSchoolnotice_title(rs.getString(2));
            a.setSchoolnotice_content(rs.getString(3));
            a.setSchoolnotice_start_time(rs.getTimestamp(4));
            a.setSchoolnotice_picture(rs.getBytes(5));
            a.setSchoolnotice_number(rs.getInt(6));
            rs.close();
            return a;
        } catch (BaseException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }
    public void deleteNotice(int noticeid) {
        List<Beanschoolnotice> result=new ArrayList<Beanschoolnotice>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from schoolnotice where schoolnotice_id =? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, noticeid);
            pst.executeUpdate();
            pst.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public void addSchoolnotice(String title,String content,byte[] img) throws BaseException {
        Connection conn=null;
        try {
            int c=1;
            conn=DBUtil.getConnection();
            String sql="SELECT MAX(schoolnotice_ID) from schoolnotice";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next())c=rs.getInt(1)+1;
            sql="INSERT INTO schoolnotice(schoolnotice_ID,schoolnotice_title,schoolnotice_content,schoolnotice_start_time,schoolnotice_picture,schoolnotice_number) values(?,?,?,?,?,0)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, c);
            pst.setString(2, title);
            pst.setString(3, content);
            pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setBytes(5,img);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
