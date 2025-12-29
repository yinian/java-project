package org.example.lamda;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String name;        // 商品名称
    private double price;       // 价格
    private boolean outofStock; // 是否缺货

}
