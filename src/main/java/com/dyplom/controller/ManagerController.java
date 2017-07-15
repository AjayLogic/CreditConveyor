package com.dyplom.controller;

import com.dyplom.entity.*;
import com.dyplom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ManagerController {

    @Autowired
    BankWorkerService bankWorkerService;
    @Autowired
    RoleService roleService;
    @Autowired
    CreditProductService creditProductService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CaseService caseService;
    @Autowired
    ScoringMapService scoringMapService;
    @Autowired
    ContractService contractService;

    /*
    * working with credtitors
    */

    @RequestMapping(value="manager/creditorlist", method = RequestMethod.GET)
    public ModelAndView getCreditorList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/creditorlist");
        modelAndView.addObject("creditorList", bankWorkerService.findAllByRole(roleService.findByRole("ROLE_CREDITOR")));
        return modelAndView;
    }

    @RequestMapping(value="manager/creditorlist/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteManager(@PathVariable long id){
        bankWorkerService.delete(id);
        return new ModelAndView("redirect:/manager/creditorlist");
    }

    @RequestMapping(value = "manager/creditorlist/{id}/edit", method = RequestMethod.GET)
    public String showEditableManager(@PathVariable long id, Model model) {
        BankWorker creditor = bankWorkerService.findOne(id);
        model.addAttribute("creditor", creditor);
        return "manager/editcreditor";
    }

    @RequestMapping(value = "/manager/creditorlist/updatecreditor", method = RequestMethod.POST)
    public ModelAndView updateManager(@ModelAttribute("creditor") BankWorker creditor, HttpSession httpSession) {
        bankWorkerService.saveBankWorkerHowCreditor(creditor, (BankWorker) httpSession.getAttribute("manager"));
        return new ModelAndView("redirect:/manager/creditorlist");
    }

     /*
    * working with credit products
    */

    @RequestMapping(value="manager/creditlist", method = RequestMethod.GET)
    public ModelAndView getCreditList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/creditlist");
        modelAndView.addObject("creditList", creditProductService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/manager/addcredit", method = RequestMethod.GET)
    public ModelAndView creditAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        CreditProduct creditProduct = new CreditProduct();
        modelAndView.addObject("credit", creditProduct);
        modelAndView.setViewName("/manager/addcredit");
        return modelAndView;
    }

    @RequestMapping(value = "/manager/addcredit", method = RequestMethod.POST)
    public ModelAndView addCredit(@ModelAttribute("credit") CreditProduct creditProduct, BindingResult bindingResult, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();

        BankWorker creator = (BankWorker) httpSession.getAttribute("manager");

        CreditProduct managerExists = creditProductService.findByName(creditProduct.getName());
        if (managerExists != null) {
            bindingResult
                    .rejectValue("name", "error.credit",
                            "Кредитный продукт с таким именем уже существует");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/manager/addcredit");
        } else {
            creditProductService.save(creditProduct, creator);
            modelAndView.addObject("successMessage", "Кредитный продукт успешно зарегистрирован");
            modelAndView.addObject("credit", new CreditProduct());
            modelAndView.setViewName("/manager/addcredit");

        }

        return modelAndView;
    }

    @RequestMapping(value = "manager/creditlist/{id}/edit", method = RequestMethod.GET)
    public String showEditableCreditProduct(@PathVariable long id, Model model) {
        CreditProduct creditProduct = creditProductService.findOne(id);
        model.addAttribute("credit", creditProduct);
        return "manager/editcredit";
    }

    @RequestMapping(value = "/manager/creditlist/updatecredit", method = RequestMethod.POST)
    public ModelAndView updateCreditProduct(@ModelAttribute("credit") CreditProduct creditProduct, HttpSession httpSession) {
        creditProductService.save(creditProduct, (BankWorker) httpSession.getAttribute("manager"));
        return new ModelAndView("redirect:/manager/creditlist");
    }

    @RequestMapping(value="manager/creditlist/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteCreditProduct(@PathVariable long id){
        creditProductService.delete(id);
        return new ModelAndView("redirect:/manager/creditlist");
    }

    /*
    * working with questions and cases
    */


    public List<Case> createCaseList(List<String> caseValueList, List<String> caseScoreList){
        List<Case> caseList = new ArrayList();
        for (int i = 0; i < caseValueList.size(); i++) {
            if (caseValueList.get(i) != "") {
                Case aCase = new Case();
                aCase.setCaseItem(caseValueList.get(i));
                aCase.setScores(Integer.parseInt(caseScoreList.get(i)));
                caseList.add(aCase);
            }
        }

        return caseList;
    }

    @RequestMapping(value="manager/questionlist", method = RequestMethod.GET)
    public ModelAndView getQuestiontList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/questionlist");
        modelAndView.addObject("questionList", questionService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/manager/addquestion", method = RequestMethod.GET)
    public ModelAndView questionAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        Question question = new Question();
        modelAndView.addObject("question", question);
        modelAndView.setViewName("/manager/addquestion");
        return modelAndView;
    }

    @RequestMapping(value = "/manager/addquestion", method = RequestMethod.POST)
    public ModelAndView addQuestion(@ModelAttribute("question") Question question, BindingResult bindingResult,
                                    @RequestParam(value="caseValue") List<String> caseValueList,
                                    @RequestParam(value="caseScore") List<String> caseScoreList) {
        ModelAndView modelAndView = new ModelAndView();

        List<Case> caseList = createCaseList(caseValueList, caseScoreList);

        Question managerExists = questionService.findByQuestionName(question.getQuestionName());
        if (managerExists != null) {
            bindingResult
                    .rejectValue("name", "error.credit",
                            "Такой вопрос уже существует");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/manager/addquestion");
        } else {
            questionService.save(question);

            for (Case caseItem: caseList) {
                caseService.saveFromQuestion(caseItem, question);
            }

            modelAndView.addObject("successMessage", "Вопрос успешно добвлен");
            modelAndView.addObject("question", new Question());
            modelAndView.setViewName("/manager/addquestion");

        }

        return modelAndView;
    }

    @RequestMapping(value = "manager/questionlist/{id}/edit", method = RequestMethod.GET)
    public ModelAndView showEditableQuestion(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.findOne(id);
        modelAndView.setViewName("manager/editquestion");
        modelAndView.addObject("question", question);
        return modelAndView;
    }

    @RequestMapping(value = "/manager/questionlist/updatequestion", method = RequestMethod.POST)
    public ModelAndView updateQuestion(@ModelAttribute("question") Question question,  @RequestParam(value="caseValue") List<String> caseValueList,
                                       @RequestParam(value="caseScore") List<String> caseScoreList) {
        List<Case> caseList = createCaseList(caseValueList, caseScoreList);

        for (Case caseItem: question.getCaseList()){ //установка вопросов в уже существующих вариантах
            caseItem.setQuestion(question);
        }

        for (Case caseItem: caseList) { //установка вопросов в новосозданных вариантах
            caseItem.setQuestion(question);
            question.getCaseList().add(caseItem);
        }

        questionService.save(question);



        return new ModelAndView("redirect:/manager/questionlist");
    }

    @RequestMapping(value="manager/questionlist/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteQuestion(@PathVariable long id){
        questionService.delete(id);
        return new ModelAndView("redirect:/manager/questionlist");
    }

    @RequestMapping(value="editquestion/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteCaseItem(@PathVariable long id, HttpServletRequest request){
        caseService.delete(id);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    /*
    * working with scoring map
    */

    @RequestMapping(value="manager/scoringmapconfig", method = RequestMethod.GET)
    public ModelAndView getScoringMapList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("scoringMapList", scoringMapService.findAll());

        int count = scoringMapService.findByIsActive(true).size();

        if (count == 0){
            modelAndView.addObject("managerMessage", "Установите одну из скоринговых карт в состояние активной! Во избежании некорректной работы программы, оставляйте только одну карту активной.");
        }

        modelAndView.setViewName("manager/scoringmapconfig");
        return modelAndView;
    }

    List<Question> questionsForAdd;
    @RequestMapping(value = "/manager/addscoringmap", method = RequestMethod.GET)
    public ModelAndView scoringMapAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        questionsForAdd = questionService.findAll();
        ScoringMap scoringMap = new ScoringMap();
        modelAndView.addObject("scoringMap", scoringMap);
        modelAndView.addObject("questionList", questionsForAdd);
        modelAndView.setViewName("/manager/addscoringmap");
        return modelAndView;
    }

    @RequestMapping(value = "/manager/addscoringmap", method = RequestMethod.POST)
    public ModelAndView addScoringMap(@ModelAttribute("scoringMap") ScoringMap scoringMap, @RequestParam(value="items") List<String> items) {
        ModelAndView modelAndView = new ModelAndView();

        List<Question> questionsToSM = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            int id = Integer.parseInt(items.get(i));
            for (Question question : questionsForAdd) {
                if (question.getId() == id){
                    questionsToSM.add(question);
                }
            }
        }

        scoringMap.setQuestionList(questionsToSM);
        scoringMapService.save(scoringMap);

        modelAndView.addObject("successMessage", "Скориговая карта успешно создана");
        modelAndView.addObject("scoringMap", new ScoringMap());
        modelAndView.setViewName("/manager/addscoringmap");

        return modelAndView;
    }

    List<Question> questionsForEdit;
    @RequestMapping(value = "/manager/scoringmapconfig/{id}/edit", method = RequestMethod.GET)
    public ModelAndView showEditableScoringMap(@PathVariable long id, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        ScoringMap scoringMap = scoringMapService.findOne(id);
        questionsForEdit = questionService.findAll();

        int count = scoringMapService.findByIsActive(true).size();

        if (count == 1){
            modelAndView.addObject("activeMessage", "Активная скоринговая карта уже существует! Во избежании некорректной работы программы, оставляйте только одну карту активной.");
        }

        for (Question q : scoringMap.getQuestionList()) {
            for (Iterator<Question> q1 = questionsForEdit.iterator(); q1.hasNext(); ) {
                if (q1.next().getId() == q.getId()) {
                    q1.remove();
                }
            }
        }

        modelAndView.setViewName("manager/editscoringmap");
        modelAndView.addObject("scoringMap", scoringMap);
        httpSession.setAttribute("scoringMap", scoringMap);
        httpSession.setAttribute("questions", scoringMap.getQuestionList());
        modelAndView.addObject("questions", scoringMap.getQuestionList());
        modelAndView.addObject("questionList", questionsForEdit);


        return modelAndView;
    }

    @RequestMapping(value = "/manager/scoringmapconfig/updatescoringmap", method = RequestMethod.POST)
    public ModelAndView updateScoringMap(@ModelAttribute("scoringMap") ScoringMap scoringMap, BindingResult bindingResult,
                                         HttpServletRequest request, @RequestParam(value="items", required = false) List<String> items,
                                         HttpSession httpSession,  @RequestParam(value="isActive", required = false) boolean[] isActive){

        if (isActive != null) {
            scoringMap.setActive(isActive[0]);
        }

        List<Question> questionsToSM = (List<Question>) httpSession.getAttribute("questions");

        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                int id = Integer.parseInt(items.get(i));
                for (Question question : questionsForEdit) {
                    if (question.getId() == id) {
                        questionsToSM.add(question);
                    }
                }
            }
        }

        scoringMap.setQuestionList(questionsToSM);
        scoringMapService.save(scoringMap);

        return new ModelAndView("redirect:/manager/scoringmapconfig");
    }


    @RequestMapping(value="editscoringmap/{id}/deletequestion", method = RequestMethod.GET)
    public ModelAndView deleteQuestionFromScoringMap(@PathVariable long id, HttpServletRequest request, HttpSession httpSession){
        ScoringMap scoringMap = (ScoringMap) httpSession.getAttribute("scoringMap");
        Question question = questionService.findOne(id);

        for (Iterator<Question> q = scoringMap.getQuestionList().iterator(); q.hasNext(); ) {
            if (q.next().getId() == question.getId())
                q.remove();
        }

        scoringMapService.save(scoringMap);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value="/manager/scoringmapconfig/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteScoringMap(@PathVariable long id){
        scoringMapService.delete(id);
        return new ModelAndView("redirect:/manager/scoringmapconfig");
    }

    @RequestMapping(value="manager/report", method = RequestMethod.POST)
    public ModelAndView getReport(@RequestParam(value="startDate") String startDate, @RequestParam(value="endDate") String endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date sD= format.parse(startDate);
        Date eD= format.parse(endDate);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/report");
        modelAndView.addObject("report", contractService.findByDatesBetween(sD, eD));
        return modelAndView;
    }
}
