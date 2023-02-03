package ru.practicum.shareit.booking.model;

import lombok.*;
import ru.practicum.shareit.booking.common.BookingStatus;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "start_date")
        private LocalDateTime start;
        @Column(name = "end_date")
        private LocalDateTime end;
        @Column(name = "item_id")
        private Long itemId;
//        @ManyToOne(fetch = FetchType.LAZY, optional = false)
//        @JoinColumn(name = "id", nullable = false)
        @Column(name = "booker_id")
        private Long booker;
        @Column(name = "status")
        private BookingStatus status;
}