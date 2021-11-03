package com.epoint.bookmanagement.action.borrowinfo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanagement.bizlogic.bookinfo.BookinfoService;
import com.epoint.bookmanagement.bizlogic.bookinfo.domain.Bookinfo;
import com.epoint.bookmanagement.bizlogic.borrowinfo.BorrowinfoService;
import com.epoint.bookmanagement.bizlogic.borrowinfo.domain.Borrowinfo;

//新增相关的逻辑
@WebServlet("/borrowinfoaddaction")
public class BorrowinfoAddAction extends HttpServlet {

	private BorrowinfoService borrowinfoService = new BorrowinfoService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");

		if (StringUtils.isNotBlank(method)) {
			if ("querybookinfo".equals(method)) {
				queryBookInfo(req, resp);
			}
			if("addborrowinfo".equals(method)) {
				addBorrowinfo(req,resp);
			}
			if("getnextborrowid".equals(method)) {
				getNextBorrowId(req,resp);
			}
			if("getbooknamebybookid".equals(method)) {
				getBookNameByBookId(req,resp);
			}
		}
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }


	/*
	 * 借阅信息新增页 下拉列表查询图书信息
	 */
	public void queryBookInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BookinfoService bookinfoService = new BookinfoService();
		// 获得表1列表数据
		List<Bookinfo> bookinfolist = bookinfoService.queryBookinfoByKeyWords(0, bookinfoService.findLength(), null,
				null);
		for (Bookinfo bookinfo : bookinfolist) {
			bookinfo.setBookName(bookinfo.getBookName() + "(" + bookinfo.getRemain() + ")");
		}
		resp.getWriter().write(JSONObject.toJSONString(bookinfolist));
	}
	
	/*
	 * 添加借阅信息
	 */
	public void addBorrowinfo (HttpServletRequest req, HttpServletResponse resp) 
				throws ServletException, IOException {
		String data=req.getParameter("data");
		Borrowinfo borrowinfo=JSON.parseObject(data,Borrowinfo.class);
		
		String bookId=borrowinfo.getBookId();
		BookinfoService bookinfoService=new BookinfoService();
		Bookinfo bookinfo = bookinfoService.queryBookInfoByID(bookId);
		if(bookinfo!=null && bookinfo.getRemain()>0) {
			String str=borrowinfoService.addBorrowInfo(borrowinfo);
			resp.getWriter().write(str);
		}else {
			resp.getWriter().write("图书剩余数量不走，无法借阅！");
		}
	}
	
	/*
	 * 获得下一个借阅编号
	 */
	public void getNextBorrowId (HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String data=req.getParameter("data");
		Borrowinfo borrowinfo=JSON.parseObject(data,Borrowinfo.class);
		String str = borrowinfoService.getNextBorrowId();
		resp.getWriter().write(str);
	}
	
	/*
	 * 根据编号查询图书名称
	 */
	public void getBookNameByBookId(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String bookId=req.getParameter("bookid");
		BookinfoService bookinfoService = new BookinfoService();
		String str = bookinfoService.queryBookName(bookId);
		resp.getWriter().write(str);
	}
}
