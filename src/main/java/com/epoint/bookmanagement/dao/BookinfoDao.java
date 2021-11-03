package com.epoint.bookmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.epoint.bookmanagement.bizlogic.bookinfo.domain.Bookinfo;
import com.epoint.bookmanagement.bizlogic.borrowinfo.domain.Borrowinfo;
import com.epoint.bookmanagement.utils.JDBCUtil;

//操作bookinfo数据库表类
public class BookinfoDao {

	/*
	 * 添加图书信息 返回新增成功的数据条数
	 */
	public int addBookinfo(Bookinfo bookinfo) {
		Connection con = JDBCUtil.getConnection();
		String sql = "insert into bookinfo value(?,?,?,?,?,?)";
		PreparedStatement ps = null;
		int res = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, bookinfo.getBookId());
			ps.setString(2, bookinfo.getBookName());
			ps.setString(3, bookinfo.getPublisher());
			ps.setString(4, bookinfo.getAuthor());
			ps.setInt(5, bookinfo.getBookType());
			ps.setInt(6, bookinfo.getRemain());
			res = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(null, ps, con);
		}
		return res;
	}

	/*
	 * 更新图书信息 （返回更新成功的数据条数）
	 */
	public int updateBookInfo(Bookinfo bookinfo) {
		Connection con = JDBCUtil.getConnection();
		String sql = "update bookinfo set bookname=?,publisher=?,author=?,booktype=?,remain=? where bookid=?";
		PreparedStatement ps = null;
		int res = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, bookinfo.getBookName());
			ps.setString(2, bookinfo.getPublisher());
			ps.setString(3, bookinfo.getAuthor());
			ps.setInt(4, bookinfo.getBookType());
			ps.setInt(5, bookinfo.getRemain());
			ps.setString(6, bookinfo.getBookId());
			res = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(null, ps, con);
		}
		return res;
	}

	/*
	 * 根据图书id删除图书信息（传入单个图书id，返回删除成功的数据条数）
	 */
	public int deleteBookinfoByBookId(String bookid) {
		Connection con = JDBCUtil.getConnection();
		String sql = "delete from bookinfo where bookid=?";
		String sql2 = "delete from borrowinfo where bookid=?";
		PreparedStatement ps = null;
		int i=0;
		int j=0;
		int res = 0;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setString(1, bookid);
			i = ps.executeUpdate();
			if(i>0) {
				ps.close();
				ps = con.prepareStatement(sql2);
				ps.setString(1, bookid);
				j=ps.executeUpdate();
			}
			if(i>0 && j>=0) {
				res=1;
				con.commit();
			}else {
				res=0;
				con.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtil.closeConnection(null, ps, con);
		}
		return res;
	}
	
	/*
	 * 根据图书id删除图书信息（传入多个图书id，返回删除成功的数据条数）
	 */
	public int deleteBookinfo(String bookids) {
		Connection con = JDBCUtil.getConnection();
//		String sql = "delete from bookinfo where bookid in ('"+bookids+"')";
		String sql = "delete from bookinfo where find_in_set (bookid,?)";
		PreparedStatement ps = null;
		int res = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, bookids);
			res = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(null, ps, con);
		}
		return res;
	}
	

	/*
	 * 根据图书id查询图书信息 （返回查询的实体）
	 */
	public Bookinfo queryBookInfoByID(String bookid) {
		Connection con = JDBCUtil.getConnection();
		String sql = "select * from bookinfo where bookid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Bookinfo bookinfo = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, bookid);
			rs = ps.executeQuery();
			while (rs.next()) {
				String bookId = rs.getString("bookId");
				String bookName = rs.getString("bookName");
				String publisher = rs.getString("publisher");
				String author = rs.getString("author");
				int bookType = rs.getInt("bookType");
				int remain = rs.getInt("remain");
				bookinfo = new Bookinfo(bookId, bookName, publisher, author, bookType, remain);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return bookinfo;
	}
	

	/*
	 * 根据关键词对图书信息列表进行分页查询（假设关键词为String bookname、Integer booktype）
	 */
	public List<Bookinfo> queryBookinfoByKeyWords(int pageIndex, int pageSize, String bookname, Integer booktype) {
		Connection con = JDBCUtil.getConnection();
		int indexnum = pageIndex * pageSize;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Bookinfo> bookInfoList = new ArrayList<Bookinfo>();
		Bookinfo bookInfo = null;

		StringBuffer sb = new StringBuffer();
		sb.append(" select * from bookinfo where 1=1 ");

		if (StringUtils.isNotBlank(bookname)) {
			sb.append(" and bookname like concat('%', ? ,'%') ");
		}
		if (booktype != null) {
			sb.append(" and booktype = ? ");
		}
		sb.append(" limit ?,?");

		String sql = sb.toString();
		int i = 1;
		try {
			ps = con.prepareStatement(sql);
			if (StringUtils.isNotBlank(bookname)) {
				ps.setString(i++, bookname);
			}
			if (booktype != null) {
				ps.setInt(i++, booktype);
			}
			ps.setInt(i++, indexnum);
			ps.setInt(i++, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				bookInfo = new Bookinfo();
				bookInfo.setBookId(rs.getString("bookid"));
				bookInfo.setBookName(rs.getString("bookname"));
				bookInfo.setAuthor(rs.getString("author"));
				bookInfo.setPublisher(rs.getString("publisher"));
				bookInfo.setBookType(rs.getInt("booktype"));
				bookInfo.setRemain(rs.getInt("remain"));
				bookInfoList.add(bookInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return bookInfoList;
	}

	/*
	 * 查询图书信息表中所有数据的条数
	 */
	public int findLength() {
		Connection con = JDBCUtil.getConnection();
		String sql = "select count(1) from bookinfo";
		PreparedStatement ps = null;
		ResultSet rs=null;
		int rowCount = 0;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				rowCount=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return rowCount;
	}
	
	/*
	 * 新增页重名验证
	 */
	public Bookinfo checkExist(String bookName,String author) {
		Connection con = JDBCUtil.getConnection();
		String sql = "select * from bookinfo where bookname=? and author=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Bookinfo bookinfo = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, bookName);
			ps.setString(2, author);
			rs = ps.executeQuery();
			while (rs.next()) {
				bookinfo=new Bookinfo();
				bookinfo.setBookId(rs.getString("bookid"));
				bookinfo.setBookName(rs.getString("bookname"));
				bookinfo.setAuthor(rs.getString("author"));
				bookinfo.setPublisher(rs.getString("publisher"));
				bookinfo.setBookType(rs.getInt("booktype"));
				bookinfo.setRemain(rs.getInt("remain"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return bookinfo;
	}
	
	/*
	 * 修改页重名验证
	 */
	public Bookinfo checkExist(String bookNameBefore,String bookNameNow,String author) {
		Connection con = JDBCUtil.getConnection();
		String sql = "select * from bookinfo where bookname !=? and bookname =? and author=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Bookinfo bookinfo = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, bookNameBefore);
			ps.setString(2, bookNameNow);
			ps.setString(3, author);
			rs = ps.executeQuery();
			while (rs.next()) {
				bookinfo=new Bookinfo();
				bookinfo.setBookId(rs.getString("bookid"));
				bookinfo.setBookName(rs.getString("bookname"));
				bookinfo.setAuthor(rs.getString("author"));
				bookinfo.setPublisher(rs.getString("publisher"));
				bookinfo.setBookType(rs.getInt("booktype"));
				bookinfo.setRemain(rs.getInt("remain"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return bookinfo;
	}
	
	/*
	 * 根据id查找bookName
	 */
	public String queryBookName(String bookId) {
		Connection con = JDBCUtil.getConnection();
		String sql = "select bookName from bookinfo where bookid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String bookName="";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, bookId);
			rs = ps.executeQuery();
			while (rs.next()) {
				 bookName = rs.getString("bookName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return bookName;
	}
	
	/*
	 * 归还图书
	 */
	public int returnBook(Borrowinfo borrowinfo) {
		Connection con = JDBCUtil.getConnection();
		String sql = "update borrowinfo set returntim =? where borrowid =?";
		String sql2 = "update bookinfo set remain=remain+1 where bookid =?";
		PreparedStatement ps = null;
		int i = 0;
		int j = 0;
		int res = 0;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setObject(1, borrowinfo.getReturnTim());
			ps.setString(2, borrowinfo.getBorrowId());
			i = ps.executeUpdate();
			if(i>0) {
				ps.close();
				ps=con.prepareStatement(sql2);
				ps.setString(1, borrowinfo.getBookId());
				j=ps.executeUpdate();
			}
			if(i>0 && j>0) {
				res=1;
				con.commit();
			}else {
				con.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtil.closeConnection(null, ps, con);
		}
		return res;
	}
}