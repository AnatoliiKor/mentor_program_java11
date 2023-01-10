package jmp.application;

import jmp.bank.api.Bank;
import jmp.cloud.bank.impl.BankImpl;
import jmp.cloud.bank.impl.CardNotFoundException;
import jmp.cloud.service.impl.ServiceImpl;
import jmp.cloud.service.impl.SubscriptionNotFoundException;
import jmp.dto.BankCard;
import jmp.dto.BankCardType;
import jmp.dto.Subscription;
import jmp.dto.User;
import jmp.service.api.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MainApplication {
    private static Map<Integer, User> users;

    public static void main(String[] args) {
        Map<Integer, User> users = initiateUsers();

        Bank bank = new BankImpl();
        Service service = new ServiceImpl();

        bank.createBankCard(users.get(1), BankCardType.DEBIT);
        bank.createBankCard(users.get(2), BankCardType.CREDIT);
        bank.createBankCard(users.get(1), BankCardType.CREDIT);
        bank.createBankCard(users.get(3), BankCardType.CREDIT);

        BankCard.getCardList().forEach(service::subscribe);

        BankCard card = BankCard.getCardList().stream()
                .findAny()
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        try {
            Subscription subscription = service
                    .getSubscriptionByBankCardNumber(card.getNumber())
                    .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
            System.out.println("new subscription: " + subscription);

            System.out.println("------Subscription with wrong card number-------");
            service.getSubscriptionByBankCardNumber("4654")
                    .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("-------------");
        }

        service.getAllUsers().forEach(System.out::println);
        Service.isPayableUser(users.get(2));
        System.out.printf("Average age of users is %4.1f years %n", service.getAverageUsersAge());

        users.forEach((key, value) ->
                System.out.printf("Is %s payable? %b. %n", value.getName() + " " + value.getSurname(), Service.isPayableUser(value)));
        ServiceImpl.printAllEntities();

        var timePoint = LocalDate.of(2023, 1, 10);
        service.getAllSubscriptionsByCondition(subscription -> subscription.getStartDate().isBefore(timePoint))
                .forEach(System.out::println);
    }

    private static Map<Integer, User> initiateUsers() {
        Map<Integer, User> users = new HashMap<>();
        users.put(1, new User("Julie", "Fox", LocalDate.of(1980, 3, 15)));
        users.put(2, new User("Andriy", "Ivanov", LocalDate.of(1995, 6, 11)));
        users.put(3, new User("Ivan", "Younger", LocalDate.of(2015, 1, 25)));
        users.put(3, new User("Olga", "Budko", LocalDate.of(1970, 11, 1)));
        return users;
    }
}
