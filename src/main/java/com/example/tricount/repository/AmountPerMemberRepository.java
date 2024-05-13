package com.example.tricount.repository;

import com.example.tricount.entity.AmountPerMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmountPerMemberRepository extends JpaRepository<AmountPerMember, Long> {

}
