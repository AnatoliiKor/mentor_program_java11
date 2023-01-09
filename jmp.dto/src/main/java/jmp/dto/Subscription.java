package jmp.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subscription {

    private final String bankcard;
    private LocalDate startDate;
    private static final List<Subscription> SUBSCRIPTIONS = new ArrayList<>();

    public Subscription(String bankcard) {
        this.bankcard = bankcard;
        this.startDate = LocalDate.now();
    }

    public String getBankcard() {
        return bankcard;
    }

    public static List<Subscription> getSubscriptions() {
        return SUBSCRIPTIONS;
    }

}