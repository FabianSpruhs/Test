package de.haegerconsulting.haegermanagement.persistence;

import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SickNotePersistence extends JpaRepository<SickNoteJPA, Integer> {
}
