package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class Order {

    private String name ;
    private Optional<String> discount;
}
