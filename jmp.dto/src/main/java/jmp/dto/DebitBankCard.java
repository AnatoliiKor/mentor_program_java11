package jmp.dto;

public class DebitBankCard extends BankCard {

    public DebitBankCard(User user) {
        this.user = user;
        var number = CARD_LIST.size() + 2000001;
        this.number = BankCardType.DEBIT.name() + number;
        CARD_LIST.add(this);
    }
}