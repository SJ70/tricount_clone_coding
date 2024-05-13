package com.example.tricount.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AmountPerMember {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    @JsonBackReference
    private Balance balance;

    private BigDecimal amount;

    public AmountPerMember(Member member, Balance balance, BigDecimal amount) {
        this.member = member;
        this.balance = balance;
        this.amount = amount.setScale(0, RoundingMode.HALF_UP);
    }

}
