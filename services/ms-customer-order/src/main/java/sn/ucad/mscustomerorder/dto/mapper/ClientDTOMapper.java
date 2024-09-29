package sn.ucad.mscustomerorder.dto.mapper;

import sn.ucad.mscustomerorder.dto.ClientDTO;
import sn.ucad.mscustomerorder.models.Client;

public class ClientDTOMapper {

    public static ClientDTO convertToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        return dto;
    }

    public static Client convertToEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        return client;
    }
}
