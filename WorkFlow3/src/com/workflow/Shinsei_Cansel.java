package com.workflow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Shinsei_Cansel
 */
@WebServlet("/Shinsei_Cansel")
public class Shinsei_Cansel extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Shinsei_Cansel() {
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
		if(authority.equals("0")) {
			response.sendRedirect("menu1.jsp");
		}else {
			response.sendRedirect("menu2.jsp");
		}
	}

}
