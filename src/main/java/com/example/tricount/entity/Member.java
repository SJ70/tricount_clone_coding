package com.example.tricount.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Member(String username, String userId, String password) {
        this.username = username;
        this.userId = userId;
        this.password = password;
    }

}
