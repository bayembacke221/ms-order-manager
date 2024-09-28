package sn.ucad.mscustomerorder.service;

import org.springframework.stereotype.Service;
import sn.ucad.mscustomerorder.models.Client;
import sn.ucad.mscustomerorder.repository.ClientRepository;

@Service
public class ClientService extends GenericService<Client, Long> {

    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
    }

}
