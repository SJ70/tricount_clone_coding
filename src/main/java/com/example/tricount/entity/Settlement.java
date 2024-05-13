package com.example.tricount.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@NoArgsConstructor
@Getter
public class Settlement {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @BatchSize(size = 100)
    @ManyToMany
    @JsonManagedReference
    private Set<Member> members = new HashSet<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "settlement")
    @JsonManagedReference
    private List<Expense> expenses = new ArrayList<>();

    @OneToOne(mappedBy = "settlement")
    @Nullable
    @JsonBackReference
    private Balance balance;

    public Settlement(String title) {
        this.title = title;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public boolean containsMemberByUsername(String username) {
        return members.stream().map(Member::getUsername).anyMatch(name -> name.equals(username));
    }

    public boolean isBalanced() {
        return this.balance != null;
    }

    @Override
    public String toString() {
        String members = this.members.stream().map(Member::getNickname).collect(Collectors.joining(", "));
        String expenses = this.expenses.stream().map(Expense::getTitle).collect(Collectors.joining(", "));
        return String.format("id: %d, title: %d, members: [%s], expenses: [%s]", id, title, members, expenses);
    }

}
