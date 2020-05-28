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

@WebServlet("/SendMail")
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendMail() {
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
		final String referenceDirectory = (String) session.getAttribute("referenceDirectory");

		String subject = "";
		String content = "";

		BufferedReader brSend = null;
		String[] send = new String[6];
		try {
			brSend = Files.newBufferedReader(
					Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));
			String lineSend = "";

			while ((lineSend = brSend.readLine()) != null) {
				send = lineSend.split(",", -1);
				if (send[0].equals((String) session.getAttribute("Id"))) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				brSend.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (((String) session.getAttribute("approvedResult")).equals("承認")) {
			BufferedReader brEmployee = null;
			String[] employee = new String[6];
			try {
				brEmployee = Files.newBufferedReader(
						Paths.get(referenceDirectory + "employee_muster.csv"),
						Charset.forName("UTF-8"));
				String lineEmployee = "";

				while ((lineEmployee = brEmployee.readLine()) != null) {
					employee = lineEmployee.split(",", -1);
					if (employee[0].equals((String) session.getAttribute("approve1Id"))) {
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					brEmployee.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (((String) session.getAttribute("approvedResult")).equals("差戻")) {
			BufferedReader brEmployee = null;
			String[] employee = new String[6];
			try {
				brEmployee = Files.newBufferedReader(
						Paths.get(referenceDirectory + "employee_muster.csv"),
						Charset.forName("UTF-8"));
				String lineEmployee = "";

				while ((lineEmployee = brEmployee.readLine()) != null) {
					employee = lineEmployee.split(",", -1);
					if (employee[0].equals((String) session.getAttribute("approve2Id"))) {
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					brEmployee.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			BufferedReader brEmployee = null;
			String[] employee = new String[6];
			try {
				brEmployee = Files.newBufferedReader(
						Paths.get(referenceDirectory + "employee_muster.csv"),
						Charset.forName("UTF-8"));
				String lineEmployee = "";

				while ((lineEmployee = brEmployee.readLine()) != null) {
					employee = lineEmployee.split(",", -1);
					if (employee[0].equals((String) session.getAttribute("approvedId"))) {
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					brEmployee.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	response.sendRedirect("menu.jsp");
	}

}
