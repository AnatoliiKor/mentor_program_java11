package jmp.dto;

import java.util.ArrayList;
import java.util.List;

public class BankCard {

    private String number;
    private User user;
    private static final List<BankCard> CARD_LIST = new ArrayList<>();

    public BankCard(User user) {
        this.user = user;
        var number = CARD_LIST.size() + 1;
        this.number = String.valueOf(number);
        CARD_LIST.add(this);

    }

    public String getNumber() {
        return number;
    }


    public User getUser() {
        return user;
    }

    public static List<BankCard> getCardList() {
        return CARD_LIST;
    }


}