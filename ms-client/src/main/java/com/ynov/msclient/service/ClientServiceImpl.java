package com.ynov.msclient.service;

import com.ynov.msaccount.entities.Account;
import com.ynov.msclient.dao.ClientRepository;
import com.ynov.msclient.entities.Client;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	public static final String ACCOUNTS = "/accounts";
	private static final String ACCOUNT_SERVICE_URL = "http://localhost:8082/account/v1";

	private final ClientRepository clientRepository;

	private final RestTemplate restTemplate;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
		this.restTemplate = new RestTemplate();
	}

	@Override
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	@Override
	public Client findById(Long id) {
		return clientRepository.findById(id).orElse(null);
	}

	@Override
	public List<Client> saveAll(List<Client> clients) {
		return clientRepository.saveAll(clients);
	}

	@Override
	public Client save(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Client update(Long id, Client client) {
		Optional<Client> clientFound = clientRepository.findById(id);

		if (clientFound.isPresent()) {
			client.setId(id);
			return clientRepository.save(client);
		} else {
			return null;
		}
	}

	@Override
	public String deleteAll() {
		restTemplate.delete(ACCOUNT_SERVICE_URL + ACCOUNTS);
		clientRepository.deleteAll();
		return "All clients and their accounts have been deleted";
	}

	@Override
	public String deleteById(Long id) {
		Optional<Client> clientFound = clientRepository.findById(id);

		if (clientFound.isPresent()) {
			getAccounts(id).stream()
					.flatMap(account -> Stream.of(account.getId()))
					.forEach(accountId -> restTemplate.delete(ACCOUNT_SERVICE_URL + ACCOUNTS + "/" + accountId));

			clientRepository.deleteById(id);
			return "Client with id " + id + " and their account(s) have been deleted";
		} else {
			return null;
		}
	}

	@Override
	public List<Account> getAccounts(Long id) {
		Optional<Client> clientFound = clientRepository.findById(id);

		if (clientFound.isPresent()) {
			ResponseEntity<List<Account>> response = restTemplate.exchange(
					ACCOUNT_SERVICE_URL + ACCOUNTS,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<>() {
					}
			);

			List<Account> accounts = response.getBody();
			return accounts != null ? filterAccountsByClientId(accounts, id) : null;
		} else {
			return null;
		}
	}

	private List<Account> filterAccountsByClientId(List<Account> accounts, Long id) {
		return accounts.stream()
				.filter(account -> account.getClientId().equals(id))
				.toList();
	}

}
