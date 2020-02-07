package tfire.springdemo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tfire.springdemo.dto.CommentDTO;
import tfire.springdemo.enums.CommentTypeEnum;
import tfire.springdemo.enums.NotificationStatusEnum;
import tfire.springdemo.enums.NotificationTypeEnum;
import tfire.springdemo.exception.CustomizeErrorCode;
import tfire.springdemo.exception.CustomizeException;
import tfire.springdemo.mapper.*;
import tfire.springdemo.model.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // Reply to comment
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

            // increase comment counts to parent comment
//            System.out.println("trying to increase count to comment");
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

            // pass notifications
            createNotification(comment, dbComment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT, commentator.getName(), question.getTitle(), question.getId());
        } else {
            // Reply to Question
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);

            // pass notifications
            createNotification(comment, question.getCreator(), NotificationTypeEnum.REPLY_QUESTION, commentator.getName(), question.getTitle(), question.getId());
        }
    }

    private void createNotification(Comment comment, Long receiver, NotificationTypeEnum notificationType, String notifierName, String outerTitle, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, String order, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample
                .createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        if (order.equals("time")){
            commentExample.setOrderByClause("gmt_create desc");
        } else if (order.equals("like")){
            commentExample.setOrderByClause("like_count desc");
        } else {
            commentExample.setOrderByClause("like_count desc");
        }
        // Retrieve all comments under a question using question id.
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) return new ArrayList<>();

        // Retrieve all commentators ids and put in a set.
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // Using commentators id, retrieve all Users, and put in a <id, User> map.
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // Now we have the user full info (name, etc. rather than just an id), convert comment to commentDTO.
        // This helps because we can just put the User in the model, and pass it to the front end.
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

    public void delete(Long id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        question.setCommentCount(-1);
        questionExtMapper.incCommentCount(question);
        commentMapper.deleteByPrimaryKey(id);
    }
}
