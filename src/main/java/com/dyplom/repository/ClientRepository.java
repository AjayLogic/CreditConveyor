package com.dyplom.repository;

import com.dyplom.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByPassportId(String passportId);
}
