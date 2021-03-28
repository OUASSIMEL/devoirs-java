package ma.ensa;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FiltreAutorisation
 */
public class FiltreAutorisation implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltreAutorisation() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		Cookie[] cookies = hrequest.getCookies();
		// Chercher la valeur du cookie nom
		String nom = Identification.chercheNom(cookies);
		if (nom == null) {
			// Appel servlet inscrire
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("inscrire");
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
