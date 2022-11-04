package org.geekhub.andrij.course_project.controllers.errors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice
public class HttpErrorController implements ErrorController {
    @GetMapping("/error")
    public ModelAndView handleHttpError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ModelAndView("common/errors/404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ModelAndView("common/errors/500");
            } else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                return new ModelAndView("common/errors/503");
            }
        }

        return new ModelAndView("common/errors/error");
    }

    @GetMapping("/access_denied")
    public ModelAndView get403Page() {
        return new ModelAndView("common/errors/403");
    }
}