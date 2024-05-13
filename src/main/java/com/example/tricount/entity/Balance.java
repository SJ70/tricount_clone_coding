package com.example.tricount.entity;

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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
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

        // 평균 비용
        Optional<BigDecimal> entireAmount = settlement.getExpenses().stream().map(Expense::getAmount).reduce(BigDecimal::add);
        BigDecimal membersCount = new BigDecimal(settlement.getMembers().size());
        averageAmount = entireAmount.isPresent() ? entireAmount.get().divide(membersCount) : new BigDecimal("0");

        // 참여자 별로 사용한 비용
        Map<Member, BigDecimal> amountsPerMembers = new HashMap<>();
        for (Expense expense : settlement.getExpenses()) {
            Member member = expense.getMember();
            BigDecimal amount = expense.getAmount();
            BigDecimal currentAmount = amountsPerMembers.getOrDefault(member, new BigDecimal("0"));
            amountsPerMembers.put(member, currentAmount.add(amount));
        }
        this.amountPerMembers = amountsPerMembers
                .entrySet()
                .stream()
                .map(e -> new AmountPerMember(e.getKey(), this, e.getValue()))
                .toList();

        // 평균 비용 초과/미만 값
        List<OverAverageAmountPerMember> overAverageAmountsPerMembers = this.amountPerMembers.stream()
                .map(amountPerMember -> new OverAverageAmountPerMember(
                        amountPerMember.getMember(),
                        amountPerMember.getAmount().subtract(averageAmount)))
                .sorted(Comparator.comparing(OverAverageAmountPerMember::getOverAverageAmount))
                .toList();
        int left = 0;
        int right = settlement.getMembers().size() - 1;
        while (left < right) {

            OverAverageAmountPerMember receiverNode = overAverageAmountsPerMembers.get(left);
            Member receiver = receiverNode.getMember();
            BigDecimal receiverOverValue = receiverNode.getOverAverageAmount().abs();

            OverAverageAmountPerMember senderNode = overAverageAmountsPerMembers.get(right);
            Member sender = senderNode.getMember();
            BigDecimal senderBelowValue = senderNode.getOverAverageAmount().abs();

            switch (receiverOverValue.compareTo(senderBelowValue)) {
                case -1 :
                    transfers.add(new Transfer(this, sender, receiver, receiverOverValue));
                    left++;
                    senderNode.add(receiverOverValue);
                    break;
                case 0 :
                    transfers.add(new Transfer(this, sender, receiver, receiverOverValue));
                    left++;
                    right--;
                    break;
                case 1 :
                    transfers.add(new Transfer(this, sender, receiver, senderBelowValue));
                    right--;
                    receiverNode.sub(senderBelowValue);
                    break;
            }
        }

    }

    @AllArgsConstructor
    @Getter
    private class OverAverageAmountPerMember {
        private Member member;
        private BigDecimal overAverageAmount;

        public void add(BigDecimal value) {
            overAverageAmount = overAverageAmount.add(value);
        }

        public void sub(BigDecimal value) {
            overAverageAmount = overAverageAmount.subtract(value);
        }
    }

}
