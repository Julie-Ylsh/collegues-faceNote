package dev.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
import dev.entites.PhotoUrl;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController


@CrossOrigin
public class CollegueControlleur {

	@Autowired
	private CollegueService collegueService;

	@GetMapping
	@ResponseBody
	public List<Collegue> RetournerListeColleguesParNom(@RequestParam("nomClient") String nomClient) {
		List<Collegue> listeDeNoms = collegueService.rechercherParNom(nomClient);
		return listeDeNoms.stream()
				.map(collegue -> new Collegue(collegue.getMatricule(), collegue.getNbVotes(), collegue.getNom(),
						collegue.getPrenoms(), collegue.getPhotoUrl(), collegue.getRoles()))
				.collect(Collectors.toList());

	}
	
	@GetMapping(path="/all")
	@ResponseBody
	public Collegue[] afficherTousColleguesDistants() {

		return collegueService.tableauCollegueDistant();
	}
	
	@GetMapping(path="/players")
	@ResponseBody
	public List<Collegue> afficherTousColleguesLocal() {

		return collegueService.list();
	}

	@GetMapping(path = "/{matricule}")
	@ResponseBody
	public ResponseEntity<String> retournerCollegueMatricule(@PathVariable Integer matricule) {

		try {
			Collegue collegueTouve = collegueService.rechercherParMatricule(matricule);
			return ResponseEntity.status(200).body(collegueTouve.getNom());
		} catch (CollegueNonTrouveException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collègue non trouvé");

		}

	}

	@Secured("ROLE_ADMIN")
	@PostMapping
	@ResponseBody
	public ResponseEntity<String> creerCollegue(@RequestBody Collegue collegueAAjouter) {

		try {
			collegueService.ajouterUnCollegue(collegueAAjouter);
			return ResponseEntity.status(200)
					.body("Collègue ajouté : " + collegueAAjouter.getNom() + " " + collegueAAjouter.getPrenoms());
		}

		catch (CollegueInvalideException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}

	}
	
	@PatchMapping(path = "/{matricule}")
	public ResponseEntity<String> modifierPhotoParMatricule(@PathVariable Integer matricule, @RequestBody PhotoUrl photo)
			throws CollegueNonTrouveException {
		try {
			Collegue collegueAModifier = collegueService.rechercherParMatricule(matricule);
			collegueService.modifierPhotoUrl(matricule, photo.getPhotoUrl());
			return ResponseEntity.status(200).body("URL de la photo modifié pour " + collegueAModifier.getNom() + " "
					+ collegueAModifier.getPrenoms() + ". Nouvel Url: " + collegueAModifier.getPhotoUrl());
		}

		catch (CollegueInvalideException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}
	}

	
}
