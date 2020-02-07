package tfire.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tfire.springdemo.cache.TagCache;
import tfire.springdemo.dto.QuestionDTO;
import tfire.springdemo.mapper.QuestionMapper;
import tfire.springdemo.mapper.UserMapper;
import tfire.springdemo.model.Question;
import tfire.springdemo.model.User;
import tfire.springdemo.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {

        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());

        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String postPublish(
            @RequestParam(value="title",required = false) String title,
            @RequestParam(value="description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "Title cannot be empty.");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error","question content cannot be empty.");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "Tag cannot be empty");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error","User not signed in");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        question.setId(id);

        questionService.createOrUpdate(question);
        model.addAttribute("tags", TagCache.get());

        return "redirect:/";
    }
}
