package jmp.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subscription {

    private final String bankcardNumber;
    private final LocalDate startDate;
    private static final List<Subscription> SUBSCRIPTIONS = new ArrayList<>();

    public Subscription(String bankcardNumber) {
        this.bankcardNumber = bankcardNumber;
        this.startDate = LocalDate.now();
        SUBSCRIPTIONS.add(this);
    }

    public String getBankcardNumber() {
        return bankcardNumber;
    }

    public static List<Subscription> getSubscriptions() {
        return SUBSCRIPTIONS;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "bankcard='" + bankcardNumber + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}