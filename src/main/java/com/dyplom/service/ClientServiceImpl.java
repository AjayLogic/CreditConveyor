package com.dyplom.service;

import com.dyplom.entity.Client;
import com.dyplom.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client findClientByPassportId(String passportId) {
        return clientRepository.findClientByPassportId(passportId);
    }
}
