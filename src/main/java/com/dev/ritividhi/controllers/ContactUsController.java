package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.ContactUs;
import com.dev.ritividhi.services.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ritividhi/contactUs")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @GetMapping
    public ResponseEntity<List<ContactUs>> getAllContactUsDetails() {
        List<ContactUs> contacts = contactUsService.getAllContactUsDetails();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactUs> getContactUsDetailsById(@PathVariable String id) {
        return contactUsService.getContactUsDetailsById(id)
                .map(contact -> ResponseEntity.ok(contact))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContactUs> createContactUsDetail(@RequestBody ContactUs contactUs) {
        ContactUs createdContact = contactUsService.createContactUsDetail(contactUs);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactUsDetail(@PathVariable String id) {
        contactUsService.deleteContactUsDetail(id);
        return ResponseEntity.noContent().build();
    }
}
