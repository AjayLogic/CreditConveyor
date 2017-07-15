package com.dyplom.controller;

import javax.servlet.http.*;

import com.dyplom.entity.BankWorker;
import com.dyplom.service.BankWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @Autowired
    private BankWorkerService bankWorkerService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/403")
    public ModelAndView error403(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("403");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView homeAdmin(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BankWorker admin = bankWorkerService.findBankWorkerByEmail(auth.getName());
        httpSession.setAttribute("admin", admin);
        modelAndView.addObject("userName", "Добро пожаловать " + admin.getName() + " " + admin.getPhone() + " (" + admin.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Текущая страница доступна только пользователям с правами администратора");
        modelAndView.setViewName("/admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "/creditor/home", method = RequestMethod.GET)
    public ModelAndView homeCreditor(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BankWorker creditor = bankWorkerService.findBankWorkerByEmail(auth.getName());
        httpSession.setAttribute("creditor", creditor);
        modelAndView.addObject("userName", "Добро пожаловать " + creditor.getName() + " " + creditor.getPhone() + " (" + creditor.getEmail() + ")");
        modelAndView.addObject("creditorMessage", "Текущая страница доступна только пользователям с правами специалиста по кредитам");
        modelAndView.setViewName("/creditor/home");
        return modelAndView;
    }

    @RequestMapping(value = "/manager/home", method = RequestMethod.GET)
    public ModelAndView homeManager(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BankWorker manager = bankWorkerService.findBankWorkerByEmail(auth.getName());
        httpSession.setAttribute("manager", manager);
        modelAndView.addObject("userName", "Добро пожаловать " + manager.getName() + " " + manager.getPhone() + " (" + manager.getEmail() + ")");
        modelAndView.addObject("managerMessage", "Текущая страница доступна только пользователям с правами менеджера");
        modelAndView.setViewName("/manager/home");
        return modelAndView;
    }

    @RequestMapping("/redirect")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/home";
        } else if (request.isUserInRole("ROLE_CREDITOR")) {
            return "redirect:/creditor/home";
        } else if (request.isUserInRole("ROLE_MANAGER")) {
            return "redirect:/manager/home";
        }
        return "redirect:/home/";
    }
}
