package jmp.service.api;

public class SubscriptionNotFoundException extends RuntimeException{

    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
