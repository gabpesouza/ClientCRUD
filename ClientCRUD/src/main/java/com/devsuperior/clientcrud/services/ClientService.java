package com.devsuperior.clientcrud.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.clientcrud.dto.ClientDTO;
import com.devsuperior.clientcrud.entities.Client;
import com.devsuperior.clientcrud.repositories.ClientRepository;
import com.devsuperior.clientcrud.services.exceptions.DataBaseException;
import com.devsuperior.clientcrud.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest){
		
		Page<Client> client = repository.findAll(pageRequest);
		
		Page<ClientDTO> clientDTO  = client.map(x -> new ClientDTO(x));
		
		return clientDTO;
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById (Long id) {
		
	
		Optional<Client> optional = repository.findById(id);
		
		Client client = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new ClientDTO(client);
		
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client client = new Client();
		copyDtoToEntity(dto, client);
		repository.save(client);
		
		return new ClientDTO(client);
		
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		
	try {
		Client client = repository.getOne(id);
		copyDtoToEntity(dto, client);
		
		return new ClientDTO(client);
		}
	catch(EntityNotFoundException e) {
		throw new ResourceNotFoundException("Entity not found");
		}
		
	}
	
	@Transactional
	public void delete(Long id) {
		
	try {
		repository.deleteById(id);
		}
	catch(DataIntegrityViolationException e) {
		throw new DataBaseException("Database integrity violation");
		}
	catch(EmptyResultDataAccessException e) {
		throw new ResourceNotFoundException("Entity not found");
		}
	}
	
	
	public void copyDtoToEntity(ClientDTO dto, Client client) {
		client.setName(dto.getName());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
	}
	
	
}
