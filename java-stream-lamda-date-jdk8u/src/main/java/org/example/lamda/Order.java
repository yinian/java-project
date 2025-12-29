package org.example.lamda;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {

    String orderId;
    double amount;
    boolean isPaid;
    String status;
    List<Product> productList;
}
