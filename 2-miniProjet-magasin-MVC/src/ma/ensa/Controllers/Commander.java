package ma.ensa.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ma.ensa.Models.Article;
import ma.ensa.Models.Client;
import ma.ensa.Models.Commande;
import ma.ensa.Utils.CookiesHelper;

/**
 * Servlet implementation class Commander
 */
@WebServlet("/commander")
public class Commander extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Commander() {
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

		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		List<String> attributes = Collections.list(names);
		int nbArticles = attributes.size();
		if (nbArticles == 0) {			
			// Pas d articles dans le panier
			request.getRequestDispatcher("catalogue.jsp").forward(request, response);
			return;
		}
		List<Article> panier = new ArrayList<>();
		for (String attribute : attributes) {
			if (attribute.startsWith("article")) {
				Article ar = (Article) request.getSession().getAttribute(attribute);
				panier.add(ar);
			}
		}

		int numCommande = Commande.passerCommande(panier, client);
		if (numCommande < 0) {
			// En cas d erreur
			request.getRequestDispatcher("achat.jsp").forward(request, response);
		} else {
			request.getSession().invalidate();
			request.setAttribute("numCommande", numCommande);
			request.getRequestDispatcher("resume.jsp").forward(request, response);
		}
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
