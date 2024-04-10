package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.ContactsInfo;
import com.example.demo.form.ContactForm;
import com.example.demo.repo.ContactRepository;


import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public String getContactDetail(Long id, Model model) {
        Optional<ContactsInfo> contactOptional = contactRepository.findById(id);
        if (contactOptional.isPresent()) {
            ContactsInfo contact = contactOptional.get();
            model.addAttribute("contact", contact);
            return "detail";
        } else {
            return "error_page";
        }
    }


    public String edit(Long id, Model model) {
        Optional<ContactsInfo> contactOptional = contactRepository.findById(id);
        if (contactOptional.isPresent()) {
            ContactsInfo contact = contactOptional.get();
            model.addAttribute("contact", contact);
            return "edit";
        } else {
            return "error";
        }
    }
    public String update(Long id, ContactForm form) {
        Optional<ContactsInfo> optionalContact = contactRepository.findById(id);
        ContactsInfo contact = optionalContact.orElseThrow(() -> new RuntimeException("お問い合わせが見つかりません: " + id));

        contact.setLastName(form.getLastName());
        contact.setFirstName(form.getFirstName());
        contact.setEmail(form.getEmail());
        contact.setPhone(form.getPhone());
        contact.setZipCode(form.getZipCode());
        contact.setAddress(form.getAddress());
        contact.setBuildingName(form.getBuildingName());
        contact.setContactType(form.getContactType());
        contact.setBody(form.getBody());

        contactRepository.save(contact);

        return "redirect:/admin/contacts/" + id;
    }

    public ContactsInfo getById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }
    
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
