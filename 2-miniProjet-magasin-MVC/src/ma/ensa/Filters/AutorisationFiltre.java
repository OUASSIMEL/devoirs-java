package ma.ensa.Filters;

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

import ma.ensa.Models.Client;
import ma.ensa.Utils.CookiesHelper;

/**
 * Servlet Filter implementation class AutorisationFiltre
 */
public class AutorisationFiltre implements Filter {

    /**
     * Default constructor. 
     */
    public AutorisationFiltre() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		Cookie[] cookies = hrequest.getCookies();
		// Chercher la valeur du cookie nom
		Client client = CookiesHelper.chercheClient(cookies);
		if (client == null) {
			// Appel servlet inscrire
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("identification");
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
