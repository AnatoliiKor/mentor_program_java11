package jmp.dto;

import java.util.ArrayList;
import java.util.List;

public class BankCard {

    String number;
    User user;
    static final List<BankCard> CARD_LIST = new ArrayList<>();

    public String getNumber() {
        return number;
    }

    public User getUser() {
        return user;
    }

    public static List<BankCard> getCardList() {
        return CARD_LIST;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "number='" + number + '\'' +
                ", user=" + user +
                '}';
    }
}