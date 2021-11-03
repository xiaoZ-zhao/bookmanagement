package com.epoint.bookmanagement.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "CharacterFilter" ,urlPatterns = "/*" ,initParams = {@WebInitParam(name = "encoding",value = "utf-8")})
public class CharacterFilter implements Filter{
	
	//字符编码
	private String encoding;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		//获取初始化参数
        encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (encoding!=null) {
            //设置request字符编码
			request.setCharacterEncoding(encoding);
            //设置response字符编码
			response.setContentType("text/html;charset="+encoding);
        }
        //传递给下一个过滤器
		chain.doFilter(request,response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		encoding=null;
	}

}
