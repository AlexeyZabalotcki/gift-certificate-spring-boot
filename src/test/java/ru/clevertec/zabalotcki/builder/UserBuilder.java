package ru.clevertec.zabalotcki.builder;

import ru.clevertec.zabalotcki.model.User;

public class UserBuilder implements TestBuilder<User> {

    private Long id = 1L;
    private String name = "User";

    @Override
    public User build() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
