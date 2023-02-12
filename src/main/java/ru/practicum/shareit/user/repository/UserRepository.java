package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user " +
        "from User user " +
        "where lower(user.name) like lower(concat('%', ?1, '%')) " +
        "order by user.id")
    List<User> findByNameLike2(String name);

    @Query(value = "select * " +
        "from users " +
        "where lower(name) like lower(concat('%', ?1, '%')) " +
        "order by id", nativeQuery = true)
    List<User> findByNameLike(String name);
}
