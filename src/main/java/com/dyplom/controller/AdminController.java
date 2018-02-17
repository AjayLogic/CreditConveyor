package com.dyplom.controller;

import com.dyplom.entity.Bank;
import com.dyplom.entity.BankWorker;
import com.dyplom.service.BankWorkerService;
import com.dyplom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    BankWorkerService bankWorkerService;
    @Autowired
    RoleService roleService;

    @RequestMapping(value="admin/managerlist", method = RequestMethod.GET)
    public ModelAndView getManagerList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/managerlist");
        modelAndView.addObject("managerList", bankWorkerService.findAllByRole(roleService.findByRole("ROLE_MANAGER")));
        return modelAndView;
    }

    @RequestMapping(value="admin/managerlist/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteManager(@PathVariable long id){
        bankWorkerService.delete(id);
        return new ModelAndView("redirect:/admin/managerlist");
    }

    @RequestMapping(value = "admin/managerlist/{id}/edit", method = RequestMethod.GET)
    public String showEditableManager(@PathVariable long id, Model model) {
        BankWorker manager = bankWorkerService.findOne(id);
        model.addAttribute("manager", manager);
        return "admin/editmanager";
    }

    @RequestMapping(value = "/admin/managerlist/updatemanager", method = RequestMethod.POST)
    public ModelAndView updateManager(@ModelAttribute("manager") BankWorker manager, HttpSession httpSession) {
        bankWorkerService.saveBankWorkerHowManager(manager, (BankWorker) httpSession.getAttribute("admin"));
        return new ModelAndView("redirect:/admin/managerlist");
    }
}
