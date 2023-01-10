package jmp.application;

import jmp.bank.api.Bank;
import jmp.bank.api.CardNotFoundException;
import jmp.dto.*;
import jmp.service.api.Service;
import jmp.service.api.SubscriptionNotFoundException;

import java.time.LocalDate;
import java.util.*;

public class MainApplication {
    private static Map<Integer, User> users;

    public static void main(String[] args) {

        //      Create bank users
        Map<Integer, User> users = initiateUsers();

//      use ServiceLoader.load() for module loading
        Bank bank = getBank();
        Service service = getService();

        //      Implement Bank into jmp-cloud-bank-impl. Method should create a new class depending on the type
        bank.createBankCard(users.get(1), BankCardType.DEBIT);
        bank.createBankCard(users.get(2), BankCardType.CREDIT);
        bank.createBankCard(users.get(1), BankCardType.CREDIT);
        bank.createBankCard(users.get(3), BankCardType.DEBIT);

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

        users.values().forEach(value ->
                System.out.printf("Is %s %s payable? %b. %n", value.getName(), value.getSurname(), Service.isPayableUser(value)));

        // Add a new method for Service interface - getAllSubscriptionsByCondition
        var timePoint = LocalDate.of(2023, 1, 10);
        service.getAllSubscriptionsByCondition(subscription -> subscription.getStartDate().isBefore(timePoint))
                .forEach(System.out::println);

        //Reimplement createBankCard with method reference (ex: CreditBankCard::new).
        new ArrayList<>(users.values()).forEach(CreditBankCard::new);

        // printAllEntities
        System.out.println("-----------------------ALL ENTITIES------------------------");
        BankCard.getCardList().forEach(System.out::println);
        Subscription.getSubscriptions().forEach(System.out::println);
        service.getAllUsers().forEach(System.out::println);
        System.out.println("-----------------------------------------------");
    }


    private static Map<Integer, User> initiateUsers() {
        Map<Integer, User> users = new HashMap<>();
        users.put(1, new User("Julie", "Fox", LocalDate.of(1980, 3, 15)));
        users.put(2, new User("Andriy", "Ivanov", LocalDate.of(1995, 6, 11)));
        users.put(3, new User("Ivan", "Younger", LocalDate.of(2015, 1, 25)));
        users.put(4, new User("Olga", "Budko", LocalDate.of(1970, 11, 1)));
        return users;
    }

    //use ServiceLoader.load() for module loading
    private static Bank getBank() {
        ServiceLoader<Bank> serviceLoader = ServiceLoader.load(Bank.class);
        Optional<Bank> optional = serviceLoader.findFirst();
        optional.orElseThrow(() -> new RuntimeException("No service providers found"));
        return optional.get();
    }

    //use ServiceLoader.load() for module loading
    private static Service getService() {
        ServiceLoader<Service> serviceLoader = ServiceLoader.load(Service.class);
        Optional<Service> optional = serviceLoader.findFirst();
        optional.orElseThrow(() -> new RuntimeException("No service providers found"));
        return optional.get();
    }
}
