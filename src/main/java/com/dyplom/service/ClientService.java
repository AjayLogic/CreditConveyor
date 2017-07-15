package com.dyplom.service;


import com.dyplom.entity.Client;

public interface ClientService {
    void save (Client client);
    Client findClientByPassportId(String passportId);
}
