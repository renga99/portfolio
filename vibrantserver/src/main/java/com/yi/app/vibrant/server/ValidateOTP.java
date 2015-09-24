package com.yi.app.vibrant.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ValidateOTP")
public class ValidateOTP extends HttpServlet{



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String otp = req.getParameter("otp");
		if(!otp.equals(null)){
			resp.sendRedirect("summary.jsp");
		}else{
			resp.sendRedirect("invalid.jsp");
		}

	}

}
