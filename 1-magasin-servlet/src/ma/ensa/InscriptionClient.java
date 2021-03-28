package ma.ensa;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InscriptionClient
 */
public class InscriptionClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InscriptionClient() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Valeurs du nom et mot de passe dans les paramaetres de la requete
		String nomRecu = request.getParameter("nom");
		String motPasseRecu = request.getParameter("motdepasse");
		
		// Valeurs du nom et mot de passe dans les coookies
		Cookie[] cookies = request.getCookies();
		String nomCookie = Identification.chercheNom(cookies);
		String motPasseCookie = Identification.chercheMDP(cookies);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (nomCookie == null && nomRecu == null) {
			// Cas 1 : cas ou il n'y a ni de cookies ni de parametres
			// on affiche le formulaire
			printFormulaire(out, nomRecu, motPasseRecu, nomCookie, motPasseCookie);
		} else if (nomCookie == null && nomRecu != null) {
			// Cas 2 : cas ou il n'y a pas de cookies mais les parametres nom et mot de passes sont presents
			// on initialise les cookies en utilisant les parametres
			Cookie nameCK = new Cookie("nom", nomRecu);
			response.addCookie(nameCK);
			Cookie pwCK = new Cookie("motdepasse", motPasseRecu);
			response.addCookie(pwCK);
			// on affiche le formulaire
			printFormulaire(out, nomRecu, motPasseRecu, nomCookie, motPasseCookie);
		} else if (identique(nomRecu, nomCookie) && identique(motPasseRecu, motPasseCookie)) {
			// Cas 4 : cas ou le nom et le mot passe sont correctes
			// appel a la servlet achat
			response.sendRedirect("achat");
		} else {
			// Cas 3 : les cookies sont presents demande de s'identifier
			// on affiche le formulaire
			printFormulaire(out, nomRecu, motPasseRecu, nomCookie, motPasseCookie);
		}
	}

	private void printFormulaire(PrintWriter out, String nomRecu, String motPasseRecu, String nomCookie,
			String motPasseCookie) {
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("    <title>inscription d'un client</title>");
		out.println("    <style>");
		out.println("        td,");
		out.println("        th {");
		out.println("            border: 1px solid #dddddd;");
		out.println("            text-align: left;");
		out.println("            padding: 8px;");
		out.println("        }");
		out.println("    </style>");
		out.println("</head>");
		out.println("<body>");
		out.println("    <table>");
		out.println("        <tr>");
		out.println("            <td>nomRecu</td>");
		out.println("            <td>motPasseRecu</td>");
		out.println("            <td>nomCookie</td>");
		out.println("            <td>motPasseCookie</td>");
		out.println("        </tr>");
		out.println("        <tr>");
		out.println("            <td>"+nomRecu+"</td>");
		out.println("            <td>"+motPasseRecu+"</td>");
		out.println("            <td>"+nomCookie+"</td>");
		out.println("            <td>"+motPasseCookie+"</td>");
		out.println("        </tr>");
		out.println("    </table>");
		out.println("    <h3>Bonjour, vous devez vous inscrire</h3>");
		out.println("    <h3>Attention mettre nom et le mot de passe avec plus de 3 caracteres</h3>");
		out.println("    <form action='inscrire' method='GET'>");
		out.println("        <label for='nom'>Nom</label><br>");
		out.println("        <input type='text' size='20' name='nom' id='nom' minlength='3'><br>");
		out.println("        <label for='mdp'>Mot de passe</label><br>");
		out.println("        <input type='password' size='20' name='motdepasse' id='mdp' minlength='3'><br>");
		out.println("        <input type='submit' value='inscription'>");
		out.println("    </form>");
		out.println("</body>");
		out.println("</html>");
	}

	boolean identique(String recu, String cookie) {
		return ((recu != null) && (recu.length() > 3) && (cookie != null) && (recu.equals(cookie)));
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
