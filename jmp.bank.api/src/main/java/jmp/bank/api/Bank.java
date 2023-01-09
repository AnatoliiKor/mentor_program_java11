package jmp.bank.api;

import jmp.dto.BankCard;
import jmp.dto.BankCardType;
import jmp.dto.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);
}
