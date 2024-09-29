package sn.ucad.mspaiement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiCollection<T> {
    private int status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private T data;

    private long totalItems;

    private long totalPages;

    public ApiCollection(T data, long totalItems, long totalPages) {
        this.status = 200;
        this.timestamp = LocalDateTime.now();
        this.message = "Success";
        this.data = data;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}