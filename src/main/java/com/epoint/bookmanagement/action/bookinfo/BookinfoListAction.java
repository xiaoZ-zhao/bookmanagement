package com.epoint.bookmanagement.action.bookinfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.print.attribute.HashAttributeSet;
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

//列表数据加载、删除、查询相关的逻辑
@WebServlet("/bookinfolistaction")
public class BookinfoListAction extends HttpServlet{
	
	private BookinfoService bookinfoService = new BookinfoService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if (method == null || method.trim().length() == 0) {
			return;
		}
		
		if(method.equals("querybookinfobykeywords")) {
			queryBookinfoByKeyWords(req,resp);
		}else if(method.equals("deletebookinfo")) {
			deleteBookInfo(req,resp);
		}
	}

	/*
	 * 根据关键字查询图书列表
	 */
	public void queryBookinfoByKeyWords(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize=Integer.parseInt(request.getParameter("pageSize"));
		String bookName=request.getParameter("bookName");
		String bookType=request.getParameter("bookType");

		Integer bookTypeValue=null;
		if(StringUtils.isNotBlank(bookType)) {
			bookTypeValue=Integer.parseInt(bookType);
		}
		
		List<Bookinfo> bookinfoList = bookinfoService.queryBookinfoByKeyWords(pageIndex, pageSize, bookName, bookTypeValue);
	
		int total=bookinfoService.findLength();
		int listSize=bookinfoService.queryBookinfoByKeyWords(0, total, bookName, bookTypeValue).size();
		HashMap<String,Object> result=new HashMap<>();
		result.put("total", listSize);
		result.put("data", bookinfoList);
		
		response.getWriter().write(JSONObject.toJSONString(result));
		
	}
	
	/*
	 * 删除数据
	 */
	public void deleteBookInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookIds=request.getParameter("bookids");
		String str = bookinfoService.deleteBookInfo(bookIds);
		response.getWriter().write(str);
	}
	
}
