package com.example.tricount.entity;

import com.example.tricount.util.BalanceUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor
public class Balance {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "settlement_id", referencedColumnName = "id")
    private Settlement settlement;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "balance")
    @JsonManagedReference
    private Collection<AmountPerMember> amountPerMembers = new ArrayList<>();

    private BigDecimal averageAmount;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "balance")
    @JsonManagedReference
    private Collection<Transfer> transfers = new ArrayList<>();

    public Balance(Settlement settlement) {
        this.settlement = settlement;
        this.averageAmount = BalanceUtil.getAverageAmount(settlement);
        this.amountPerMembers = BalanceUtil.getAmountsPerMembers(this, settlement);
        this.transfers = BalanceUtil.getTransfers(this, settlement, amountPerMembers, averageAmount);
    }

    public boolean canAccessedBy(String username) {
        return amountPerMembers.stream().map(AmountPerMember::getMember).map(Member::getUsername).toList().contains(username);
    }

}
