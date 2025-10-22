package com.example.shadow.repo;

import com.example.shadow.entity.ProvisionalCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProvRepo extends JpaRepository<ProvisionalCredit, UUID> {

}
