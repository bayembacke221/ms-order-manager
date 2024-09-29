package sn.ucad.mscustomerorder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    @JsonIgnoreProperties("client")
    private List<OrderDTO> orders;
}
