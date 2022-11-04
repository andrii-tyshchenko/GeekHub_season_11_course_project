package org.geekhub.andrij.course_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/about")
public class AboutController {
    @GetMapping
    public ModelAndView getAboutPage() {
        return new ModelAndView("common/about");
    }
}