package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
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
        @ManyToOne
        @JoinColumn(name = "item_id", nullable = false)
        private Item item;
        @ManyToOne
        @JoinColumn(name = "booker_id", nullable = false)
        private User booker;
        @Column(name = "status")
        private String status;
}