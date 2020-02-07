package tfire.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tfire.springdemo.cache.HotTagCache;
import tfire.springdemo.dto.HotTagDTO;
import tfire.springdemo.dto.PaginationDTO;
import tfire.springdemo.dto.QuestionDTO;
import tfire.springdemo.mapper.QuestionMapper;
import tfire.springdemo.mapper.UserMapper;
import tfire.springdemo.model.Question;
import tfire.springdemo.model.User;
import tfire.springdemo.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "7") Integer size,
                        @RequestParam(name = "search", required = false) String search) {

        PaginationDTO pagination = questionService.list(search, page,size);
        List<HotTagDTO> hots = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("hots", hots);
        return "index";
    }
}
