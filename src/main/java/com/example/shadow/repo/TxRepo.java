package com.example.shadow.repo;


import com.example.shadow.entity.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface TxRepo extends JpaRepository<Transaction, UUID> {
    Optional<Transaction> findByNipRef(String nipRef);
}

