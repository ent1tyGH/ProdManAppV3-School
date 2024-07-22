package com.gabriel.prodmsv.model;

import lombok.Data;

@Data
public class Contact {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
