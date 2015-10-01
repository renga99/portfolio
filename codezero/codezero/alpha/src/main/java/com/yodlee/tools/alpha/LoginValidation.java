package com.yodlee.tools.alpha;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.yodlee.dap.gatherer.validationutils.ScriptUtil;

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
		String loginUrl = req.getParameter("loginUrlName").trim();
		try{
		ServletContext sc = req.getServletContext();

		String source = getModifiedPage(loginUrl,sc.getRealPath("/index.jsp"));
		source = updateClass(source);
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
//		resp.getWriter().write("<html><body>");
//		resp.getWriter().write("<iframe id=\"myframe\" name=\"myframename\" srcdoc=\"");
		resp.getWriter().write(source);
//		resp.getWriter().write("\"></iframe>");
//		resp.getWriter().write("</body></html>");
		resp.getWriter().flush();
		resp.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}




	private String updateClass(String source) {

		Document doc = Jsoup.parse(source);
//		Elements elements = doc.body().select("*");
//		//elements.attr("style", ":hover {background: yellow}");
//		elements.append("<link rel=\"stylesheet\" href=\"http://localhost:8080/alpha/custom.css\">");
//		elements.addClass("hellothere");

		Element head = doc.head();
		Element body = doc.body();
		//elements.attr("style", ":hover {background: yellow}");
		head.append("<link rel=\"stylesheet\" href=\"http://localhost:8080/alpha/combined.css\">");
		head.append("<script type=\"text/javascript\" src=\"http://localhost:8080/alpha/combined.js\"></script>");
		head.append("<script type=\"text/javascript\" src=\"http://localhost:8080/alpha/mutation-summary.js\"></script>");
		head.append("<script type=\"text/javascript\" src=\"http://localhost:8080/alpha/custom.js\"></script>");
//		head.append("<script type=\"text/javascript\" src=\"https://togetherjs.com/togetherjs-min.js\"></script>");
//		body.append("<button onclick=\"TogetherJS(this); return false;\">Start TogetherJS</button>");
		Elements elements = doc.body().children().select("*");
		elements.addClass("hellothere");
		Elements images = doc.select("img");
		for(Element image : images){
			String url = image.absUrl("src");
		}
		return doc.toString();
	}




	private String getModifiedPage(String loginUrl, String path) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		System.setProperty("webdriver.chrome.driver", "D://apps//chromedriver_win32//chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(loginUrl);

		String source = driver.getPageSource();

		Document doc = Jsoup.parse(source);
		Elements images = doc.select("img");
		for(Element image : images){
			String url = image.attr("src");
			System.out.println(url);
			if(url.contains(".gif") || url.contains(".png") || url.contains(".jpg") || url.contains(".jpeg") ||
					url.contains(".ico")){

				if(url.contains("http") || url.contains("https")){
				list.add(url.trim());
				}else
				{
					list.add(loginUrl+url.trim());
				}
			}
		}
		System.out.println(list);

//		FileDownloader downloadTestFile = new FileDownloader(driver);
//
//		for(String url : list){
//			downloadTestFile.downloadImage(url,path);
//		}
		driver.close();
		return source;


	}




}
