package jmp.cloud.bank.impl;

import jmp.bank.api.Bank;
import jmp.dto.*;

public class BankImpl implements Bank {

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        if (cardType == BankCardType.CREDIT) {
            return new CreditBankCard(user);
        }

        if (cardType == BankCardType.DEBIT) {
            return new DebitBankCard(user);
        }

        return null;
    }
}
