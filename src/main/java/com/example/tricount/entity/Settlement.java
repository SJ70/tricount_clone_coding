package com.example.tricount.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@NoArgsConstructor
@Data
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

    public Settlement(String title) {
        this.title = title;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

}
