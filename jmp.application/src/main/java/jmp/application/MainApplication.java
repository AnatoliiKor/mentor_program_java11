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

        //      Create bank users
        Map<Integer, User> users = initiateUsers();

        Bank bank = new BankImpl();
        Service service = new ServiceImpl();

        //      Implement Bank into jmp-cloud-bank-impl. Method should create a new class depending on the type
        bank.createBankCard(users.get(1), BankCardType.DEBIT);
        bank.createBankCard(users.get(2), BankCardType.CREDIT);
        bank.createBankCard(users.get(1), BankCardType.CREDIT);
        bank.createBankCard(users.get(3), BankCardType.CREDIT);

        //      subscribe all cards
        BankCard.getCardList().forEach(service::subscribe);


        BankCard card = BankCard.getCardList().stream()
                .findAny()
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        // find subscription by subscribed card and by wrong number with exception
        try {
            Subscription subscription = service
                    .getSubscriptionByBankCardNumber(card.getNumber())
                    .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
            System.out.println("found subscription: " + subscription);

            System.out.println("------Subscription with wrong card number-------");
            service.getSubscriptionByBankCardNumber("4654")
                    .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("-------------");
        }

        // Add a new default method for Service interface, which uses getAllUsers. Use LocalDate.now(), ChronoUnit and mapToLong
        System.out.printf("Average age of users is %4.1f years %n", service.getAverageUsersAge());

        // Add a new static method for Service interface, which returns true, if the user is over 18 years old
        users.forEach((key, value) ->
                System.out.printf("Is %s payable? %b. %n", value.getName() + " " + value.getSurname(), Service.isPayableUser(value)));

        // Add a new method for Service interface - getAllSubscriptionsByCondition
        var timePoint = LocalDate.of(2023, 1, 10);
        service.getAllSubscriptionsByCondition(subscription -> subscription.getStartDate().isBefore(timePoint))
                .forEach(System.out::println);

        // printAllEntities
        ServiceImpl.printAllEntities();
        service.getAllUsers().forEach(System.out::println);
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
