package com.patientmonitoring;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static RequestDispatcher requestDispatcher;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		String user = request.getParameter("user");
		session.setAttribute("user", user);
		if(user.equals("doctor")) {
			requestDispatcher = request.getRequestDispatcher("/doctor.jsp");
			requestDispatcher.forward(request, response);
		}
		else if(user.equals("ambulance")) {
			requestDispatcher = request.getRequestDispatcher("/ambulance.jsp");
			requestDispatcher.forward(request, response);
		}
		else {
			requestDispatcher = request.getRequestDispatcher("/patient.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}