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
//        return BankCard.getCardList().stream().filter(card -> card.getNumber().equals(cardNumber)).findFirst();
        return Subscription.getSubscriptions().stream().filter(s -> s.getBankcard().equals(cardNumber)).findFirst();
    }

    @Override
    public List<User> getAllUsers() {
//        List<String> cards = Subscription.getSubscriptions().stream().map(Subscription::getBankcard).collect(Collectors.toList());
//        List<BankCard> bankCards = BankCard.getCardList();
        return BankCard.getCardList().stream().map(BankCard :: getUser).collect(Collectors.toList());

    }

    private boolean isCardSubscribed(BankCard card) {
        return Subscription.getSubscriptions().stream().anyMatch(c -> c.equals(card));
    }

    private BankCard getBankCardByNumber(String number) {
        return BankCard.getCardList().stream().filter(card -> card.getNumber().equals(number)).findFirst().orElse(null);
    }


}
