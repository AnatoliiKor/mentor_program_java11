package jmp.dto;

public class CreditBankCard extends BankCard {

    public CreditBankCard(User user) {
        this.user = user;
        var number = CARD_LIST.size() + 1000001;
        this.number = BankCardType.CREDIT.name() + number;
        CARD_LIST.add(this);
    }
}