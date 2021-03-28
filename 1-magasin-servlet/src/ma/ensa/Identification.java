package ma.ensa;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;

public class Identification {

	private Identification() {
	}

	static String chercheNom(Cookie[] cookies) {
		// cherche dans les cookies la valeur de celui qui se nomme "nom"
		// retourne la valeur de ce nom sinon null
		return Optional.ofNullable(cookies).flatMap(cookiesF -> Arrays.stream(cookiesF)
				.filter(cookie -> "nom".equals(cookie.getName())).findAny())
				// If we have a matching cookie, return its value.
				.map(e -> e.getValue())
				// otherwise return null to retain original behaviour
				.orElse(null);
	}

	static String chercheMDP(Cookie[] cookies) {
		// cherche dans les cookies la valeur de celui qui se nomme "motdepasse"
		// et retourne sa valeur sinon null
		return Optional.ofNullable(cookies).flatMap(cookiesF -> Arrays.stream(cookiesF)
				// Find the cookie if we can.
				.filter(cookie -> "motdepasse".equals(cookie.getName())).findAny())
				// If we have a matching cookie, return its value.
				.map(e -> e.getValue())
				// otherwise return null to retain original behaviour
				.orElse(null);
	}
}
