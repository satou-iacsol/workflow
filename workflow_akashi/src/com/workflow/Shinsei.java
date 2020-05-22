package com.workflow;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Shinsei")
public class Shinsei extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Shinsei() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け対策
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		LocalDate localDateTime = LocalDate.now();

		DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		String datetimeformated = datetimeformatter.format(localDateTime);

		System.out.println(datetimeformated);

		HttpSession session = request.getSession();
		String num = (String) session.getAttribute("id");
		String type = request.getParameter("type");
		String fromYear = request.getParameter("fromYear");
		String fromMonth = request.getParameter("fromMonth");
		String fromDay = request.getParameter("fromDay");
		String fromTime = request.getParameter("fromTime");
		String fromMinutes = request.getParameter("fromMinutes");
		String toYear = request.getParameter("toYear");
		String toMonth = request.getParameter("toMonth");
		String toDay = request.getParameter("toDay");
		String toTime = request.getParameter("toTime");
		String toMinutes = request.getParameter("toMinutes");
		String reason = request.getParameter("reason");
		String address = request.getParameter("address");
		String remarks = request.getParameter("remarks");
		String skip = request.getParameter("skip");
		String authorizer = request.getParameter("authorizer");

		String str = datetimeformated.substring(2) + num + toMonth + toDay + "01" + "," + num + "," + type
				+ "," + "20" + fromYear + fromMonth + fromDay + ","
				+ "20" + toYear + toMonth + toDay + "," +
				"20" + fromYear + fromMonth + fromDay + fromTime + fromMinutes
				+ "," + "20" + toYear + toMonth + toDay + toTime + toMinutes + "," +
				reason + "," + address + "," + remarks + "," + skip + "," + authorizer + "," + "," + ",";
		BufferedWriter writer = Files
				.newBufferedWriter(Paths.get("C:/pleiades/workspace/workflow_akashi/WebContent/data.csv"),
						StandardOpenOption.APPEND);
		writer.write(str);
		writer.newLine();
		writer.flush();
		writer.close();

		PrintWriter out = response.getWriter();

		out.println("<html lang='ja'>");
		out.println("<head>");
		out.println("<title>申請完了</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<form action='menu.jsp'");
		out.println("<table border='1' class='table'>");
		out.println("<tr>");
		out.println("<td>" + num + "</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td style='text-align:right'><input type='submit' value='戻る'></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
}