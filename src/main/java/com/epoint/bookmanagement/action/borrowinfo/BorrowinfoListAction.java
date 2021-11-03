package com.epoint.bookmanagement.action.borrowinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanagement.bizlogic.bookinfo.BookinfoService;
import com.epoint.bookmanagement.bizlogic.borrowinfo.BorrowinfoService;
import com.epoint.bookmanagement.bizlogic.borrowinfo.domain.Borrowinfo;

//列表数据加载、删除、查询相关的逻辑
@WebServlet("/borrowinfolistaction")
public class BorrowinfoListAction extends HttpServlet{
	
	private BorrowinfoService borrowinfoService = new BorrowinfoService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");

		if (StringUtils.isNotBlank(method)) {
			if ("qurryborrowinfo".equals(method)) {
				queryBorrowinfo(req, resp);
			}
			if("deleteborrowinfo".equals(method)) {
				deleteBorrowInfo(req,resp);
			}
			if("returnbook".equals(method)) {
				returnBook(req,resp);
			}
		}
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req,resp);
	}


	/*
	 * 查询借阅信息列表
	 */
	public void queryBorrowinfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageIndex=Integer.parseInt(req.getParameter("pageIndex"));
		int pageSize=Integer.parseInt(req.getParameter("pageSize"));
		String sortField=req.getParameter("sortField");
		String sortOrder = req.getParameter("sortOrder");
		
		List<Borrowinfo> borrowinfoList=new ArrayList<>();
		borrowinfoList=borrowinfoService.queryBorrowInfo(pageIndex, pageSize, sortField, sortOrder);
		List<Map<String,Object>> borrowinfoMapList=new ArrayList<>();
		for(Borrowinfo borrowinfo:borrowinfoList) {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("borrowId",borrowinfo.getBorrowId());
			map.put("bookId", borrowinfo.getBookId());
			map.put("borrower", borrowinfo.getBorrower());
			map.put("phone",borrowinfo.getPhone());
			map.put("borrowTime",borrowinfo.getBorrowTime());
			map.put("returnTim", borrowinfo.getReturnTim());
			map.put("bookName",getBookName(borrowinfo.getBookId()));
			borrowinfoMapList.add(map);
		}
		
		int bookinfoListTotalSize=0;
		int listLength=borrowinfoService.findLength();
		bookinfoListTotalSize=borrowinfoService.queryBorrowInfo(0, listLength, "", "").size();
		Map<String,Object> json=new HashMap<String, Object>();
		json.put("data", borrowinfoMapList);
		json.put("total", bookinfoListTotalSize);
		resp.getWriter().write(JSONObject.toJSONStringWithDateFormat(json,"yyyy-MM-dd HH:mm:ss"));
	}


	/*
	 * 删除借阅信息
	 */
	public void deleteBorrowInfo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String borrowId=req.getParameter("borrowid");
		String str = borrowinfoService.deleteBorrowInfoByBorrowId(borrowId);
		resp.getWriter().write(str);
	}
	
	/*
	 * 归还图书
	 */
	public void returnBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String borrowId=req.getParameter("borrowid");
		Borrowinfo borrowinfo = borrowinfoService.queryBorrowInfoByID(borrowId);
		borrowinfo.setReturnTim(new Date());
		BookinfoService bookinfoService = new BookinfoService();
		String str = bookinfoService.returnBook(borrowinfo);
		resp.getWriter().write(str);
		
	}
	
	/*
	 * 获取图书名称
	 */
	private String getBookName(String bookId) {
		// TODO Auto-generated method stub
		BookinfoService bookinfoService=new BookinfoService();
		String bookName = bookinfoService.queryBookName(bookId);
		return bookName==null?" ":bookName;
	}
}
