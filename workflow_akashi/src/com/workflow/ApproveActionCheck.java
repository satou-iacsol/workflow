package com.workflow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ApproveActionCheck")
public class ApproveActionCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApproveActionCheck() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();

		String comment = (String)request.getParameter("comment");
		String action = (String)request.getParameter("action");
		session.setAttribute("approvedComment", comment);
		session.setAttribute("approvedAction", action);

		if(action.equals("差戻") && comment.equals("")){
			session.setAttribute("approvedCommentError", "差戻時はコメントが必須です");
			response.sendRedirect("approveAction.jsp");
		} else {
			response.sendRedirect("approveCheck.jsp");
		}
	}

}
