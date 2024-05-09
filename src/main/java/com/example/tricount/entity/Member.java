package com.example.tricount.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@NoArgsConstructor
@Data
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "member")
    @JsonBackReference
    private List<Expense> expenses = new ArrayList<>();

    @BatchSize(size = 100)
    @ManyToMany(mappedBy = "members")
    @JsonBackReference
    private List<Settlement> settlements = new ArrayList<>();

    public Member(String username, String userId, String password) {
        this.username = username;
        this.userId = userId;
        this.password = password;
    }

}
