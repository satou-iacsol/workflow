package com.workflow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String authority = (String) session.getAttribute("authority");
		String fullname = (String) session.getAttribute("fullname");
		String affiliationCode = (String) session.getAttribute("affiliationCode");
		String mail = (String) session.getAttribute("mail");
		String affiliationName = (String) session.getAttribute("affiliationName");
		String approverNumber_1 = (String) session.getAttribute("approverNumber_1");
		String approverName_1 = (String) session.getAttribute("approverName_1");
		String approverNumber_2 = (String) session.getAttribute("approverNumber_2");
		String approverName_2 = (String) session.getAttribute("approverName_2");

		String type = (String) request.getParameter("type");
		String date_1 = (String) request.getParameter("date_1");
		String date_2 = (String) request.getParameter("date_2");
		String time_1 = (String) request.getParameter("time_1");
		String time_2 = (String) request.getParameter("time_2");
		String comment = (String) request.getParameter("comment");
		String tellnumber = (String) request.getParameter("tellnumber");
		String bikou = (String) request.getParameter("bikou");
		String approver = (String) request.getParameter("approver");
		String flag = (String) request.getParameter("flag");

		//承認者スキップが入っている場合の承認者表示について
		String approver_1_2 = "";
		if(flag.equals("0")) {
			approver_1_2 = approverName_1;
		}else {
			approver_1_2 = approverName_2;
		}

		//表記変更（YYYY-MM-DD → YYYY年MM月DD日）
		date_1 = date_1.replace("-", "");
		date_2 = date_2.replace("-", "");

		StringBuilder sb_date_1 = new StringBuilder(date_1);
		sb_date_1.insert(4,"年");
		sb_date_1.insert(7,"月");
		sb_date_1.insert(10, "日");
		date_1 = sb_date_1.toString();

		StringBuilder sb_date_2 = new StringBuilder(date_2);
		sb_date_2.insert(4,"年");
		sb_date_2.insert(7,"月");
		sb_date_2.insert(10, "日");
		date_2 = sb_date_2.toString();

		//表記変更（hh:mm → hh時mm分）
		time_1 = time_1.replace(":", "時");
		time_2 = time_2.replace(":", "時");
		time_1 = time_1 + "分";
		time_2 = time_2 + "分";

		session.setAttribute("id", id);
		session.setAttribute("authority",authority);
		session.setAttribute("fullname", fullname);
		session.setAttribute("affiliationCode", affiliationCode);
		session.setAttribute("mail", mail);
		session.setAttribute("affiliationName", affiliationName);
		session.setAttribute("approverNumber_1", approverNumber_1);
		session.setAttribute("approverName_1", approverName_1);
		session.setAttribute("approverNumber_2", approverNumber_2);
		session.setAttribute("approverName_2", approverName_2);

		session.setAttribute("affiliationCode", affiliationCode);
		session.setAttribute("fullname", fullname);
		session.setAttribute("type", type);
		session.setAttribute("date_1", date_1);
		session.setAttribute("date_2", date_2);
		session.setAttribute("time_1", time_1);
		session.setAttribute("time_2", time_2);
		session.setAttribute("comment", comment);
		session.setAttribute("tellnumber", tellnumber);
		session.setAttribute("bikou", bikou);
		session.setAttribute("approver", approver);
		session.setAttribute("approver_1_2", approver_1_2);
		session.setAttribute("flag", flag);
		response.sendRedirect("Confirmation.jsp");
	}

}
