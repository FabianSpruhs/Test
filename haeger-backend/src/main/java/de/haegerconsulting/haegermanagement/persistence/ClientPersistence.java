package de.haegerconsulting.haegermanagement.persistence;


import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientPersistence extends JpaRepository<ClientJPA, Integer> {
}
