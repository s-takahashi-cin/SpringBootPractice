package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.ContactsInfo;
import com.example.demo.form.ContactForm;
import com.example.demo.repo.ContactRepository;
import com.example.demo.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class ContactsController {
    
    private final ContactRepository contactRepository;
    private final ContactService contactService;

    
    
    @Autowired
    public ContactsController(ContactRepository contactRepository, ContactService contactService) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
    }
    
    @GetMapping("/contacts")
    public String view(Model model) {
            List<ContactsInfo> contacts = contactRepository.findAll();
            model.addAttribute("contacts", contacts);
            return "contacts";
    }

    //詳細ページ
    @GetMapping("/contacts/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        return contactService.getContactDetail(id, model);
    }

    //編集ページ
    @GetMapping("/contacts/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        return contactService.edit(id, model);
    }

    @PostMapping("/contacts/{id}")
    public String update(@PathVariable Long id, @ModelAttribute ContactForm form) {
        return contactService.update(id, form);
    }
    
    //削除
    @PostMapping("/contacts/delete")
    public String delete(@RequestParam("id") Long id) {
        contactService.delete(id);
        return "redirect:/admin/deleteComplete";
    }
    @GetMapping("/deleteComplete")
        public String deleteComplete() {
        return "deleteComplete";
    } 

    // お問い合わせフォーム表示
    @GetMapping("/contactForm")
    public String contactForm(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contactForm";
    }

    // お問い合わせ内容確認
    @PostMapping("/contactForm")
    public String confirmation(@ModelAttribute("contactForm") @Validated ContactForm contactForm, BindingResult errorResult, HttpServletRequest request) {
        if (errorResult.hasErrors()) {
            return "contactForm";
        }

          HttpSession session = request.getSession();
          session.setAttribute("contactForm", contactForm);
  
        
        return "confirmation";
    }

    // お問い合わせ情報保存
    @PostMapping("/confirmation")
        public String confirm(Model model, HttpServletRequest request) {
            HttpSession session = request.getSession();
            ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
    
                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.setLastName(contactForm.getLastName());
                contactsInfo.setFirstName(contactForm.getFirstName());
                contactsInfo.setEmail(contactForm.getEmail());
                contactsInfo.setPhone(contactForm.getPhone());
                contactsInfo.setZipCode(contactForm.getZipCode());
                contactsInfo.setAddress(contactForm.getAddress());
                contactsInfo.setBuildingName(contactForm.getBuildingName());
                contactsInfo.setContactType(contactForm.getContactType());
                contactsInfo.setBody(contactForm.getBody());
    
                contactRepository.save(contactsInfo);
            

            return "redirect:/admin/complete";
        }
    
    @GetMapping("/complete")
    public String complete(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("contactForm") == null) {
            return "redirect:/contact";
        }
        
        ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
        model.addAttribute("contactForm", contactForm);
        
        return "completion";
    }

//ログアウト
@GetMapping("/admin/logout")
    public String logout() {
        // ログアウト処理を行うため、ここでは何もする必要はありません。
        // Spring Securityがログアウト処理を行った後、自動的にログインページにリダイレクトされます。
        return "redirect:/admin/signin"; // ログアウト成功後にログインページにリダイレクト
    }

}
