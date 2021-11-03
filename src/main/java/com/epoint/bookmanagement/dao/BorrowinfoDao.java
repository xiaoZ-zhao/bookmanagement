package com.epoint.bookmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.epoint.bookmanagement.bizlogic.borrowinfo.domain.Borrowinfo;
import com.epoint.bookmanagement.utils.JDBCUtil;

//操作borrowinfo
public class BorrowinfoDao {

	/*
	 * 添加借阅信息 返回新增成功的数据条数
	 */
	public int addBorrowInfo(Borrowinfo borrowinfo) {
		Connection con = JDBCUtil.getConnection();
		String sql = "insert into borrowinfo value(?,?,?,?,?,?)";
		String sql2 = "update bookinfo set remain=remain-1 where bookid=?";
		PreparedStatement ps = null;
		int i = 0;
		int j = 0;
		int res = 0;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setString(1, borrowinfo.getBorrowId());
			ps.setString(2, borrowinfo.getBookId());
			ps.setString(3, borrowinfo.getBorrower());
			ps.setString(4, borrowinfo.getPhone());
			ps.setObject(5, borrowinfo.getBorrowTime());
			ps.setObject(6, borrowinfo.getReturnTim());
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

	/*
	 * 更新借阅信息 （返回更新成功的数据条数）
	 */
	public int updateBorrowInfo(Borrowinfo borrowinfo) {
		Connection con = JDBCUtil.getConnection();
		String sql = "update borrowinfo set bookid=?,borrower=?,phone=?,borrowtime=?,returntim=? where borrowid=?";
		PreparedStatement ps = null;
		int res = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, borrowinfo.getBookId());
			ps.setString(2, borrowinfo.getBorrower());
			ps.setString(3, borrowinfo.getPhone());
			ps.setObject(4, borrowinfo.getBorrowTime());
			ps.setObject(5, borrowinfo.getReturnTim());
			ps.setString(6, borrowinfo.getBorrowId());
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
	 * 根据图书id删除借阅信息（传入单个图书id，返回删除成功的数据条数）
	 */
	public int deleteBorrowinfoByBorrowId(String borrowid) {
		Connection con = JDBCUtil.getConnection();
		String sql = "delete from borrowinfo where borrowid=?";
		PreparedStatement ps = null;
		int res = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, borrowid);
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
	 * 根据图书id查询借阅信息 （返回查询的实体）
	 */
	public Borrowinfo queryBorrowInfoByID(String borrowid) {
		Connection con = JDBCUtil.getConnection();
		String sql = "select * from borrowinfo where borrowid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Borrowinfo borrowinfo = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, borrowid);
			rs = ps.executeQuery();
			while (rs.next()) {
				String borrowId = rs.getString("borrowId");
				String bookId = rs.getString("bookId");
				String borrower = rs.getString("borrower");
				String phone = rs.getString("phone");
				Timestamp borrowtime = rs.getTimestamp("borrowtime");
				Timestamp returntim = rs.getTimestamp("returntim");
				borrowinfo = new Borrowinfo(borrowId, bookId, borrower, phone, borrowtime, returntim);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return borrowinfo;
	}

	/*
	 * 查询借阅信息列表
	 */
	public List<Borrowinfo> queryBookinfoByKeyWords(int pageIndex, int pageSize, String sortField, String sortOrder) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		int indexnum = pageIndex * pageSize;

		Borrowinfo borrowinfo = null;
		List<Borrowinfo> borrowInfoList = new ArrayList<Borrowinfo>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from borrowinfo where 1=1 ");

		if (StringUtils.isNotBlank(sortField)) {
			sb.append(" order by ").append(sortField);
			if (StringUtils.isNotBlank(sortOrder)) {
				sb.append(" ").append(sortOrder);
			}
		}
		sb.append(" limit ?,? ");
		String sql = sb.toString();
		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, indexnum);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				borrowinfo = new Borrowinfo();
				borrowinfo.setBorrowId(rs.getString("borrowid"));
				borrowinfo.setBookId(rs.getString("bookid"));
				borrowinfo.setBorrower(rs.getString("borrower"));
				borrowinfo.setPhone(rs.getString("phone"));
				borrowinfo.setBorrowTime(rs.getTimestamp("borrowtime"));
				borrowinfo.setReturnTim(rs.getTimestamp("returntim"));
				borrowInfoList.add(borrowinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return borrowInfoList;
	}

	/*
	 * 查询借阅信息表中所有数据的条数
	 */
	public int findLength() {
		Connection con = JDBCUtil.getConnection();
		String sql = "select count(1) from borrowinfo";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowCount = 0;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				rowCount = rs.getInt(1);
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
	 * borrowid递增
	 */
	public String getNextBorrowId() {
		Connection con = JDBCUtil.getConnection();
		String sql = "select concat('Borrow',extract(year from sysdate()),lpad(max(right(borrowid,4))+ 1, 4, 0)) as maxnum "
				+ "from borrowinfo where mid(borrowid,7,4)=extract(year from sysdate())";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String maxnum = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				maxnum = rs.getString("maxnum");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		if (maxnum == null) {
			return "Borrow" + new SimpleDateFormat("yyyy").format(new Date()) + "0001";
		} else {
			return maxnum;
		}
	}

	
	/*
	 * 根据bookid查询当前还有多少书没有归还
	 */
	public int queryBorrowInfoCountByBookId(String bookId) {
		Connection con = JDBCUtil.getConnection();
		String sql = "select count(*) as totalcount from borrowinfo where bookId=? and returntim is null";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalcount = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1,bookId);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalcount = rs.getInt("totalcount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, ps, con);
		}
		return totalcount;
	}
}
