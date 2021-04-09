package ma.ensa.Utils;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;

import ma.ensa.Models.Client;

public class CookiesHelper {

	public CookiesHelper() {
		// TODO Auto-generated constructor stub
	}


	public static Client chercheClient(Cookie[] cookies) {
		String email = getCookie("email", cookies);
		String mdp = getCookie("motdepasse", cookies);
		return Client.getClient(email, mdp);
	}
	
	public static String getCookie(String name, Cookie[] cookies) {
		return Optional.ofNullable(cookies).flatMap(cookiesF -> Arrays.stream(cookiesF)
				.filter(cookie -> name.equals(cookie.getName())).findAny())
				// If we have a matching cookie, return its value.
				.map(e -> e.getValue())
				// otherwise return null to retain original behaviour
				.orElse(null);
	}

}
