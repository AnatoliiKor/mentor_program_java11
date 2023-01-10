package jmp.cloud.service.impl;

import jmp.dto.BankCard;
import jmp.dto.Subscription;
import jmp.dto.User;
import jmp.service.api.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    @Override
    public void subscribe(BankCard card) {
        if (!isCardSubscribed(card)) {
            new Subscription(card.getNumber());
        }
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Subscription.getSubscriptions().stream().filter(s -> s.getBankcardNumber().equals(cardNumber)).findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return BankCard.getCardList().stream().map(BankCard :: getUser).collect(Collectors.toList());
    }

    private boolean isCardSubscribed(BankCard card) {
        return Subscription.getSubscriptions().stream().anyMatch(subscription -> subscription.getBankcardNumber().equals(card.getNumber()));
    }

    private BankCard getBankCardByNumber(String number) {
        return BankCard.getCardList().stream().filter(card -> card.getNumber().equals(number)).findFirst().orElse(null);
    }

    public static void printAllEntities() {
        System.out.println("-----------------------ALL ENTITIES------------------------");
        BankCard.getCardList().forEach(System.out :: println);
        Subscription.getSubscriptions().forEach(System.out :: println);
    }

}
