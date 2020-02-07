package tfire.springdemo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tfire.springdemo.exception.CustomizeErrorCode;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);

        if (status.is4xxClientError()) {
            model.addAttribute("message", CustomizeErrorCode.PAGE_NOT_EXIST.getMessage());
        } else if (status.is5xxServerError()) {
            model.addAttribute("message", CustomizeErrorCode.SERVER_ERROR.getMessage());
        } else {
            model.addAttribute("message", "unkown error");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statuscode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statuscode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statuscode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


}
