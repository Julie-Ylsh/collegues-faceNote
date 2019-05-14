package dev.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.entites.Collegue;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.CollegueRepository;

@Service
public class CollegueService {
	@Autowired
	CollegueRepository cRepo;

	public List<Collegue> list() {
		return cRepo.findAll()
				.stream().map(collegue -> new Collegue(collegue.getMatricule(), collegue.getNbVotes(), collegue.getNom(),
						collegue.getPrenoms(), collegue.getPhotoUrl(), collegue.getRoles()))
				.sorted(Comparator.comparing(Collegue::getNbVotes))
				.collect(Collectors.toList());
	}

	public Collegue[] tableauCollegueDistant() {
		RestTemplate rt = new RestTemplate();

		Collegue[] result = rt.getForObject("https://julie-collegue-api.herokuapp.com/collegue/photo",
				Collegue[].class);
		return result;
	}

	public Collegue trouverCollegueDistant(String matricule) throws CollegueNonTrouveException {
		RestTemplate rt = new RestTemplate();
		String url = "https://julie-collegue-api.herokuapp.com/collegue/found?=";
		url += matricule;
		System.out.println(url);
		Collegue result = rt.getForObject(url, Collegue.class);
		return result;
	}

	public void save(Collegue collegueAAjouter) throws CollegueInvalideException {
		if (collegueAAjouter.getNom().length() < 2)
			throw new CollegueInvalideException();

		else if (collegueAAjouter.getPrenoms().length() < 2)
			throw new CollegueInvalideException();

		else if (!collegueAAjouter.getPhotoUrl().startsWith("http"))
			throw new CollegueInvalideException();

		else {
			cRepo.save(collegueAAjouter);
		}
	}

	public List<Collegue> rechercherParNom(String nomRecherche) {
		// retourner une liste de collègues dont le nom est fourni
		return cRepo.findAll().stream().filter(p -> p.getNom().equals(nomRecherche)).collect(Collectors.toList());
	}

	public Collegue rechercherParMatricule(Integer matriculeRecherche) throws CollegueNonTrouveException {
		return cRepo.findById(matriculeRecherche).orElseThrow(CollegueNonTrouveException::new);

	}

	public Collegue ajouterUnCollegue(Collegue collegueAAjouter) throws CollegueInvalideException {
		Collegue nouveauCollegue = null;

		// Vérifier que le nom et les prenoms ont chacun au moins 2
		// caractères
		// Vérifier que la photoUrl commence bien par `http`
		// Si une des règles ci-dessus n'est pas valide, générer une
		// exception :
		// `CollegueInvalideException`.
		if (collegueAAjouter.getNom().length() < 2)
			throw new CollegueInvalideException();

		else if (collegueAAjouter.getPrenoms().length() < 2)
			throw new CollegueInvalideException();

		else if (!collegueAAjouter.getPhotoUrl().startsWith("http"))
			throw new CollegueInvalideException();

		else {

			// Création de ce nouveau collègue à ajouter avec son matricule
			// (role par défaut user)
			collegueAAjouter.setRoles(Arrays.asList("ROLE_USER"));
			nouveauCollegue = new Collegue(collegueAAjouter.getMatricule(), collegueAAjouter.getMotDePasse(),
					collegueAAjouter.getNom(), collegueAAjouter.getPrenoms(), collegueAAjouter.getPhotoUrl(),
					collegueAAjouter.getRoles());

			// Sauvegarder le collègue
			cRepo.save(nouveauCollegue);
		}

		return nouveauCollegue;
	}

	public void ajoutVote(Integer matricule) throws CollegueNonTrouveException {
		Collegue collegueVote = rechercherParMatricule(matricule);

		// retourner une exception `CollegueNonTrouveException`
		// si le matricule ne correspond à aucun collègue
		if (collegueVote == null) {
			throw new CollegueNonTrouveException();
		}
		
		collegueVote.setNbVotes(collegueVote.getNbVotes()+2);
		cRepo.save(collegueVote);
	}
	
	public void moinsVote(Integer matricule) throws CollegueNonTrouveException {
		Collegue collegueVote = rechercherParMatricule(matricule);

		// retourner une exception `CollegueNonTrouveException`
		// si le matricule ne correspond à aucun collègue
		if (collegueVote == null) {
			throw new CollegueNonTrouveException();
		}
		
		collegueVote.setNbVotes(collegueVote.getNbVotes()-1);
		cRepo.save(collegueVote);
	}

	public Collegue modifierPhotoUrl(Integer matricule, String photoUrl)
			throws CollegueNonTrouveException, CollegueInvalideException {
		Collegue collegueModif = rechercherParMatricule(matricule);

		// retourner une exception `CollegueNonTrouveException`
		// si le matricule ne correspond à aucun collègue
		if (collegueModif == null) {
			throw new CollegueNonTrouveException();
		}

		// Vérifier que l'URL conmmence bien par HTTP
		// Si la règle ci-dessus n'est pas valide, générer une exception :
		// `CollegueInvalideException`. avec un message approprié.
		else if (!photoUrl.startsWith("http"))
			throw new CollegueInvalideException();

		// Modifier le collègue
		collegueModif.setPhotoUrl(photoUrl);
		cRepo.save(collegueModif);
		return collegueModif;
	}

	public CollegueRepository getpRepo() {
		return cRepo;
	}

	public void setpRepo(CollegueRepository pRepo) {
		this.cRepo = pRepo;
	}

}
