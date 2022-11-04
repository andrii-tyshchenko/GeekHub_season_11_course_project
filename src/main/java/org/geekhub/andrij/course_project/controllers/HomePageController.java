package org.geekhub.andrij.course_project.controllers;

import org.geekhub.andrij.course_project.entities.CurrentUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomePageController {
    @GetMapping
    public ModelAndView getHomePage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String authority = ((CurrentUser) principal).getAuthority();

            if (authority.equals("ROLE_ADMIN")) {
                return new ModelAndView("redirect:/admin/cabinet");
            } else if (authority.equals("ROLE_USER")) {
                return new ModelAndView("redirect:/user/cabinet");
            } else {
                return new ModelAndView("redirect:/exit");
            }
        } else {
            return new ModelAndView("common/home");
        }
    }
}