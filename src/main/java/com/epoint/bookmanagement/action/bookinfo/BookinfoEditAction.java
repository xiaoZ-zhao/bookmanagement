package com.epoint.bookmanagement.action.bookinfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanagement.bizlogic.bookinfo.BookinfoService;
import com.epoint.bookmanagement.bizlogic.bookinfo.domain.Bookinfo;

//修改图书信息
@WebServlet("/bookinfoeditaction")
public class BookinfoEditAction extends HttpServlet{
	
	private BookinfoService bookinfoService=new BookinfoService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if (method == null || method.trim().length() == 0) {
			return;
		}
		
		if(("updatebookinfo").equals(method)) {
			updateBookInfo(req,resp);
		}else if(("querybookinfobyid").equals(method)) {
			queryBookInfoByID(req,resp);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
	/*
	 * 更新图书信息
	 */
	public void updateBookInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String data = request.getParameter("data");
		System.out.println(data);
		Bookinfo bookinfo = JSON.parseObject(data,Bookinfo.class);
		System.out.println(bookinfo.toString());
		String str = bookinfoService.updateBookInfo(bookinfo);
		response.getWriter().write(str);
	}
	
	/*
	 * 根据id查询实体
	 */
	public void queryBookInfoByID(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String bookId=request.getParameter("bookid");
		Bookinfo bookinfo = bookinfoService.queryBookInfoByID(bookId);
		response.getWriter().write(JSONObject.toJSONString(bookinfo));
	}
}
