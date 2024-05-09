package com.example.tricount.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
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

    @NotNull
    private BigDecimal amount;

    private LocalDateTime date;

    public Expense(String title, Member member, Number amount) {
        this.title = title;
        this.member = member;
        this.amount = (BigDecimal) amount;
        this.date = LocalDateTime.now();
    }

}
