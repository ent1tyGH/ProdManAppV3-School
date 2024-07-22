package com.gabriel.prodmsv.service;

import com.gabriel.prodmsv.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ContactServiceImpl {

    Logger logger = LoggerFactory.getLogger(com.gabriel.prodmsv.service.ContactServiceImpl.class);
    private static com.gabriel.prodmsv.service.ContactServiceImpl service = null;
    @Value("${service.api.endpoint}")
    private String endpointUrl = "http://localhost:8080/api/contacts";
    private RestTemplate restTemplate = null;

    public static com.gabriel.prodmsv.service.ContactServiceImpl getService() {
        if (service == null) {
            service = new com.gabriel.prodmsv.service.ContactServiceImpl();
        }
        return service;
    }

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
        }
        return restTemplate;
    }

    public Contact getContact(Long id) {
        String url = endpointUrl + "/" + id;
        logger.info("getContact: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        ResponseEntity<Contact> response = getRestTemplate().exchange(url, HttpMethod.GET, request, Contact.class);
        return response.getBody();
    }

    public Contact[] getContacts() {
        String url = endpointUrl;
        logger.info("getContacts: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        ResponseEntity<Contact[]> response = getRestTemplate().exchange(url, HttpMethod.GET, request, Contact[].class);
        return response.getBody();
    }

    public Contact create(Contact contact) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Contact> request = new HttpEntity<>(contact, headers);
        ResponseEntity<Contact> response = getRestTemplate().exchange(url, HttpMethod.PUT, request, Contact.class);
        return response.getBody();
    }

    public Contact update(Contact contact) {
        logger.info("update: " + contact.toString());
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Contact> request = new HttpEntity<>(contact, headers);
        ResponseEntity<Contact> response = getRestTemplate().exchange(url, HttpMethod.POST, request, Contact.class);
        return response.getBody();
    }

    public void delete(Long id) {
        logger.info("delete: " + id);
        String url = endpointUrl + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        getRestTemplate().exchange(url, HttpMethod.DELETE, request, Void.class);
    }
}
