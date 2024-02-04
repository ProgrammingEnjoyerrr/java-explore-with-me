package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.dto.NewCommentDto;
import ru.practicum.ewm.dto.UpdateCommentDto;
import ru.practicum.ewm.exception.IncorrectParametersException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.enums.EventStatus;
import ru.practicum.ewm.model.mappers.CommentMapper;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.CommentService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentDto createComment(Long userId, Long eventId, NewCommentDto commentDto) {
        final Event event = checkEvent(eventId);
        final User user = checkUser(userId);

        if (event.getEventStatus() != EventStatus.PUBLISHED) {
            throw new IncorrectParametersException("Невозможно добавить комментарий к событию со статусом не PUBLISHED");
        }

        final Comment created = commentRepository.save(CommentMapper.toComment(commentDto, event, user));

        return CommentMapper.toCommentDto(created);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto updateCommentDto) {
        final User user = checkUser(userId);
        final Comment comment = checkComment(commentId);
        checkAuthorComment(user, comment);
        comment.setText(updateCommentDto.getText());
        comment.setLastUpdatedOn(LocalDateTime.now());

        final Comment updated = commentRepository.save(comment);

        return CommentMapper.toCommentDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getComments(Long userId) {
        checkUser(userId);
        final List<Comment> commentList = commentRepository.findByAuthor_Id(userId);
        return commentList.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentByUserAndCommentId(Long userId, Long commentId) {
        checkUser(userId);
        return commentRepository.findByAuthor_IdAndId(userId, commentId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("У пользователя c id=%d  не найден комментарий с id=%d", userId, commentId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsForEvent(Long eventId, Integer from, Integer size) {
        checkEvent(eventId);
        final PageRequest pageable = PageRequest.of(from / size, size);
        return commentRepository.findAllByEvent_Id(eventId, pageable);

    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        final User user = checkUser(userId);
        final Comment comment = checkComment(commentId);
        checkAuthorComment(user, comment);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        checkComment(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public List<Comment> searchComments(String text, Integer from, Integer size) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }

        final Pageable pageable = PageRequest.of(from / size, size);
        return commentRepository.searchComments(text, pageable);
    }

    private Event checkEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие с id=%d  не найдено", id)));
    }

    private User checkUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь c id=%d  не найден", id)));
    }

    private Comment checkComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Комментарий c id=%d  не найден", id)));
    }

    private void checkAuthorComment(User user, Comment comment) {
        if (!comment.getAuthor().equals(user)) {
            throw new IncorrectParametersException("Пользователь не является автором комментария");
        }
    }
}
