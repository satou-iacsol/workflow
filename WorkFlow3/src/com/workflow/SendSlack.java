package com.workflow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SendSlack")
public class SendSlack extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendSlack() {
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

		PrintWriter out = response.getWriter();

		String sendAction = (String) session.getAttribute("sendAction");
		String fromName = (String) session.getAttribute("fullname");

		if (((String) session.getAttribute("sendAction")).equals("承認２")
				|| ((String) session.getAttribute("sendAction")).equals("差戻２")) {
			String number = (String) session.getAttribute("approvedNumber");
			String approvedName = (String) session.getAttribute("approvedName");

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>SendSlack</title>");
			out.println("</head>");
			out.println("<body>");
			out.println(sendAction);
			out.println("<hr>");
			out.println(fromName);
			out.println("から");
			out.println(approvedName);
			out.println("に通知<br>");
			out.println(number);
			out.println("<br>");
			out.println("有給申請が");
			out.println(sendAction.substring(0, 2));
			out.println("されました。<br>");
			out.println("</body>");
			out.println("</html>");

			out.close();

		} else if (sendAction.equals("承認")
				|| sendAction.equals("差戻")) {
			String number = (String) session.getAttribute("approvedNumber");

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>SendSlack</title>");
			out.println("</head>");
			out.println("<body>");
			out.println(sendAction);
			out.println("<hr>");
			out.println(fromName);
			out.println("から");

			if (sendAction.equals("承認")) {
				out.println(session.getAttribute("approverName_2"));

			} else if (sendAction.equals("差戻")) {
				out.println(session.getAttribute("approverName_1"));

			}

			out.println("に通知<br>");
			out.println(number);
			out.println("<br>");
			out.println("有給申請が");
			out.println(sendAction.substring(0, 2));
			out.println("されました。<br>");
			out.println("</body>");
			out.println("</html>");

			out.close();

		} else {
			@SuppressWarnings("unchecked")
			ArrayList<String> historyList = (ArrayList<String>) session.getAttribute("historyList");
			String approve1notification = (String) session.getAttribute("approve1notification");
			String approve2notification = (String) session.getAttribute("approve2notification");

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>SendSlack</title>");
			out.println("</head>");
			out.println("<body>");
			out.println(sendAction);
			out.println("<hr>");
			out.println(fromName);
			out.println("から");

			if (approve1notification.equals("1")) {

				out.println(session.getAttribute("approverName_1"));

			}
			if (approve2notification.equals("1")) {

				out.println(session.getAttribute("approverName_2"));

			}
			out.println("に通知<br>");
			out.println(historyList.get(1));
			out.println("<br>");
			out.println("有給申請が");
			out.println(sendAction.substring(0, 2));
			out.println("されました。<br>");
			if (sendAction.equals("修正")) {

				out.println("新しい申請番号は<br>");
				out.println(historyList.get(1).substring(0, 14)
						+ String.format("%02d", Integer.parseInt(historyList.get(1).substring(14)) + 1));
				out.println("となります。<br>");

			}
			out.println("</body>");
			out.println("</html>");

			out.close();
		}
		//		response.sendRedirect("menu.jsp");
	}

}
