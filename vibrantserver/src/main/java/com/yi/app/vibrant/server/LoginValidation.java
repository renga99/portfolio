package com.yi.app.vibrant.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginValidation")
public class LoginValidation extends HttpServlet{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;




	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("inside get");
		String loginid = req.getParameter("loginid").trim();
		String password = req.getParameter("password").trim();

		try{
			if(loginid.equals("rengaraj")&& password.equals("admin")){
				resp.sendRedirect("phone.jsp");
			}
			else{
				resp.sendRedirect("invalid.jsp");
			}
		}
		catch(Throwable e){
			System.out.println(e);
		}
	}




}
