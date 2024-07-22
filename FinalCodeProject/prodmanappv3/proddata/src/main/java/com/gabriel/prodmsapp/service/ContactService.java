package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Contact;

public interface ContactService {
    Contact[] getProducts() throws Exception;

    Contact getProduct(Integer id) throws Exception;

    Contact create(Contact product) throws Exception;

    Contact update(Contact product) throws Exception;

    void delete(Integer id) throws Exception;
}
