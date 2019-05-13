package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Collegue;

public interface CollegueRepository extends JpaRepository<Collegue, Integer> {
	public List<Collegue> findByNom(String nom); 
	
}