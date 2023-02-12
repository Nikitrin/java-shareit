package ru.practicum.shareit.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    private void addUsers() {
        userRepository.save(new User("Name", "email@email.ru"));
        userRepository.save(new User("Name2", "email2@email.ru"));
    }

    @AfterEach
    private void deleteUsers() {
        userRepository.deleteAll();
    }

    @Test
    void findById() {
        assertEquals(2, userRepository.findByNameLike("Na").size());
    }
}