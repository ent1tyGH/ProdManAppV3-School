package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.ContactData;
import com.gabriel.prodmsapp.model.Contact;
import com.gabriel.prodmsapp.repository.ContactDataRepository;
import com.gabriel.prodmsapp.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocalContactServiceImpl implements ContactService {
    Logger logger = LoggerFactory.getLogger(LocalContactServiceImpl.class);

    @Autowired
    ContactDataRepository productDataRepository;

    @Override
    public Contact[] getProducts() throws Exception {
        List<ContactData> productsData = new ArrayList<>();
        List<Contact> products = new ArrayList<>();
        productDataRepository.findAll().forEach(productsData::add);
        Iterator<ContactData> it = productsData.iterator();

        while (it.hasNext()) {
            Contact product = new Contact();
            ContactData productData = it.next();
            product.setId(productData.getId());
            product.setName(productData.getName());
            product.setDescription(productData.getDescription());
            products.add(product);
        }

        Contact[] array = new Contact[products.size()];
        for (int i = 0; i < products.size(); i++) {
            array[i] = products.get(i);
        }
        return array;
    }

    @Override
    public Contact create(Contact product) throws Exception {
        logger.info("add: Input " + product.toString());
        ContactData productData = new ContactData();
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());

        productData = productDataRepository.save(productData);
        logger.info("add: Input " + productData.toString());

        Contact newProduct = new Contact();
        newProduct.setId(productData.getId());
        newProduct.setName(productData.getName());
        newProduct.setDescription(productData.getDescription());
        return newProduct;
    }

    @Override
    public Contact update(Contact product) throws Exception {
        ContactData productData = new ContactData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());

        productData = productDataRepository.save(productData);

        Contact newProduct = new Contact();
        newProduct.setId(productData.getId());
        newProduct.setName(productData.getName());
        newProduct.setDescription(productData.getDescription());

        return newProduct;
    }

    @Override
    public Contact getProduct(Integer id) throws Exception {
        logger.info("Input id >> " + id);
        Optional<ContactData> optional = productDataRepository.findById(id);
        if (optional.isPresent()) {
            logger.info("Is present >> ");
            Contact product = new Contact();
            ContactData productDatum = optional.get();
            product.setId(productDatum.getId());
            product.setName(productDatum.getName());
            product.setDescription(productDatum.getDescription());
            return product;
        }
        logger.info("Failed  >> unable to locate product");
        return null;
    }

    @Override
    public void delete(Integer id) throws Exception {
        logger.info("Input >> " + id);
        Optional<ContactData> optional = productDataRepository.findById(id);
        if (optional.isPresent()) {
            ContactData productDatum = optional.get();
            productDataRepository.delete(productDatum);
            logger.info("Successfully deleted >> " + productDatum.toString());
        } else {
            logger.info("Failed  >> unable to locate product id: " + id);
        }
    }
}
