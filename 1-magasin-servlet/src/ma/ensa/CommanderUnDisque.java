package ma.ensa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CommanderUnDisque
 */
public class CommanderUnDisque extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommanderUnDisque() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		// Chercher la valeur du cookie nom
		String nom = Identification.chercheNom(cookies);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> votre commande </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour " + nom + " voici votre commande" + "</h3>");
		// Affichage des disques presents dans le panier (elements de la session)
		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		List<String> attributes = Collections.list(names);
		// Nombre de disques dans la session
		int nbDisques = attributes.size();
		// Affichage de la liste des disques
		out.println("<ul>");
		// Recuperer et afficher les disques de la session
		if (nbDisques != 0) {
			for (String attribut : attributes) {
				String nomDisque = (String) session.getAttribute(attribut);
				String[] infosDisque = Stock.chercheNom(nomDisque);
				out.println("<li>Disque" + " Nom=" + nomDisque + " Prix=" + infosDisque[1] + "</li>");
			}
		}
		// Si parametre ordre == ajouter affichage du disque a ajouter au panier
		if (request.getParameter("ordre").equals("ajouter")) {
			String code = request.getParameter("code");
			String[] disque = Stock.chercheCode(code);
			// Affichage du disque a ajouter au panier
			out.println("<li>Disque" + " Nom=" + disque[0] + " Prix=" + disque[1] + "</li>");
			// Incrementer le nombre de disques
			nbDisques++;
			// Ajout du disque a la session
			session.setAttribute("disque" + nbDisques, disque[0]);
		}
		out.println("</ul>");
		out.println("<a href=achat> Vous pouvez commandez un autre disque </a><br> ");
		out.println("<a href=enregistre> Vous pouvez enregistrer votre commande </a><br> ");
		out.println("</body>");
		out.println("</html>");
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
