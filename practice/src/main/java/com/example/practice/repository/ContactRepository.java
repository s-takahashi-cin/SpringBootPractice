package com.example.practice.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}
