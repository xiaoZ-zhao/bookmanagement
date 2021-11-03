package com.epoint.bookmanagement.action.bookinfo;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.epoint.bookmanagement.bizlogic.bookinfo.BookinfoService;
import com.epoint.bookmanagement.bizlogic.bookinfo.domain.Bookinfo;

//新增图书
@WebServlet("/bookinfoaddaction")
public class BookinfoAddAction extends HttpServlet {

	private BookinfoService bookinfoService = new BookinfoService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if (method == null || method.trim().length() == 0) {
			return;
		}
		
		if(method.equals("addbookinfo")) {
			addBookInfo(req,resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

	/*
	 * 新增图书信息
	 */
	public void addBookInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String data = request.getParameter("data");
		Bookinfo bookInfo = JSON.parseObject(data, Bookinfo.class);
		bookInfo.setBookId(UUID.randomUUID().toString());
		String str = bookinfoService.addBookInfo(bookInfo);
		response.getWriter().write(str);
	}
}
