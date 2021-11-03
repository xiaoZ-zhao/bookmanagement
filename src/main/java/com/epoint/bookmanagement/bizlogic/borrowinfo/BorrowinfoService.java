package com.epoint.bookmanagement.bizlogic.borrowinfo;

import java.util.List;

import com.epoint.bookmanagement.bizlogic.borrowinfo.domain.Borrowinfo;
import com.epoint.bookmanagement.dao.BorrowinfoDao;

public class BorrowinfoService {

	private BorrowinfoDao borrowinfoDao = new BorrowinfoDao();
	private int i = 0;

	/*
	 * 添加借阅信息
	 */
	public String addBorrowInfo(Borrowinfo borrowinfo) {
		i = borrowinfoDao.addBorrowInfo(borrowinfo);
		if (i > 0) {
			return "数据添加成功";
		} else {
			return "数据添加失败";
		}
	}

	/*
	 * 更新借阅信息
	 */
	public String updateBorrowInfo(Borrowinfo borrowinfo) {
		i = borrowinfoDao.updateBorrowInfo(borrowinfo);
		if (i > 0) {
			return "数据更新成功";
		} else {
			return "数据更新失败";
		}
	}

	/*
	 * 删除借阅信息（传入单个图书id删除单条数据）
	 */
	public String deleteBorrowInfoByBorrowId(String borrowid) {
		i = borrowinfoDao.deleteBorrowinfoByBorrowId(borrowid);
		if (i > 0) {
			return "数据删除成功";
		} else {
			return "数据删除失败";
		}
	}

	/*
	 * 根据图书id查询图书信息
	 */
	public Borrowinfo queryBorrowInfoByID(String borrowid) {
		return borrowinfoDao.queryBorrowInfoByID(borrowid);
	}


	/*
	 * 查询借阅信息表中所有数据的条数
	 */
	public int findLength() {
		return borrowinfoDao.findLength();
	}
	
	/*
	 * 查询借阅信息列表
	 */
	public List<Borrowinfo> queryBorrowInfo(int pageIndex,int pageSize,String sortField,String sortOrder){
		return borrowinfoDao.queryBookinfoByKeyWords(pageIndex, pageSize, sortField, sortOrder);
	}
	
	/*
	 * 获得下一个借阅ID
	 */
	public String getNextBorrowId() {
		return borrowinfoDao.getNextBorrowId();
	}
}
