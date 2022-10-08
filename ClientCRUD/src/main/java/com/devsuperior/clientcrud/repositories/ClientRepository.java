package com.devsuperior.clientcrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.clientcrud.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
