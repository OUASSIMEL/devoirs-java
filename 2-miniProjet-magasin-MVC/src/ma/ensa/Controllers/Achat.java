package ma.ensa.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ma.ensa.Models.Article;

/**
 * Servlet implementation class Achat
 */
@WebServlet("/achat")
public class Achat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Achat() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		List<String> attributes = Collections.list(names);
		if (request.getParameter("code") != null) {
			// code de l article a ajouter au panier
			String code = (String) request.getParameter("code");
			// Recherche de l article
			Article article = Article.getArticle(code);
			// Nombre d articles dans la session
			int nbArticles = attributes.size();
			nbArticles++;
			// Ajout de l article a la session
			session.setAttribute("article" + nbArticles, article);
		}

		boolean vide = true;
		for (String attribute : attributes) {
			if (attribute.startsWith("article")) {
				vide = false;
				break;
			}
		}
		if (vide) {
			System.out.println("REDIRECTION");
			request.setAttribute("erreur", "vide");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/catalogue");
			dispatcher.forward(request, response);
		} else {

			List<Article> panier = new ArrayList<>();
			Enumeration<String> atts = request.getSession().getAttributeNames();
			while (atts.hasMoreElements()) {
				String attribute = (String) atts.nextElement();
				if (attribute.startsWith("article")) {
					Article ar = (Article) request.getSession().getAttribute(attribute);
					panier.add(ar);
				}
			}

			request.setAttribute("panier", panier);
			request.getRequestDispatcher("panier.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
