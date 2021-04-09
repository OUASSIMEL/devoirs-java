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
@WebServlet("/inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Inscription() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Client client = new Client();
			String email = request.getParameter("email"), password = request.getParameter("password");
			String fname = request.getParameter("fname"), lname = request.getParameter("lname");
			String adresse = request.getParameter("address"), ville = request.getParameter("city");
			String telephone = request.getParameter("phone");
			int zip = Integer.parseInt(request.getParameter("zip"));

			client.setEmail(email);
			client.setMdp(password);
			client.setNom(lname);
			client.setPrenom(fname);
			client.setAdresse(adresse);
			client.setVille(ville);
			client.setZip(zip);
			client.setTelephone(telephone);

			/*
			 * Enregistrer le client dans la DB
			 * Si l email est deja utilise on leve un exception pour
			 * renvoyer l utilisateur vers la page d inscription
			 */
			client.saveClient();
			Cookie emailCK = new Cookie("email", email);
			response.addCookie(emailCK);
			Cookie pwCK = new Cookie("motdepasse", password);
			response.addCookie(pwCK);
			
			request.setAttribute("client", client);
			request.getRequestDispatcher("acceuil.jsp").forward(request, response);
		} catch (Exception e) {
			// Redirection vers la page d inscription
			String msg = e.getMessage().equals("duplicate")? "duplicate":"other";
			request.setAttribute("erreur", msg);
			
			doGet(request, response);
		}
	}

}
