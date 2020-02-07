package tfire.springdemo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import tfire.springdemo.model.Comment;
import tfire.springdemo.model.CommentExample;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}