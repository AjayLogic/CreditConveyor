package com.dyplom.controller;

import com.dyplom.creditCalculator.CreditCalculator;
import com.dyplom.entity.*;
import com.dyplom.mail.SmtpMailSender;
import com.dyplom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class ClientSideController {

    @Autowired
    CreditProductService creditProductService;
    @Autowired
    ScoringMapService scoringMapService;
    @Autowired
    ClientService clientService;
    @Autowired
    TestResultService testResultService;
    @Autowired
    ContractService contractService;
    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(value = {"/", "/bank"}, method = RequestMethod.GET)
    public ModelAndView showBankPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bank");
        return modelAndView;
    }

    @RequestMapping(value = "/credits", method = RequestMethod.GET)
    public ModelAndView showCredits() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("creditList", creditProductService.findAll());
        modelAndView.setViewName("credits");
        return modelAndView;
    }
    ScoringMap scoringMap;

    Long idCreditProduct;
    @RequestMapping(value = "credits/{id}/issue", method = RequestMethod.GET)
    public ModelAndView issueCredit(@PathVariable("id") Long idProductCredit) {
        idCreditProduct = idProductCredit;
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        scoringMap = scoringMapService.findByIsActive(true).get(0);
        List<Question> questionList = scoringMap.getQuestionList();

        modelAndView.addObject("questionList", questionList);
        modelAndView.addObject("client", client);

        modelAndView.setViewName("creditissue");
        return modelAndView;
    }

    @RequestMapping(value = "credits/send/issue", method = RequestMethod.POST)
    public ModelAndView sendIssue(@Valid @ModelAttribute("client") Client client, BindingResult bindingResult, @RequestParam(value="q") List<Long> casesId) {

        List<Case> clientCases = new ArrayList<>();
        TestResult testResult = new TestResult();
        int resultScores = 0;

        if (casesId != null) {
            for (Long id : casesId) {
                for (Iterator<Question> q = scoringMap.getQuestionList().iterator(); q.hasNext(); ) {
                    Question question = q.next();
                    for (Iterator<Case> c = question.getCaseList().iterator(); c.hasNext(); ) {
                        Case aCase = c.next();
                        if (id == aCase.getId()) {
                            clientCases.add(aCase);
                            resultScores += aCase.getScores();
                        }
                    }
                }
            }
        }


        ModelAndView modelAndView = new ModelAndView();
        Client clientExists;
        if (bindingResult.hasErrors()) {
            ScoringMap scoringMap = scoringMapService.findByIsActive(true).get(0);
            List<Question> questionList = scoringMap.getQuestionList();
            modelAndView.addObject("questionList", questionList);
            modelAndView.addObject("client", client);
            modelAndView.setViewName("creditissue");
            return modelAndView;
        } else {
            clientExists = clientService.findClientByPassportId(client.getPassportId());
            if (clientExists != null){
                client.setId(clientExists.getId());
                clientService.save(client);
            } else {
                clientService.save(client);
            }


            testResult.setResultScore(resultScores);
            testResult.setDateOfTesting(new Date());
            testResult.setScoringMap(scoringMap);
            testResult.setCaseList(clientCases);
            testResult.setClient(client);

            testResultService.save(testResult);

            if (client.getIncome().compareTo(client.getExpenditure()) == -1){
                try {
                    sendEmail(client.getEmail(), "Результат проверки заявки на кредит", "Здравствуйте, " + client.getName() + "! К сожалению Ваша заявка на кредит не была одобренна."
                                                                                                    + " Одной из причин в отказе является превышение доходов над расходами."
                                                                                                    + " Для уточнения подробностей Вы можете обратиться в наш банк по телефону.");
                } catch (MessagingException e) {
                    System.out.println("ошибка отправлния письма");
                }
            } else if(testResult.getResultScore() < scoringMap.getMinScores()){
                try {
                    sendEmail(client.getEmail(), "Результат проверки заявки на кредит", "Здравствуйте, " + client.getName() + "! К сожалению Ваша заявка на кредит не была одобренна."
                                                                                                    + " Для уточнения подробностей Вы можете обратиться в наш банк по телефону.");
                } catch (MessagingException e) {
                    System.out.println("ошибка отправлния письма");
                }
            } else {
                try {
                    sendEmail(client.getEmail(), "Результат проверки заявки на кредит", "Здравствуйте, " + client.getName() + "! Ваша заявка на кредит была одобренна."
                                                                                        + " В ближайщее время с Вами свяжется наш специалист для уточнения подробностей.");
                } catch (MessagingException e) {
                    System.out.println("ошибка отправлния письма");
                }



                CreditProduct creditProduct = creditProductService.findOne(idCreditProduct);
                Contract contract = new Contract();
                contract.setClient(client);
                contract.setCreditProduct(creditProduct);
                contractService.save(contract);
            }
        }


        modelAndView.addObject("client", new Client());
        modelAndView.addObject("successMessage", "Ваша заявка передана в банк. Вам на электронную почту выслано письмо. Вближайщее время с Вами свяжется наш специалист.");
        modelAndView.setViewName("creditissue");
        return modelAndView;
    }

    void sendEmail(String to, String subject, String body) throws MessagingException {
        smtpMailSender.send(to, subject, body);
    }

    CreditProduct clientCreditProduct;
    @RequestMapping(value = "credits/{id}/creditcalc", method = RequestMethod.GET)
    public ModelAndView showCreditCalculatorPage(@PathVariable("id") Long idProductCredit) {
        ModelAndView modelAndView = new ModelAndView();
        clientCreditProduct = creditProductService.findOne(idProductCredit);
        modelAndView.addObject("credit", clientCreditProduct);
        modelAndView.setViewName("creditcalc");
        return modelAndView;
    }

    @RequestMapping(value = "/credits/creditcalcresult", method = RequestMethod.POST)
    public ModelAndView creditCalResult(@ModelAttribute("credit") CreditProduct creditProduct,
                                        @RequestParam(value="clientSumm")BigDecimal clientSum, @RequestParam(value="clientMonth")int clientMonth){
        ModelAndView modelAndView  = new ModelAndView();

        CreditCalculator creditCalculator = new CreditCalculator(clientSum, clientCreditProduct.getPercentage() , clientMonth);//попробовать разобраться почему нет модели

        modelAndView.addObject("creditCalculator", creditCalculator);
        modelAndView.setViewName("creditcalcresult");

        return modelAndView;
    }
}
