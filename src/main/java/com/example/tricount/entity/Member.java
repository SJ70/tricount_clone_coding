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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Member implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String nickname;

    @NotNull
    @Column(unique = true)
    private String username;

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

    public Member(String nickname, String username, String password) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
    }

    public void addSettlement(Settlement settlement) {
        this.settlements.add(settlement);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of("USER")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("id: %d, nickname: %s, username: %s", id, nickname, username);
    }
}
