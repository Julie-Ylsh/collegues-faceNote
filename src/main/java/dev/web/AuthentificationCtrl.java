package dev.web;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.entites.Collegue;
import dev.entites.InfosAuthentification;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController

public class AuthentificationCtrl {
	@Value("${jwt.expires_in}")
	private Integer EXPIRES_IN;

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Value("${jwt.secret}")
	private String SECRET;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CollegueService collegueService;

	@PostMapping(value = "/login")
	public ResponseEntity authenticate(@RequestBody InfosAuthentification collegueInscription,
			HttpServletResponse response) throws URISyntaxException {

		System.out.println("hello");
		RestTemplate rt = new RestTemplate();

		ResponseEntity<?> result = rt.postForEntity("https://julie-collegue-api.herokuapp.com/auth",
				collegueInscription, Collegue.class);
		String jetonJWT = result.getHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];
		Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);

		// DEFINIT LE COOKIE ET PERMET DE LE TRANSMETTRE
		authCookie.setHttpOnly(true);
		authCookie.setMaxAge(EXPIRES_IN * 1000);
		authCookie.setPath("/");
		response.addCookie(authCookie);

		RequestEntity<?> requestEntity = RequestEntity.get(new URI("https://julie-collegue-api.herokuapp.com/me"))
				.header("Cookie", result.getHeaders().getFirst("Set-Cookie")).build();

		ResponseEntity<Collegue> rep2 = rt.exchange(requestEntity, Collegue.class);
		Collegue collegueConnecte = rep2.getBody();

		try {
			collegueService.rechercherParMatricule(collegueConnecte.getMatricule());
			System.out.println("Collegue existant dans la base de donnée");
		} catch (CollegueNonTrouveException e1) {
			try {
				System.out.println("Collègue non trouvé, ajout dans la base de donnée");
				try {
					collegueConnecte.setPhotoUrl(collegueInscription.getUrlPhoto());
				} catch (NullPointerException e2) {
					System.out.println("pas d'url");
				}

				collegueService.ajouterUnCollegue(collegueConnecte);
			} catch (CollegueInvalideException e) {
				System.out.println("Vous n'avez pas entré les bons paramètres");
			}
		}

		return ResponseEntity.ok(collegueConnecte);

	}

	@GetMapping(path = "/me")
	@ResponseBody
	public ResponseEntity afficherCollegueCookie(HttpServletRequest req) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Collegue collegueTrouve;

		try {
			collegueTrouve = collegueService.rechercherParMatricule(Integer.parseInt(username));
			return ResponseEntity.status(200).body(collegueTrouve);
		} catch (NumberFormatException | CollegueNonTrouveException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collègue non trouvé");
		}

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity mauvaiseInfosConnexion(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
