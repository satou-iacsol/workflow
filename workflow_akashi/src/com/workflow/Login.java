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
		String[] employee = new String[5];

		try {
			br = Files.newBufferedReader(Paths.get("C:/pleiades/workspace/workflow_akashi/WebContent/employee_muster.csv"),
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 所属の取得
		BufferedReader brBelongs = null;
		String[] belongs = new String[2];
		try {
			brBelongs = Files.newBufferedReader(Paths.get("C:/pleiades/workspace/workflow_akashi/WebContent/belongs.csv"),
			Charset.forName("UTF-8"));
			String lineBelongs = "";

			while ((lineBelongs = brBelongs.readLine()) != null) {
				belongs = lineBelongs.split(",", -1);
				if (belongs[0].equals(employee[4])) {
			break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				brBelongs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (employee[0] == null) {
			response.sendRedirect("error.jsp");
		} else if(employee[2].equals("0")) {
			HttpSession session = request.getSession();
			// 社員番号 をセッションに渡す
			session.setAttribute("id", id);
			response.sendRedirect("menu1.jsp");
		} else {
			HttpSession session = request.getSession();
			// 社員番号 をセッションに渡す
			session.setAttribute("id", id);
			response.sendRedirect("menu2.jsp");
		}
	}
}

