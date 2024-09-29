package sn.ucad.mscustomerorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ucad.mscustomerorder.dto.ApiCollection;
import sn.ucad.mscustomerorder.dto.ClientDTO;
import sn.ucad.mscustomerorder.dto.mapper.ClientDTOMapper;
import sn.ucad.mscustomerorder.helper.ResponseHandler;
import sn.ucad.mscustomerorder.models.Client;
import sn.ucad.mscustomerorder.service.ClientService;

import java.util.List;
import java.util.Map;

@Tag(name = "clients", description = "The clients API")
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Get all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all clients",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Clients not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<ApiCollection<List<ClientDTO>>> getAllClients(@RequestParam(defaultValue = "true") boolean pageable,
                                                                        @RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result  = clientService.findAll(pageable, page, size);

        if (result.containsKey("totalItems")) {
            return (ResponseEntity<ApiCollection<List<ClientDTO>>>) ResponseHandler.generateResponse("Liste recuperée", HttpStatus.OK, result.get("data"), (long) result.get("totalItems"), (int) result.get("totalPages"));
        } else
            return (ResponseEntity<ApiCollection<List<ClientDTO>>>) ResponseHandler.generateResponse("Liste recuperée", HttpStatus.OK, result.get("data"));
    }

    @Operation(summary = "Get a client by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the client",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        ClientDTO clientDTO = ClientDTOMapper.convertToDTO(client);
        return ResponseEntity.ok(clientDTO);
    }

    @Operation(summary = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        Client client = ClientDTOMapper.convertToEntity(clientDTO);
        Client newClient = clientService.save(client);
        ClientDTO newClientDTO = ClientDTOMapper.convertToDTO(newClient);
        return new ResponseEntity<>(newClientDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
        Client client = ClientDTOMapper.convertToEntity(clientDTO);
        Client updatedClient = clientService.update(id, client);
        ClientDTO updatedClientDTO = ClientDTOMapper.convertToDTO(updatedClient);
        return ResponseEntity.ok(updatedClientDTO);
    }

    @Operation(summary = "Delete a client by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
