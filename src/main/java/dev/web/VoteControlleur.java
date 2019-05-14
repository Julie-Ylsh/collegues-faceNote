package dev.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Vote;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController

@RequestMapping("/vote")
@CrossOrigin
public class VoteControlleur {

	@Autowired
	private CollegueService collegueService;

	@PostMapping
	@ResponseBody
	public ResponseEntity<String> vote(@RequestBody Vote vote) {

		try {
			collegueService.ajoutVote(vote);
			return ResponseEntity.status(200).body("Vote ajouté");
		} catch (CollegueNonTrouveException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collègue non trouvé");

		}

	}
	


}
