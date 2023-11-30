package com.ynov.msclient.dao;

import com.ynov.msclient.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
