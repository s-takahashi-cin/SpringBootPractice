package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.ContactsInfo;

@Repository
public interface ContactRepository extends JpaRepository<ContactsInfo, Long> {
}
