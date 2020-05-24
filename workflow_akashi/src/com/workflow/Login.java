package com.workflow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String referenceDirectory = "C:/Users/明石佑介/git/workflow/workflow_akashi/WebContent/";


	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		BufferedReader br = null;
		String[] employee = new String[6];
		BufferedReader brBelongs = null;
		String[] belongs = new String[2];
		BufferedReader brApprover1 = null;
		String[] approver1 = new String[6];
		BufferedReader brApprover2 = null;
		String[] approver2 = new String[6];

		try {
			// ログイン者社員マスタの取得
			br = Files.newBufferedReader(
					Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));
			String line = "";

			while ((line = br.readLine()) != null) {
				employee = line.split(",", -1);
				// ログインの判定
				if (employee[0].equals(id) && employee[1].equals(pass)) {
					break;
				}
				employee = new String[5];
			}

			// 所属マスタの取得

			brBelongs = Files.newBufferedReader(
					Paths.get(referenceDirectory + "belongs.csv"),
					Charset.forName("UTF-8"));
			String lineBelongs = "";

			while ((lineBelongs = brBelongs.readLine()) != null) {
				belongs = lineBelongs.split(",", -1);
				if (belongs[0].equals(employee[4])) {
					break;
				}
			}

			// 承認者１社員マスタの取得
			brApprover1 = Files.newBufferedReader(
					Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));
			String lineApprover1 = "";

			while ((lineApprover1 = brApprover1.readLine()) != null) {
				approver1 = lineApprover1.split(",", -1);
				// ログインの判定
				if (approver1[0].equals(belongs[2])) {
					break;
				}
				approver1 = new String[5];

			}

			// 承認者２社員マスタの取得
			brApprover2 = Files.newBufferedReader(
					Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));
			String lineApprover2 = "";

			while ((lineApprover2 = brApprover2.readLine()) != null) {
				approver2 = lineApprover2.split(",", -1);
				// ログインの判定
				if (approver2[0].equals(belongs[3])) {
					break;
				}
				approver2 = new String[5];
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				brBelongs.close();
				brApprover1.close();
				brApprover2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (employee[0] == null) {
			response.sendRedirect("error.jsp");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("authority", employee[2]);
			session.setAttribute("fullname", employee[3]);
			session.setAttribute("affiliationCode", employee[4]);
			session.setAttribute("mail", employee[5]);
			session.setAttribute("affiliationName", belongs[1]);
			session.setAttribute("approverNumber_1", belongs[2]);
			session.setAttribute("approverName_1", approver1[3]);
			session.setAttribute("approverNumber_2", belongs[3]);
			session.setAttribute("approverName_2", approver2[3]);

			if (employee[2].equals("0")) {
				response.sendRedirect("menu1.jsp");
			} else {
				response.sendRedirect("menu2.jsp");
			}
		}

	}
}
