package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.dto.CommentsCountByEventDto;
import ru.practicum.ewm.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEvent_Id(Long eventId, Pageable pageable);

    List<Comment> findByAuthor_Id(Long userId);

    Optional<Comment> findByAuthor_IdAndId(Long userId, Long id);

    @Query("select new ru.practicum.ewm.dto.CommentsCountByEventDto(c.event.id, COUNT(c)) " +
            "from comments as c where c.event.id in ?1 " +
            "GROUP BY c.event.id")
    List<CommentsCountByEventDto> countCommentByEvent(List<Long> eventIds);

    @Query("select c " +
            "from comments as c " +
            "where lower(c.text) like lower(concat('%', ?1, '%') )")
    List<Comment> searchComments(String text, Pageable pageable);
}