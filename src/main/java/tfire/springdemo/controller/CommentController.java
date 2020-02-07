package tfire.springdemo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tfire.springdemo.dto.CommentCreateDTO;
import tfire.springdemo.dto.CommentDTO;
import tfire.springdemo.dto.ResultDTO;
import tfire.springdemo.enums.CommentTypeEnum;
import tfire.springdemo.exception.CustomizeErrorCode;
import tfire.springdemo.model.Comment;
import tfire.springdemo.model.User;
import tfire.springdemo.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody // automatic serialize return val to JSON and send to front end
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment, user);

        return ResultDTO.successOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOs = commentService.listByTargetId(id, "like", CommentTypeEnum.COMMENT);
        return ResultDTO.successOf(commentDTOs);
    }

    @GetMapping("/comment/delete/{id}")
    public String delete(@PathVariable(name = "id") Long commentId,
                         @RequestParam(name = "question", defaultValue = "0") Long questionId) {
        commentService.delete(commentId);
        return "redirect:/question/" + questionId;
    }
}
