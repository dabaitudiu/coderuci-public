package tfire.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tfire.springdemo.dto.CommentDTO;
import tfire.springdemo.dto.PaginationDTO;
import tfire.springdemo.dto.QuestionDTO;
import tfire.springdemo.enums.CommentTypeEnum;
import tfire.springdemo.service.CommentService;
import tfire.springdemo.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           @RequestParam(name = "order", defaultValue = "time") String order,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, order, CommentTypeEnum.QUESTION);

        List<QuestionDTO> subRelated = relatedQuestions.subList(0,Math.min(10, relatedQuestions.size()));

        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", subRelated);
        return "question";
    }

    @GetMapping("/question/{id}/local")
    public String localRefresh(@PathVariable(name = "id") Long id,
                           @RequestParam(name = "order", defaultValue = "like") String order,
                           Model model) {
        List<CommentDTO> comments = commentService.listByTargetId(id, order, CommentTypeEnum.QUESTION);
        model.addAttribute("comments", comments);
        return "question::comment_list";
    }

    @GetMapping("/question/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        questionService.delete(id);
        return "redirect:/";
    }

}
