package jmp.service.api;

import jmp.dto.BankCard;
import jmp.dto.Subscription;
import jmp.dto.User;

import java.util.List;
import java.util.Optional;

public interface Service {

  void subscribe(BankCard card);
  Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);
  List<User> getAllUsers();


}
