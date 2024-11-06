package com.dev.ritividhi.services;

import com.dev.ritividhi.models.ContactUs;
import com.dev.ritividhi.repository.ContactUsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    public List<ContactUs> getAllContactUsDetails() {
        return contactUsRepository.findAll();
    }

    public Optional<ContactUs> getContactUsDetailsById(String id) {
        return contactUsRepository.findById(id);
    }

    public ContactUs createContactUsDetail(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }

//    public ContactUs updateContact(String id, ContactUs contactUs) {
//       // contactUs.setId(id); // Assuming you have an ID field
//        return contactUsRepository.save(contactUs);
//    }

    public void deleteContactUsDetail(String id) {
        contactUsRepository.deleteById(id);
    }
}
