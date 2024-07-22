package com.gabriel.prodmsv.service;

import com.gabriel.prodmsv.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RemoteContactServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(RemoteContactServiceImpl.class);

    @Value("${service.api.endpoint}")
    private String endpointUrl;

    private final RestTemplate restTemplate;

    public ContactServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
    }

    public Contact getContact(int id) {
        String url = endpointUrl + "/" + id;
        logger.info("getContact: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        ResponseEntity<Contact> response = restTemplate.exchange(url, HttpMethod.GET, request, Contact.class);
        return response.getBody();
    }

    public Contact[] getContacts() {
        String url = endpointUrl;
        logger.info("getContacts: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        ResponseEntity<Contact[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Contact[].class);
        return response.getBody();
    }

    public Contact create(Contact contact) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Contact> request = new HttpEntity<>(contact, headers);
        ResponseEntity<Contact> response = restTemplate.exchange(url, HttpMethod.POST, request, Contact.class);
        return response.getBody();
    }

    public Contact update(Contact contact) {
        logger.info("update: " + contact);
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Contact> request = new HttpEntity<>(contact, headers);
        ResponseEntity<Contact> response = restTemplate.exchange(url, HttpMethod.PUT, request, Contact.class);
        return response.getBody();
    }

    public void delete(int id) {
        logger.info("delete: " + id);
        String url = endpointUrl + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }
}
