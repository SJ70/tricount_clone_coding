package com.example.tricount.util;

import com.example.tricount.entity.AmountPerMember;
import com.example.tricount.entity.Balance;
import com.example.tricount.entity.Expense;
import com.example.tricount.entity.Member;
import com.example.tricount.entity.Settlement;
import com.example.tricount.entity.Transfer;
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

public class BalanceUtil {

    public static BigDecimal getAverageAmount(Settlement settlement) {
        Optional<BigDecimal> entireAmount = settlement.getExpenses().stream().map(Expense::getAmount).reduce(BigDecimal::add);
        BigDecimal membersCount = new BigDecimal(settlement.getMembers().size());
        return entireAmount.isPresent() ? entireAmount.get().divide(membersCount) : new BigDecimal("0");
    }

    public static Collection<AmountPerMember> getAmountsPerMembers(Balance balance, Settlement settlement) {
        Map<Member, BigDecimal> amountsPerMembers = new HashMap<>();
        for (Expense expense : settlement.getExpenses()) {
            Member member = expense.getMember();
            BigDecimal amount = expense.getAmount();
            BigDecimal currentAmount = amountsPerMembers.getOrDefault(member, new BigDecimal("0"));
            amountsPerMembers.put(member, currentAmount.add(amount));
        }
        return amountsPerMembers
                .entrySet()
                .stream()
                .map(e -> new AmountPerMember(e.getKey(), balance, e.getValue()))
                .toList();
    }

    public static Collection<Transfer> getTransfers(
            Balance balance,
            Settlement settlement,
            Collection<AmountPerMember> amountPerMembers,
            BigDecimal averageAmount
    ) {
        Collection<Transfer> transfers = new ArrayList<>();

        List<OverAverageAmountPerMember> overAverageAmountsPerMembers = amountPerMembers.stream()
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
                    transfers.add(new Transfer(balance, sender, receiver, receiverOverValue));
                    left++;
                    senderNode.add(receiverOverValue);
                    break;
                case 0 :
                    transfers.add(new Transfer(balance, sender, receiver, receiverOverValue));
                    left++;
                    right--;
                    break;
                case 1 :
                    transfers.add(new Transfer(balance, sender, receiver, senderBelowValue));
                    right--;
                    receiverNode.sub(senderBelowValue);
                    break;
            }
        }

        return transfers;
    }

    @AllArgsConstructor
    @Getter
    private static class OverAverageAmountPerMember {
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
