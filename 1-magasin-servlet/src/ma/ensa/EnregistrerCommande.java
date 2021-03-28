package ma.ensa;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EnregistrerCommande
 */
public class EnregistrerCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connexion = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EnregistrerCommande() {
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
		// Ouverture de la connection a la DB
		OuvreBase();
		// Ajout de l'utilisateur a la DB
		AjouteNomBase(nom);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title> votre commande </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour " + nom + " voici ta nouvelle commande" + "</h3>");
		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		// Affichage des disques dans la session
		while (names.hasMoreElements()) {
			String attribute = names.nextElement();
			String nomDisque = (String) session.getAttribute(attribute);
			out.println(nomDisque + "<br>");
		}
		// Sauvegarde de la commande dans la DB
		AjouteCommandeBase(nom, session);
		out.println("<h3>" + "et voici " + nom + " ta commande complete" + "</h3>");
		// Affichage de tous les articles commandes par l utilisateur
		MontreCommandeBase(nom, out);
		out.println("<a href=vider> Vous pouvez commandez un autre disque </a><br> ");
		out.println("</body>");
		out.println("</html>");
		// Fermeture de la connection a la DB
		FermeBase();
	}

	protected void OuvreBase() {
		try {
			// Recuperation du pilote et informations de la DB du contexte
			ServletContext context = getServletContext();
			String driver = context.getInitParameter("Driver");
			Class.forName(driver);
			String DBURL = context.getInitParameter("DBURL");
			String DBUser = context.getInitParameter("DBUser");
			String DBPW = context.getInitParameter("DBPW");
			connexion = DriverManager.getConnection(DBURL, DBUser, DBPW);
			connexion.setAutoCommit(true);
			stmt = connexion.createStatement();
		} catch (Exception E) {
			log(" -------- prolbeme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void FermeBase() {
		try {
			stmt.close();
			connexion.close();
		} catch (Exception E) {
			log(" -------- probleme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void AjouteNomBase(String nom) {
		try {
			pstmt = connexion.prepareStatement("select numero from personnel where nom=?");
			pstmt.setString(1, nom);
			ResultSet resultSet = pstmt.executeQuery();
			// Si l utilisateur n existe pas on le cree
			if (!resultSet.next()) {
				String query = "INSERT INTO personnel (nom) VALUES (?)";
				PreparedStatement preparedStatement = connexion.prepareStatement(query);
				preparedStatement.setString(1, nom);
				preparedStatement.executeUpdate();
			}
		} catch (Exception E) {
			log(" - probleme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void AjouteCommandeBase(String nom, HttpSession session) {
		// ajoute le contenu du panier dans la base
		try {
			pstmt = connexion.prepareStatement("select numero from personnel where nom=?");
			pstmt.setString(1, nom);
			ResultSet resultSet = pstmt.executeQuery();
			int numeroUser = 0;
			if (resultSet.next()) {
				numeroUser = resultSet.getInt("numero");
			}

			Enumeration<String> names = session.getAttributeNames();
			while (names.hasMoreElements()) {
				String disque = names.nextElement();
				String nomDisque = (String) session.getAttribute(disque);
				String query = "INSERT INTO commande (article, qui) VALUES (?, ?)";
				PreparedStatement preparedStatement = connexion.prepareStatement(query);

				preparedStatement.setString(1, nomDisque);
				preparedStatement.setInt(2, numeroUser);
				preparedStatement.executeUpdate();
			}

		} catch (Exception E) {
			log(" - probleme " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void MontreCommandeBase(String nom, PrintWriter out) {
		// affiche les produits presents dans la base
		try {
			// Recherche et affichage des articles commandes par l utilisateur
			pstmt = connexion.prepareStatement(
					"SELECT c.article FROM commande c inner join personnel p on p.numero = c.qui WHERE p.nom = ?");
			pstmt.setString(1, nom);
			ResultSet resultSet = pstmt.executeQuery();
			out.println("Utilisateur " + nom + " a commande : ");
			out.println("<ul>");
			while (resultSet.next()) {
				out.println("<li>" + resultSet.getString("article") + "</li>");
			}
			out.println("</ul>");
		} catch (Exception e) {
			e.printStackTrace();
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
