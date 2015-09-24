package com.yi.app.vibrant.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendSMS")
public class SendSMS extends HttpServlet{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;




	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


				resp.sendRedirect("push.jsp");

	}




}
