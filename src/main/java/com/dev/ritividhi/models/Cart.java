package com.dev.ritividhi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;



@Data
@Document(collection = "cart")

public class Cart {

    @Id
    private String cartId;

    @DBRef
    private User user;

    private List<CartItem> cartItems;

    private Date createdAt;
    

}
