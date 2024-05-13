package com.example.tricount.repository;

import com.example.tricount.entity.Transfer;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Collection<Transfer> findAllByReceiverId(Long id);

    Collection<Transfer> findAllBySenderId(Long id);

}
