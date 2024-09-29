package com.controllers.accounts;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.services.impl.UserServicesImpl;
import com.utils.Constant;
import com.models.UserModel;
import com.services.IUserServices;

@WebServlet(urlPatterns = { "/login" })

public class LoginController extends HttpServlet {

	private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return Constant.DEFAULT_FILENAME;
    }
	private static final long serialVersionUID = 1L;
	IUserServices services = new UserServicesImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("views/accounts/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		// lay tham so tu view login gui ve
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean isRememberMe = false;
		String remember = req.getParameter("remember");

		//kiem tra tham so
		if ("on".equals(remember)) {
			isRememberMe = true;
		}
		String alertMsg = "";
		if (username.isEmpty() || password.isEmpty()) {
			alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("views/accounts/login.jsp").forward(req, resp);
			return;
		}
		
		//xu ly bai toan login
		UserModel user = services.login(username, password);
		if(user!=null){
			 HttpSession session = req.getSession(true);
			 session.setAttribute("account", user);
			 session.setAttribute("username", username);
			 if(isRememberMe){
			 saveRemeberMe(resp, username);
			 }

			 resp.sendRedirect(req.getContextPath()+"/waiting");
			 UserModel profile = services.FindByUserName(username);
			 session.setAttribute("profile", profile);
			 }else{
			 alertMsg = "Tài khoản hoặc mật khẩu không đúng";
			 req.setAttribute("alert", alertMsg);
			 req.getRequestDispatcher("views/accounts/login.jsp").forward(req, resp);
			 }
			 }

	private void saveRemeberMe(HttpServletResponse resp, String username) {
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
		 cookie.setMaxAge(30*60);
		 resp.addCookie(cookie);
		 }

	}
