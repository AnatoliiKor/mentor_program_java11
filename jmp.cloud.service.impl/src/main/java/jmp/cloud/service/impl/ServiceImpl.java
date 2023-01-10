package jmp.cloud.service.impl;

import jmp.dto.BankCard;
import jmp.dto.Subscription;
import jmp.dto.User;
import jmp.service.api.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceImpl implements Service {

    @Override
    public void subscribe(BankCard card) {
        if (!isCardSubscribed(card)) {
            new Subscription(card.getNumber());
        }
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Subscription.getSubscriptions().stream()
                .filter(s -> s.getBankcardNumber().equals(cardNumber))
                .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        var cardNumbers = Subscription.getSubscriptions().stream()
                .map(Subscription::getBankcardNumber)
                .collect(Collectors.toUnmodifiableList());

        Supplier<Stream<BankCard>> sup = () -> BankCard.getCardList().stream();

        var subscribedUsers = cardNumbers.stream()
                .map(number ->
                        sup.get().filter(bankCard -> number.equals(bankCard.getNumber()))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("This is wrong number"))
                                .getUser())
                .collect(Collectors.toSet());

        return List.copyOf(subscribedUsers);
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return Subscription.getSubscriptions().stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    private boolean isCardSubscribed(BankCard card) {
        return Subscription.getSubscriptions().stream()
                .anyMatch(subscription -> subscription.getBankcardNumber().equals(card.getNumber()));
    }
}
