package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    @Query("select itemRequest " +
        "from ItemRequest itemRequest " +
        "where itemRequest.requester.id = ?1 " +
        "order by itemRequest.id desc")
    List<ItemRequest> findItemsByRequesterId(Long requesterId);

    @Query("select itemRequest " +
        "from ItemRequest itemRequest " +
        "where itemRequest.requester.id <> ?1 " +
        "order by itemRequest.id desc")
    List<ItemRequest> findAllItemRequests(Long userId, PageRequest pageRequest);
}
