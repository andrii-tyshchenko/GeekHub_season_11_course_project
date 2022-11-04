package org.geekhub.andrij.course_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contacts")
public class ContactsController {
    @GetMapping
    public ModelAndView getContactsPage() {
        return new ModelAndView("common/contacts");
    }
}