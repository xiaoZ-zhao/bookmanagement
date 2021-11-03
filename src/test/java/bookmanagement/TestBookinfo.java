package bookmanagement;



import java.sql.Connection;

import org.junit.Test;

import com.epoint.bookmanagement.bizlogic.bookinfo.domain.Bookinfo;
import com.epoint.bookmanagement.dao.BookinfoDao;

import com.epoint.bookmanagement.utils.JDBCUtil;

public class TestBookinfo {

	/*
	 * 测试连接数据库
	 */
	@Test
	public void test() {
		Connection connection = JDBCUtil.getConnection();
		System.out.println(connection);
	}

	/*
	 * 根据id删除图书信息
	 */
	@Test
	public void deleteBookinfoByBookId() {
		BookinfoDao dao = new BookinfoDao();
		int res = dao.deleteBookinfoByBookId("f7962b67c0dc47459aafd71cdd0fb559");
		System.out.println(res);
	}

	/*
	 * 根据图书id查询图书信息
	 */
	@Test
	public void queryBookInfoByID() {
		BookinfoDao dao = new BookinfoDao();
		Bookinfo bookinfo = dao.queryBookInfoByID("fd860c5d958f4a388282f5af88629d75");
		System.out.println(bookinfo.toString());
	}
	
	/*
	 * 查询图书信息表中所有数据的条数
	 */
	@Test
	public void findLength() {
		BookinfoDao dao = new BookinfoDao();
		int i = dao.findLength();
		System.out.println(i);
	}
	
	/*
	 * 根据id删除图书信息 删除多个
	 */
	@Test
	public void deleteBookinfo() {
		BookinfoDao dao = new BookinfoDao();
		String str="aa,bb,cc";
		int res = dao.deleteBookinfo(str);
		System.out.println(res);
	}
	
	@Test
	public void queryBookName() {
		BookinfoDao dao = new BookinfoDao();
		String bookName = dao.queryBookName("b7832ed7-2bd7-4e9e-9086-37418874399e");
		System.out.println(bookName);
	}
}
