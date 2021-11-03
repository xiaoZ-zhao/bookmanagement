package com.epoint.bookmanagement.bizlogic.bookinfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.epoint.bookmanagement.bizlogic.bookinfo.domain.Bookinfo;
import com.epoint.bookmanagement.bizlogic.borrowinfo.domain.Borrowinfo;
import com.epoint.bookmanagement.dao.BookinfoDao;
import com.epoint.bookmanagement.dao.BorrowinfoDao;

public class BookinfoService {

	private static BookinfoDao bookinfoDao = new BookinfoDao();
	private int i=0;
	
	/*
	 * 添加图书信息
	 */
	public String addBookInfo(Bookinfo bookInfo) {
		//重名验证
		Bookinfo existBookInfo = bookinfoDao.checkExist(bookInfo.getBookName(), bookInfo.getAuthor());
		if(existBookInfo !=null) {
			return "该作者下已有此书";
		}else {
			i = bookinfoDao.addBookinfo(bookInfo);
			if (i > 0) {
				return "数据添加成功";
			} else {
				return "数据添加失败";
			}
		}
	}
	
	/*
	 * 更新图书信息
	 */
//	public String updateBookInfo(Bookinfo bookInfo) {
//		
//		i=bookinfoDao.updateBookInfo(bookInfo);
//		if (i > 0) {
//			return "数据更新成功";
//		} else {
//			return "数据更新失败";
//		}
//	}
	
	/*
	 * 更新图书信息
	 */
	public String updateBookInfo(Bookinfo bookInfo) {
		Bookinfo before = bookinfoDao.queryBookInfoByID(bookInfo.getBookId());
		String bookNameBefore = before.getBookName();
		String bookNameNow=bookInfo.getBookName();
		
		Bookinfo bookInfoExist = bookinfoDao.checkExist(bookNameBefore, bookNameNow, bookInfo.getAuthor());
		if(bookInfoExist != null) {
			return "该作者下已有此图书";
		}else {
			i=bookinfoDao.updateBookInfo(bookInfo);
			if (i > 0) {
				return "数据更新成功";
			} else {
				return "数据更新失败";
			}
		}
	}
	
	
	
	/*
	 * 删除图书信息（传入单个图书id删除单条数据）
	 */
	public String deleteBookInfoByBookId(String bookid) {
		i=bookinfoDao.deleteBookinfoByBookId(bookid);
		if (i > 0) {
			return "数据删除成功";
		} else {
			return "数据删除失败";
		}
	}
	
	/*
	 * 删除图书信息（传入多个图书id删除单条数据）
	 */
	public String deleteBookInfo(String bookids) {
		
//		i=bookinfoDao.deleteBookinfo(bookids);
//		if (i > 0) {
//			return "数据删除成功";
//		} else {
//			return "数据删除失败";
//		}
		
		List<String> deleteSuccess=new ArrayList<>();
		List<String> deleteFail=new ArrayList<>();
		List<String> deleteError=new ArrayList<>();
		String[] str=bookids.split(",");
		for(String bookId : str) {
			Bookinfo bookinfo = bookinfoDao.queryBookInfoByID(bookId);
			BorrowinfoDao borrowinfoDao = new BorrowinfoDao();
			int totalCount=borrowinfoDao.queryBorrowInfoCountByBookId(bookId);
			if(totalCount>0) {
				deleteFail.add(bookinfo.getBookName());
			}else {
				int m=bookinfoDao.deleteBookinfoByBookId(bookId);
				if(m>0) {
					deleteSuccess.add(bookinfo.getBookName());
				}else {
					deleteError.add(bookinfo.getBookName());
				}
			}
		}
		StringBuffer resString = new StringBuffer();
		if(!deleteSuccess.isEmpty()) {
			resString.append(StringUtils.join(deleteSuccess, "、")).append("删除成功 </br>");
		}
		if(!deleteFail.isEmpty()) {
			resString.append(StringUtils.join(deleteFail,"、")).append("存在借阅信息，删除失败</br>");
		}
		if(!deleteError.isEmpty()) {
			resString.append(StringUtils.join(deleteError,"、")).append("删除发生错误</br>");
		}
		
		return resString.toString();
	}
	
	/*
	 * 根据图书id查询图书信息
	 */
	public Bookinfo queryBookInfoByID(String bookid) {
		return bookinfoDao.queryBookInfoByID(bookid);
	}
	
	/*
	 * 查询图书信息列表（返回查询的实体集合）
	 */
	public List<Bookinfo> queryBookinfoByKeyWords(int pageIndex, int pageSize, String bookName, Integer bookType){
		 List<Bookinfo> list = bookinfoDao.queryBookinfoByKeyWords(pageIndex, pageSize, bookName, bookType);
		 return list;
	}
	
	/*
	 * 查询图书信息表中所有数据的条数
	 */
	public int findLength() {
		return bookinfoDao.findLength();
	}
	
	/*
	 * 根据图书ID查找图书bookName
	 */
	public String queryBookName(String bookId) {
		return bookinfoDao.queryBookName(bookId);
	}
	
	/*
	 * 归还图书
	 */
	public String returnBook(Borrowinfo borrowinfo) {
		i=bookinfoDao.returnBook(borrowinfo);
		if (i > 0) {
			return "图书归还成功";
		} else {
			return "图书归还失败";
		}
	}
}
