package ma.ensa.Controllers;

import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.ensa.Models.Client;
import ma.ensa.Models.Commande;
import ma.ensa.Utils.CookiesHelper;

/**
 * Servlet implementation class HistoriqueCommandes
 */
@WebServlet("/historique")
public class HistoriqueCommandes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HistoriqueCommandes() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get client
		Cookie[] cookies = request.getCookies();
		Client client = CookiesHelper.chercheClient(cookies);
		if (client == null) {
			// Appel servlet identification
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("identification");
			return;
		}
		// System.out.println("USER FOUND");
		List<Commande> commandes = Commande.getCommandes(client);
		System.out.println(commandes.toArray().toString());
		request.setAttribute("commandes", commandes);
		request.getRequestDispatcher("historique.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
