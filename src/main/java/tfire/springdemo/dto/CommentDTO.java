package tfire.springdemo.dto;

import lombok.Data;
import tfire.springdemo.model.User;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
