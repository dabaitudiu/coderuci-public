package tfire.springdemo.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tfire.springdemo.dto.ResultDTO;
import tfire.springdemo.exception.CustomizeErrorCode;
import tfire.springdemo.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if (contentType != null && contentType.equals("application/json")) {
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException) {
                resultDTO =  ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SERVER_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {

            }
            return null;
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SERVER_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
