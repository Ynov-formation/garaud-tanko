package com.ynov.msclient.service;

import com.ynov.msaccount.entities.Account;
import com.ynov.msclient.entities.Client;

import java.util.List;

public interface ClientService {

	List<Client> findAll();

	Client findById(Long id);

	List<Client> saveAll(List<Client> clients);

	Client save(Client client);

	Client update(Long id, Client client);

	String deleteAll();

	String deleteById(Long id);

	List<Account> getAccounts(Long id);

}
