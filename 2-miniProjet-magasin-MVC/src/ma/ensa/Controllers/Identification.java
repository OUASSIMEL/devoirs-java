package ma.ensa.Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.ensa.Models.Client;

/**
 * Servlet implementation class Identification
 */
@WebServlet("/identification")
public class Identification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Identification() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String mdp = request.getParameter("password");
			Client client = Client.getClient(email, mdp);
			if (client == null) {
				/*
				 * Si le client n existe pas on leve une exception qui
				 * redirige l utilisateur vers la page d identification
				 */
				throw new Exception("non-existant");
			}
			Cookie emailCK = new Cookie("email", email);
			response.addCookie(emailCK);
			Cookie pwCK = new Cookie("motdepasse", mdp);
			response.addCookie(pwCK);
			request.setAttribute("client", client);
			request.getRequestDispatcher("acceuil.jsp").forward(request, response);
		} catch (Exception e) {
			// Redirection vers page d identification
			//System.out.println("Error login");
			String msg = e.getMessage().equals("non-existant")? "nex":"other";
			request.setAttribute("erreur", msg);
			doGet(request, response);
		}
	}

}
