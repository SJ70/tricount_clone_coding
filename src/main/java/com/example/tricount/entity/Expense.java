package com.example.tricount.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @ManyToOne
    @NotNull
    @JsonManagedReference
    private Member member;

    @ManyToOne
    @NotNull
    @JsonBackReference
    private Settlement settlement;

    @NotNull
    private BigDecimal amount;

    private LocalDateTime date;

    public Expense(String title, Settlement settlement, Member member, BigDecimal amount) {
        this.title = title;
        this.member = member;
        this.settlement = settlement;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String format = "id: %d, title: %s, member: %s, settlement: %s, amount: %s, date: %s";
        return String.format(format, id, title, member.getNickname(), settlement.getTitle(), amount, date);
    }

}
