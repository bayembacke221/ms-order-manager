package sn.ucad.msstatistique.models;

import lombok.Data;

import java.util.List;
@Data
public class Client {
    private Long id;
    private String name;
    private String email;
    private List<Order> orders;
}
