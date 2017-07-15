package com.dyplom.controller;

import com.dyplom.entity.Bank;
import com.dyplom.entity.BankWorker;
import com.dyplom.service.BankWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private BankWorkerService bankWorkerService;

    @RequestMapping(value = "/admin/registrationformanager", method = RequestMethod.GET)
    public ModelAndView managerRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        BankWorker manager = new BankWorker();
        modelAndView.addObject("manager", manager);
        modelAndView.setViewName("/admin/registrationformanager");
        return modelAndView;
    }

    @RequestMapping(value = "/manager/registrationforcreditor", method = RequestMethod.GET)
    public ModelAndView creditorRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        BankWorker creditor = new BankWorker();
        modelAndView.addObject("creditor", creditor);
        modelAndView.setViewName("/manager/registrationforcreditor");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/registrationformanager", method = RequestMethod.POST)
    public ModelAndView createNewManager(@Valid  @ModelAttribute("manager") BankWorker manager, BindingResult bindingResult, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();

        BankWorker managerExists = bankWorkerService.findBankWorkerByEmail(manager.getEmail());
        if (managerExists != null) {
            bindingResult
                    .rejectValue("email", "error.manager",
                            "Пользователь с таким email уже существует");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/admin/registrationformanager");
        } else {
            bankWorkerService.saveBankWorkerHowManager(manager, (BankWorker) httpSession.getAttribute("admin"));
            modelAndView.addObject("successMessage", "Пользователь с правами менеджера успешно зарегистрирован");
            modelAndView.addObject("manager", new BankWorker());
            modelAndView.setViewName("/admin/registrationformanager");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/manager/registrationforcreditor", method = RequestMethod.POST)
    public ModelAndView createNewCreditor(@Valid  @ModelAttribute("creditor") BankWorker creditor, BindingResult bindingResult, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();

        BankWorker creditorExists = bankWorkerService.findBankWorkerByEmail(creditor.getEmail());
        if (creditorExists != null) {
            bindingResult
                    .rejectValue("email", "error.creditor",
                            "Пользователь с таким email уже существует");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/manager/registrationforcreditor");
        } else {
            bankWorkerService.saveBankWorkerHowCreditor(creditor, (BankWorker) httpSession.getAttribute("manager"));
            modelAndView.addObject("successMessage", "Пользователь с правами специалиста по кредитам успешно зарегистрирован");
            modelAndView.addObject("creditor", new BankWorker());
            modelAndView.setViewName("/manager/registrationforcreditor");

        }
        return modelAndView;
    }
}
