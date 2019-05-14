package dev.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController

@RequestMapping("/vote")
@CrossOrigin
public class VoteControlleur {

	@Autowired
	private CollegueService collegueService;

	@GetMapping(path = "/yes/{matricule}")
	@ResponseBody
	public ResponseEntity<String> votePositif(@PathVariable Integer matricule) {

		try {
			collegueService.ajoutVote(matricule);
			return ResponseEntity.status(200).body("Vote positif ajouté");
		} catch (CollegueNonTrouveException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collègue non trouvé");

		}

	}
	
	@GetMapping(path = "/no/{matricule}")
	@ResponseBody
	public ResponseEntity<String> voteNegatif(@PathVariable Integer matricule) {

		try {
			collegueService.moinsVote(matricule);
			return ResponseEntity.status(200).body("Vote négatif ajouté");
		} catch (CollegueNonTrouveException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collègue non trouvé");

		}

	}

}
