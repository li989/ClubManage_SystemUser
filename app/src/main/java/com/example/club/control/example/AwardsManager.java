package com.example.club.control.example;

import android.util.Log;

import com.example.club.model.Beanawards_club;
import com.example.club.util.BaseException;
import com.example.club.util.DBUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.example.club.model.Beanawards;
import com.example.club.util.BusinessException;
import com.example.club.util.DbException;

public class AwardsManager {
	public void addAwards(String name,int club_ID,byte[] img ) throws BaseException {
		Connection conn=null;
		try {
			int c=1;
			conn= DBUtil.getConnection();
			String sql="SELECT MAX(awards_ID) from awards";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next())c=rs.getInt(1)+1;
			sql="INSERT INTO awards(awards_ID,club_ID,awards_name,awards_picture,awards_status,awards_time) values(?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, c);
			pst.setInt(2, club_ID);
			pst.setString(3, name);
			pst.setBytes(4, img);
			pst.setString(5, "审核中");
			pst.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Beanawards loadawards(int ID) throws BaseException {
		Beanawards a=new Beanawards();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from awards where awards_ID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, ID);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				a.setAwards_ID(rs.getInt(1));
				a.setClub_ID(rs.getInt(2));
				a.setAwards_name(rs.getString(3));
				a.setAwards_picture(rs.getBytes(4));
				a.setAwards_grade(rs.getInt(5));
				a.setAwards_status(rs.getString(6));
				a.setAwards_time(rs.getDate(7));
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
	public List<Beanawards> loadAllclub(int ID) throws BaseException {
		List<Beanawards> result=new ArrayList<Beanawards>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from awards where club_ID=? and awards_status=?  ORDER BY awards_time DESC" ;
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, ID);
			pst.setString(2, "已通过");
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				Beanawards a=new Beanawards();
				a.setAwards_ID(rs.getInt(1));
				a.setClub_ID(rs.getInt(2));
				a.setAwards_name(rs.getString(3));
				a.setAwards_picture(rs.getBytes(4));
				a.setAwards_grade(rs.getInt(5));
				a.setAwards_status(rs.getString(6));
				a.setAwards_time(rs.getDate(7));
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
	public List<Beanawards_club> loadallactivityclub() throws BusinessException {
		List<Beanawards_club> result=new ArrayList<Beanawards_club>();
		Connection conn=null;
		try{
			conn= DBUtil.getConnection();
			String sql="select Awards_ID,a.club_id,club_name,awards_name,awards_picture,awards_grade,awards_time \n" +
					"from awards a,club b \n" +
					"where a.club_ID=b.club_ID  and awards_status ='审核中' order by awards_time DESC";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				Beanawards_club club=new Beanawards_club();
				club.setAwards_ID(rs.getInt(1));
				club.setClub_ID(rs.getInt(2));
				club.setClub_name(rs.getString(3));
				club.setAwards_name(rs.getString(4)); ;
				club.setAwards_picture(rs.getBytes(5));
				club.setAwards_grade(rs.getInt(6));
				club.setAwards_time(rs.getDate(7));
				result.add(club);
			}

			pst.close();
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;

	}

//	public List<Beanawards> loadAll() throws BaseException {
//		List<Beanawards> result=new ArrayList<Beanawards>();
//		Connection conn=null;
//		try {
//			conn=DBUtil.getConnection();
//			String sql="select * from awards";
//			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
//			java.sql.ResultSet rs=pst.executeQuery();
//		while(rs.next()) {
//			Beanawards a=new Beanawards();
//			a.setAwards_ID(rs.getInt(1));
//			a.setClub_ID(rs.getInt(2));
//			a.setAwards_name(rs.getString(3));
//			a.setAwards_picture(rs.getBytes(4));
//			a.setAwards_grade(rs.getInt(5));
//			a.setAwards_status(rs.getString(6));
//			result.add(a);
//		}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DbException(e);
//		}
//		finally{
//			if(conn!=null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
//		return result;
//	}

	public List<Beanawards> loadAllstatus(String status) throws BaseException {
		List<Beanawards> result = new ArrayList<Beanawards>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from awards where awards_status=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, status);
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Beanawards a = new Beanawards();
				a.setAwards_ID(rs.getInt(1));
				a.setClub_ID(rs.getInt(2));
				a.setAwards_name(rs.getString(3));
				a.setAwards_picture(rs.getBytes(4));
				a.setAwards_grade(rs.getInt(5));
				a.setAwards_status(rs.getString(6));
				a.setAwards_time(rs.getDate(7));
				result.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	public void deleteAwards(Beanawards awards) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select awards_ID from awards where awards_ID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, awards.getAwards_ID());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("奖项不存在");
			rs.close();
			pst.close();
			pst=conn.prepareStatement("delete from awards where awards_ID=?");
			pst.setInt(1, awards.getAwards_ID());
			pst.execute();
			pst.close();

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
	public List<Beanawards> selectAwards(String name) throws BaseException {
		List<Beanawards> result=new ArrayList<Beanawards>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from awards where awards_name like ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, "%"+name+"%");
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				Beanawards a=new Beanawards();
				a.setAwards_ID(rs.getInt(1));
				a.setClub_ID(rs.getInt(2));
				a.setAwards_name(rs.getString(3));
				a.setAwards_picture(rs.getBytes(4));
				a.setAwards_grade(rs.getInt(5));
				a.setAwards_status(rs.getString(6));
				a.setAwards_time(rs.getDate(7));
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
	//������������
	public List<Beanawards> selectAwardsclub(String name,int ID) throws BaseException {
		List<Beanawards> result=new ArrayList<Beanawards>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from awards where club_ID=? and awards_name like ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, ID);
			pst.setString(2, "%"+name+"%");
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				Beanawards a=new Beanawards();
				a.setAwards_ID(rs.getInt(1));
				a.setClub_ID(rs.getInt(2));
				a.setAwards_name(rs.getString(3));
				a.setAwards_picture(rs.getBytes(4));
				a.setAwards_grade(rs.getInt(5));
				a.setAwards_status(rs.getString(6));
				a.setAwards_time(rs.getDate(7));
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
	public Beanawards_club selectawardsbyid(int id) throws BusinessException {
		Connection conn=null;
		try{
			conn=DBUtil.getConnection();
			String sql="select awards_ID,awards_picture,awards_name,awards_time,a.club_ID,Club_name,awards_grade\n" +
					"from awards a,club b\n" +
					"where a.club_ID=b.club_ID  and awards_ID = ? and awards_status ='审核中' ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next())
			{
				return null;
			}
			Beanawards_club club=new Beanawards_club();
			club.setAwards_ID(rs.getInt(1));
			club.setAwards_picture(rs.getBytes(2));
			club.setAwards_name(rs.getString(3));
			club.setAwards_time(rs.getDate(4)); ;

			club.setClub_ID(rs.getInt(5));
			club.setClub_name(rs.getString(6));
			club.setAwards_grade(rs.getInt(7));

			pst.close();
			rs.close();
			return club;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void modifyawards1(int per_t4,int awardsid,String choose) throws BusinessException {
		Connection conn=null;
		try{
			conn=DBUtil.getConnection();
			String sql="update awards set awards_status = ?, awards_grade = ? where awards_id = ?  and awards_status ='审核中' ";
			Log.e("hpa",""+awardsid);
			Log.e("hpa1",""+per_t4);
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, choose);
			pst.setInt(3, awardsid);
			pst.setInt(2, per_t4);
			pst.execute();
			pst.close();
			sql="update awards a,club b   \n" +
					"SET b.club_grade=b.club_grade+a.awards_grade\n" +
					"where a.awards_ID= ? AND a.club_ID=b.club_ID";
			pst =conn.prepareStatement(sql) ;
			Log.e("hpaa",""+awardsid);
			pst.setInt(1,awardsid);
			pst.execute();
			pst.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//����Ա��˽���
	public void ExamineAwards(int awards_ID,int grade,String status) throws BaseException {
		Connection conn=null;
		try {
			int c=0;
			conn=DBUtil.getConnection();
			String sql="UPDATE awards SET awards_status=? , awards_grade=? where awards_ID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, status);
			pst.setInt(2, grade);
			pst.setInt(3, awards_ID);
			pst.execute();
			pst.close();
			sql="select club_grade from club_introducein where club_ID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, awards_ID);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next())c=rs.getInt(1);
			sql="UPDATE club_introducein SET club_grade=? where club_ID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, c+grade);
			pst.setInt(2, awards_ID);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
