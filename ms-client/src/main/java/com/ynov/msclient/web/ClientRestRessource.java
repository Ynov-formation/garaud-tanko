package com.ynov.msclient.web;

import com.ynov.msclient.dao.ClientRepository;
import com.ynov.msclient.entities.Client;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/v1")
public class ClientRestRessource {

	private final ClientRepository clientRepository;

	public ClientRestRessource(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@GetMapping("/clients")
	public List<Client> clientList() {
		return clientRepository.findAll();
	}

	@GetMapping("/clients/{id}")
	public Client getClient(@PathVariable(name = "id") Long id) {
		return clientRepository.findById(id).orElse(null);
	}

	@PostMapping("/clients")
	public List<Client> saveAllClients(@RequestBody List<Client> clients) {
		return clientRepository.saveAll(clients);
	}

	@PutMapping("/clients/{id}")
	public Client updateClient(@PathVariable(name = "id") Long id, @RequestBody Client client) {
		client.setId(id);
		return clientRepository.save(client);
	}

	@DeleteMapping("/clients")
	public void deleteAllClients() {
		clientRepository.deleteAll();
	}

	@DeleteMapping("/clients/{id}")
	public void deleteClient(@PathVariable(name = "id") Long id) {
		clientRepository.deleteById(id);
	}
}
