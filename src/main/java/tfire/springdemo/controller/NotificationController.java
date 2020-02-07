package tfire.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tfire.springdemo.dto.NotificationDTO;
import tfire.springdemo.dto.PaginationDTO;
import tfire.springdemo.enums.NotificationTypeEnum;
import tfire.springdemo.mapper.CommentMapper;
import tfire.springdemo.model.User;
import tfire.springdemo.service.CommentService;
import tfire.springdemo.service.NotificationService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        }
        else {
            return "redirect:/";
        }
    }
}
