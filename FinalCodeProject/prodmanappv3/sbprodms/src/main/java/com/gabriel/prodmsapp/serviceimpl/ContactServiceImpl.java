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
public class ContactServiceImpl implements ContactService {
    Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    ContactDataRepository productDataRepository;

    @Override
    public Contact[] getProducts() {
        List<ContactData> productsData = new ArrayList<>();
        List<Contact> products = new ArrayList<>();
        productDataRepository.findAll().forEach(productsData::add);
        Iterator<ContactData> it = productsData.iterator();

        while(it.hasNext()) {
            Contact product = new Contact();
            ContactData productData = it.next();
            product.setId(productData.getId());
            product.setName(productData.getName());
            product.setDescription(productData.getDescription());
            product.setUomId(productData.getUomId());
            product.setUomName(productData.getUomName());
            products.add(product);
        }

        Contact[] array = new Contact[products.size()];
        for  (int i=0; i<products.size(); i++){
            array[i] = products.get(i);
        }
//        Product[] array = (Product[])products.toArray();
        return array;
    }

    @Override
    public Contact create(Contact product) {
        logger.info("add: Input"+ product.toString());
        ContactData productData = new ContactData();
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setUomId(product.getUomId());
        productData.setUomName(product.getUomName());

        productData = productDataRepository.save(productData);
        logger.info("add: Input"+ productData.toString());

        Contact newProduct = new Contact();
        newProduct.setId(productData.getId());
        newProduct.setName(productData.getName());
        newProduct.setDescription(productData.getDescription());
        newProduct.setUomId(productData.getUomId());
        newProduct.setUomName(productData.getUomName());
        return newProduct;
    }

    @Override
    public Contact update(Contact product) {
        ContactData productData = new ContactData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setUomId(product.getUomId());
        productData.setUomName(product.getUomName());

        productData = productDataRepository.save(productData);

        Contact newProduct = new Contact();
        newProduct.setId(productData.getId());
        newProduct.setName(productData.getName());
        newProduct.setDescription(productData.getDescription());
        newProduct.setUomId(productData.getUomId());
        newProduct.setUomName(productData.getUomName());
        return newProduct;
    }

    @Override
    public Contact getProduct(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<ContactData> optional = productDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Contact product = new Contact();
            ContactData productDatum = optional.get();
            product.setId(productDatum.getId());
            product.setName(productDatum.getName());
            product.setDescription(productDatum.getDescription());
            product.setUomId(productDatum.getUomId());
            product.setUomName(productDatum.getUomName());
            return product;
        }
        logger.info("Failed  >> unable to locate product" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Contact product = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<ContactData> optional = productDataRepository.findById(id);
         if( optional.isPresent()) {
             ContactData productDatum = optional.get();
             productDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + productDatum.toString());
             product = new Contact();
             product.setId(optional.get().getId());
             product.setName(optional.get().getName());
             product.setDescription(optional.get().getDescription());
             product.setUomId(optional.get().getUomId());
             product.setUomName(optional.get().getUomName());
         }
         else {
             logger.info("Failed  >> unable to locate product id: " +  Integer.toString(id));
         }
    }
}
