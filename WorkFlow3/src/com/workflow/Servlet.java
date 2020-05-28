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

/**
 * Servlet implementation class servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Servlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		final String referenceDirectory = (String) session.getAttribute("referenceDirectory");

		String id = request.getParameter("id");
		String pass = request.getParameter("passcode");

		BufferedReader br = null;
		BufferedReader br_2 = null;
		BufferedReader br_3 = null;
		BufferedReader br_4 = null;

		String array[] = new String[5];
		String array_2[] = new String[3];
		String array_3[] = new String[3];
		String array_4[] = new String[3];

		try {
			br = Files.newBufferedReader(
					Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));

			String line = "";

			while ((line = br.readLine()) != null) {
				//array配列にcsvの情報をカンマ区切りで格納
				array = line.split(",", -1);
				//array配列とログイン画面で入力された、情報を突合させ、等しければbreak
				if (array[0].equals(id) && array[1].equals(pass)) {
					break;
				}
				//if文に入らなければ、array配列を初期化
				array = new String[5];
			}

			br_2 = Files.newBufferedReader(
					Paths.get(referenceDirectory + "belongs.csv"),
					Charset.forName("UTF-8"));

			while ((line = br_2.readLine()) != null) {
				//array配列にcsvの情報をカンマ区切りで格納
				array_2 = line.split(",", -1);
				//array配列とログイン画面で入力された、情報を突合させ、等しければbreak
				if (array_2[0].equals(array[4])) {
					break;
				}
				//if文に入らなければ、array配列を初期化
				array_2 = new String[3];
			}

			br_3 = Files.newBufferedReader(
					Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));

			while ((line = br_3.readLine()) != null) {
				//array配列にcsvの情報をカンマ区切りで格納
				array_3 = line.split(",", -1);
				//array配列とログイン画面で入力された、情報を突合させ、等しければbreak
				if (array_2[2].equals(array_3[0])) {
					break;
				}
				//if文に入らなければ、array配列を初期化
				array_3 = new String[3];
			}

			br_4 = Files.newBufferedReader(
					Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));

			while ((line = br_4.readLine()) != null) {
				//array配列にcsvの情報をカンマ区切りで格納
				array_4 = line.split(",", -1);
				//array配列とログイン画面で入力された、情報を突合させ、等しければbreak
				if (array_2[3].equals(array_4[0])) {
					break;
				}
				//if文に入らなければ、array配列を初期化
				array_4 = new String[3];
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
					br_2.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

			session.setAttribute("id", id);
			session.setAttribute("authority", array[2]);
			session.setAttribute("fullname", array[3]);
			session.setAttribute("affiliationCode", array[4]);
			session.setAttribute("mail", array[5]);
			session.setAttribute("affiliationName", array_2[1]);
			session.setAttribute("approverNumber_1", array_2[2]);
			session.setAttribute("approverName_1", array_3[3]);
			session.setAttribute("approverNumber_2", array_2[3]);
			session.setAttribute("approverName_2", array_4[3]);
			response.sendRedirect("menu.jsp");


	}

}
