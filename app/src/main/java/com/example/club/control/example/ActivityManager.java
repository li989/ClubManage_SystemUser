package com.example.club.control.example;

import android.util.Log;

import com.example.club.model.Beanactivity;
import com.example.club.model.Beanawards;
import com.example.club.util.BaseException;
import com.example.club.util.BusinessException;
import com.example.club.util.DBUtil;
import com.example.club.util.DbException;
import com.example.club.model.Beanactivity_club;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityManager {
    public List<Beanactivity> loadAllclub(int ID) throws BaseException {
        List<Beanactivity> result=new ArrayList<Beanactivity>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from activity where club_ID=? and activity_status=?  ORDER BY activity_start_time DESC" ;
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, ID);
            pst.setString(2, "已通过");
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                Beanactivity a=new Beanactivity();
                a.setActivity_ID(rs.getInt(1));
                a.setClub_ID(rs.getInt(2));
                a.setPlace_ID(rs.getInt(3));
                a.setActivity_name(rs.getString(4));
                a.setActivity_start_time(rs.getDate(5));
                a.setActivity_finish_time(rs.getDate(6));
                a.setActivity_grade(rs.getInt(7));
                a.setActivity_status(rs.getString(8));
                a.setActivity_picture(rs.getBytes(9));
                a.setActivity_centent(rs.getString(10));
                result.add(a);
            }
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
        return result;
    }
    public Beanactivity loadactivitya(int ID) throws BaseException {
        Beanactivity a=new Beanactivity();
        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            String sql="select * from activity where activity_ID=?" ;
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, ID);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()) {
                a.setActivity_ID(rs.getInt(1));
                a.setClub_ID(rs.getInt(2));
                a.setPlace_ID(rs.getInt(3));
                a.setActivity_name(rs.getString(4));
                a.setActivity_start_time(rs.getDate(5));
                a.setActivity_centent(rs.getString(10));
                a.setActivity_finish_time(rs.getDate(6));
                a.setActivity_grade(rs.getInt(7));
                a.setActivity_status(rs.getString(8));
                a.setActivity_picture(rs.getBytes(9));
            }
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
        return a;
    }
    public List<Beanactivity_club> loadallactivityclub() throws BusinessException {
        List<Beanactivity_club> result=new ArrayList<Beanactivity_club>();
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select activity_ID,activity_picture,activity_name,Activity_start_time,Activity_finish_time,a.Place_ID,Place_name,a.club_ID,Club_name,Activity_grade,Activity_centent \n" +
                    "from activity a,club b,place c \n" +
                    "where a.club_ID=b.club_ID and a.place_id=c.place_ID  and activity_status ='审核中' order by Activity_start_time DESC";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                Beanactivity_club club=new Beanactivity_club();
                club.setActivity_ID(rs.getInt(1));
                club.setActivity_picture(rs.getBytes(2));
                club.setActivity_name(rs.getString(3));
                club.setActivity_start_time(rs.getDate(4)) ;
                club.setActivity_finish_time(rs.getDate(5));
                club.setPlace_ID(rs.getInt(6));
                club.setPlace_name(rs.getString(7));
                club.setClub_ID(rs.getInt(8));
                club.setClub_name(rs.getString(9));
                club.setActivity_grade(rs.getInt(10));
                club.setActivity_centent(rs.getString(11));
                result.add(club);
            }
            Log.e("fdsf",""+result.size());
            pst.close();
            rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }
    public void modifyactivity1(int per_t6,int activityid,String choose) throws BusinessException {
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="update activity set activity_status = ?, activity_grade = ? where activity_id = ?  and activity_status ='审核中' ";
            Log.e("hpa",""+activityid);
            Log.e("hpa1",""+per_t6);
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, choose);
            pst.setInt(3, activityid);
            pst.setInt(2, per_t6);
            pst.execute();
            pst.close();
            sql="update activity a,club b   \n" +
                    "SET b.club_grade=b.club_grade+a.activity_grade\n" +
                    "where a.activity_ID= ? AND a.club_ID=b.club_ID";
            pst =conn.prepareStatement(sql) ;
            Log.e("hpaa",""+activityid);
            pst.setInt(1,activityid);
            pst.execute();
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public Beanactivity_club selectactivitybyid(int id) throws BusinessException {
        Connection conn=null;
        try{
            conn=DBUtil.getConnection();
            String sql="select activity_ID,activity_picture,activity_name,Activity_start_time,Activity_finish_time,a.Place_ID,Place_name,a.club_ID,Club_name,Activity_grade,Activity_centent \n" +
                    "from activity a,club b,place c \n" +
                    "where a.club_ID=b.club_ID and a.place_id=c.place_ID and activity_ID = ? and activity_status ='审核中' ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next())
            {
                return null;
            }
            Beanactivity_club club=new Beanactivity_club();
            club.setActivity_ID(rs.getInt(1));
            club.setActivity_picture(rs.getBytes(2));
            club.setActivity_name(rs.getString(3));
            club.setActivity_start_time(rs.getDate(4)) ;
            club.setActivity_finish_time(rs.getDate(5));
            club.setPlace_ID(rs.getInt(6));
            club.setPlace_name(rs.getString(7));
            club.setClub_ID(rs.getInt(8));
            club.setClub_name(rs.getString(9));
            club.setActivity_grade(rs.getInt(10));
            club.setActivity_centent(rs.getString(11));
            pst.close();
            rs.close();
            return club;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void addActivity(String name,int club_ID,int place_ID,String start_time,String finish_time,String centent,byte[] img ) throws BaseException {
        Connection conn=null;
        try {
            int c=1;
            conn= DBUtil.getConnection();
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            date1 = format1.parse(start_time);
            Date date2 = null;
            date2 = format1.parse(finish_time);
            java.sql.Date  sqlDate1 = new java.sql.Date(date1.getTime());
            java.sql.Date  sqlDate2 = new java.sql.Date(date2.getTime());
            String sql="SELECT MAX(activity_ID) from activity";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next())c=rs.getInt(1)+1;
            sql="INSERT INTO activity(activity_ID,club_ID,place_ID,activity_name,activity_start_time,activity_finish_time,activity_status,activity_picture,activity_centent) values(?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, c);
            pst.setInt(2, club_ID);
            pst.setInt(3, place_ID);
            pst.setString(4,name);
            pst.setDate(5,sqlDate1);
            pst.setDate(6,sqlDate2);
            pst.setString(7,"审核中");
            pst.setBytes(8,img);
            pst.setString(9,centent);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
