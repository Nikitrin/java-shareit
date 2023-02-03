package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker = ?1 " +
        "order by booking.id desc")
    List<Booking> getAllUsersBooking(Long userId);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker = ?1 " +
        "and booking.start < current_date " +
        "and booking.end > current_date " +
        "and booking.status = 'APPROVED' " +
        "order by booking.id desc")
    List<Booking> getCurrentUsersBooking(Long userId);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker = ?1 " +
        "and booking.start > current_date " +
        "order by booking.id desc")
    List<Booking> getFutureUsersBooking(Long userId);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker = ?1 " +
        "and booking.end < current_date " +
        "and booking.status = 'APPROVED' " +
        "order by booking.id desc")
    List<Booking> getPastUsersBooking(Long userId);

    @Query("select booking " +
        "from Booking booking " +
        "where booking.booker = ?1 " +
        "and booking.status = ?2 " +
        "order by booking.id desc")
    List<Booking> getWaitingOrRejectedUsersBooking(Long userId, String status);

    //Owner
    @Query("select booking " +
        "from Booking booking " +
        "join Item item " +
        "where item.owner.id = ?1 " +
        "and booking.status = ?2 " +
        "order by booking.id desc")
    List<Booking> getWaitingOrRejectedOwnerBooking(Long userId, String status);

    @Query("select booking " +
        "from Booking booking " +
        "join Item item " +
        "where item.owner.id = ?1 " +
        "order by booking.id desc")
    List<Booking> getAllOwnerBooking(Long userId);

    @Query("select booking " +
        "from Booking booking " +
        "join Item item " +
        "where item.owner.id = ?1 " +
        "and booking.start < current_date " +
        "and booking.end > current_date " +
        "and booking.status = 'APPROVED' " +
        "order by booking.id desc")
    List<Booking> getCurrentOwnerBooking(Long userId);

    @Query("select booking " +
        "from Booking booking " +
        "join Item item " +
        "where item.owner.id = ?1 " +
        "and booking.start > current_date " +
        "order by booking.id desc")
    List<Booking> getFutureOwnerBooking(Long userId);

    @Query("select booking " +
        "from Booking booking " +
        "join Item item " +
        "where item.owner.id = ?1 " +
        "and booking.end < current_date " +
        "and booking.status = 'APPROVED' " +
        "order by booking.id desc")
    List<Booking> getPastOwnerBooking(Long userId);
}
