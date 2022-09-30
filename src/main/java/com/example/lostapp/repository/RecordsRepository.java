package com.example.lostapp.repository;

import com.example.lostapp.model.Record;
import com.example.lostapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends JpaRepository<Record, Long> {}
