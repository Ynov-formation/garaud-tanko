package com.ynov.msclient.web;

import com.ynov.msaccount.entities.Account;
import com.ynov.msclient.entities.Client;
import com.ynov.msclient.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/v1")
public class ClientRestRessource {

	private final ClientService clientService;

	public ClientRestRessource(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping("/clients")
	public List<Client> clientList() {
		return clientService.findAll();
	}

	@GetMapping("/clients/{id}")
	public ResponseEntity<Client> getClient(@PathVariable(name = "id") Long id) {
		Client client = clientService.findById(id);

		return client != null ? ResponseEntity.ok(client) : ResponseEntity.notFound().build();
	}

	@PostMapping("/client")
	public ResponseEntity<Client> saveClient(@RequestBody Client client) {
		Client clientSaved = clientService.save(client);

		return clientSaved != null ? ResponseEntity.ok(clientSaved) : ResponseEntity.badRequest().build();
	}

	@PostMapping("/clients")
	public ResponseEntity<List<Client>> saveAllClients(@RequestBody List<Client> clients) {
		List<Client> clientList = clientService.saveAll(clients);

		return clientList != null ? ResponseEntity.ok(clientList) : ResponseEntity.badRequest().build();
	}

	@PutMapping("/clients/{id}")
	public ResponseEntity<Client> updateClient(@PathVariable(name = "id") Long id, @RequestBody Client client) {
		Client clientUpdate = clientService.update(id, client);

		return clientUpdate != null ? ResponseEntity.ok(clientUpdate) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/clients")
	public ResponseEntity<String> deleteAllClients() {
		return ResponseEntity.ok(clientService.deleteAll());
	}

	@DeleteMapping("/clients/{id}")
	public ResponseEntity<String> deleteClient(@PathVariable(name = "id") Long id) {
		String response = clientService.deleteById(id);

		return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest()
				.body("Error while deleting client with id " + id);
	}

	@GetMapping("/clients/{id}/accounts")
	public ResponseEntity<List<Account>> getAccounts(@PathVariable(name = "id") Long id) {
		List<Account> accounts = clientService.getAccounts(id);

		return accounts != null ? ResponseEntity.ok(accounts) : ResponseEntity.notFound().build();
	}
}
