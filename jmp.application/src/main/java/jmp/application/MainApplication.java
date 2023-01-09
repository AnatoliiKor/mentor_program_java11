package jmp.application;

import jmp.dto.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MainApplication {
    public static void main(String[] args) {
        Map<Integer, User> users = new HashMap<>();
        users.put(1, new User("Julie", "Fox", LocalDate.of(1980, 3, 15)));
        users.put(2, new User("Andriy", "Ivanov", LocalDate.of(1995, 6, 11)));
        users.put(3, new User("Ivan", "Younger", LocalDate.of(2015, 1, 25)));
        System.out.println(users);
    }
}
