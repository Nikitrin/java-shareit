package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker.id = ?1 " +
        "order by booking.id desc")
    List<Booking> getAllUsersBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker.id = ?1 " +
        "and booking.start < current_timestamp " +
        "and booking.end > current_timestamp " +
        "order by booking.id desc")
    List<Booking> getCurrentUsersBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker.id = ?1 " +
        "and booking.start > current_timestamp " +
        "order by booking.id desc")
    List<Booking> getFutureUsersBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker.id = ?1 " +
        "and booking.end < current_timestamp " +
        "order by booking.id desc")
    List<Booking> getPastUsersBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker.id = ?1 " +
        "and booking.status = 'REJECTED' " +
        "order by booking.id desc")
    List<Booking> getRejectedUsersBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker.id = ?1 " +
        "and booking.status = 'WAITING' " +
        "order by booking.id desc")
    List<Booking> getWaitingUsersBooking(Long userId, PageRequest pageRequest);

    //Owner
    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.owner.id = ?1 " +
        "and booking.status = 'REJECTED' " +
        "order by booking.id desc")
    List<Booking> getRejectedOwnerBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.owner.id = ?1 " +
        "and booking.status = 'WAITING' " +
        "order by booking.id desc")
    List<Booking> getWaitingOwnerBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.owner.id = ?1 " +
        "order by booking.id desc")
    List<Booking> getAllOwnerBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.owner.id = ?1 " +
        "and booking.start < current_timestamp " +
        "and booking.end > current_timestamp " +
        "order by booking.id desc")
    List<Booking> getCurrentOwnerBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.owner.id = ?1 " +
        "and booking.start > current_timestamp " +
        "order by booking.id desc")
    List<Booking> getFutureOwnerBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.owner.id = ?1 " +
        "and booking.end < current_timestamp " +
        "order by booking.id desc")
    List<Booking> getPastOwnerBooking(Long userId, PageRequest pageRequest);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.id = ?1 " +
        "order by booking.id asc")
    List<Booking> getLastBooking(Long itemId);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.item.id = ?1 " +
        "order by booking.start desc")
    List<Booking> getNextBooking(Long itemId);
}
