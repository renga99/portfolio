package com.yi.app.vibrant.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String INSERT_SQL = "insert into vibrant.user (regid,otp,last_updated) values(?,?,sysdate())";


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		upload(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		upload(request,response);
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		String share = request.getParameter("shareRegId");
		String regId = "";
		if (share != null && !share.isEmpty()){
			try {
				String otp = request.getParameter("otp");
				String rid = request.getParameter("rid");
				Connection con = ConnectionManager.getDbCon().getConnection();
				PreparedStatement ps = con.prepareStatement(INSERT_SQL);
				ps.setString(1, rid);
				ps.setString(2, otp);

				ps.executeUpdate();


			}
			catch(Throwable t) {
				t.printStackTrace();
			}
		}
	}


}
