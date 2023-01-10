package jmp.service.api;

import jmp.dto.BankCard;
import jmp.dto.Subscription;
import jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    void subscribe(BankCard card);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

    // Add a new default method for Service interface, which uses getAllUsers. Use LocalDate.now(), ChronoUnit and mapToLong
    default double getAverageUsersAge() {
        return getAllUsers()
                .stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                .average()
                .orElse(-1);
    }

    // Add a new static method for Service interface, which returns true, if the user is over 18 years old
    static boolean isPayableUser(User user) {
        var age = ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now());
        return age >= 18;
    }

}
