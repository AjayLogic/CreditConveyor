package com.dyplom.controller;

import com.dyplom.creditCalculator.CreditCalculator;
import com.dyplom.entity.BankWorker;
import com.dyplom.entity.Contract;
import com.dyplom.entity.Payment;
import com.dyplom.entity.TestResult;
import com.dyplom.service.ContractService;
import com.dyplom.service.PaymentService;
import com.dyplom.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CreditorController {
    @Autowired
    ContractService contractService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    TestResultService testResultService;
    @Autowired
    org.springframework.context.ApplicationContext appContext;

    @RequestMapping(value="creditor/contractlist", method = RequestMethod.GET)
    public ModelAndView getContractList(){
        List<Contract> emptyContracts = new ArrayList<Contract>();

        for (Contract contract : contractService.findAll()) {
            if (!contract.isChecked())
                emptyContracts.add(contract);
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("creditor/contractlist");
        modelAndView.addObject("contractlist", emptyContracts);
        return modelAndView;
    }

    @RequestMapping(value = "creditor/contractlist/{id}/edit", method = RequestMethod.GET)
    public ModelAndView showEditableContract(@PathVariable long id, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Contract contract = contractService.findById(id);
        modelAndView.addObject("contract", contract);
        httpSession.setAttribute("contract", contract);
        modelAndView.setViewName("creditor/editcontract");
        return modelAndView;
    }

    @RequestMapping(value = "/creditor/contractlist/savecontract", method = RequestMethod.POST)
    public ModelAndView saveContract(@ModelAttribute("contract") Contract contract, HttpSession httpSession,  @RequestParam(value="countOfMonth") int countOfMonth) {
        BankWorker bankWorker = (BankWorker) httpSession.getAttribute("creditor");
        contract.setBankWorker(bankWorker);

        Contract c = (Contract)httpSession.getAttribute("contract");
        contract.setCreditProduct(c.getCreditProduct());
        contract.setClient(c.getClient());

        contract.setStartDate(new Date());

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, countOfMonth);
        contract.setEndtDate(calendar.getTime());

        CreditCalculator creditCalculator = new CreditCalculator(contract.getSum(), c.getCreditProduct().getPercentage(), countOfMonth);

        for (Payment p : creditCalculator.getPayments()){
            p.setContract(contract);
            paymentService.save(p);
        }

        contract.setPaymentList(creditCalculator.getPayments());

        contractService.save(contract);

        return new ModelAndView("redirect:/creditor/contractlist");
    }

    @RequestMapping(value = "/creditor/contractlist/clientdetails", method = RequestMethod.GET)
    public ModelAndView showClientDetails(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Contract c = (Contract)httpSession.getAttribute("contract");

        List<TestResult> clientTestResults = c.getClient().getTestResultsList();

        modelAndView.addObject("client", c.getClient());
        modelAndView.setViewName("creditor/clientdetails");
        return modelAndView;
    }

    @RequestMapping(value = "/creditor/contractlist/creditdetails", method = RequestMethod.GET)
    public ModelAndView showCreditDetails(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Contract c = (Contract)httpSession.getAttribute("contract");

        modelAndView.addObject("credit", c.getCreditProduct());
        modelAndView.setViewName("creditor/creditdetails");
        return modelAndView;
    }



    @RequestMapping(value="creditor/fullcontractlist", method = RequestMethod.GET)
    public ModelAndView getFullContractList(){
        List<Contract> fullContracts = new ArrayList<Contract>();

        for (Contract contract : contractService.findAll()) {
            if (contract.isChecked())
                fullContracts.add(contract);
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("creditor/fullcontractlist");
        modelAndView.addObject("fullcontractlist", fullContracts);
        return modelAndView;
    }

    @RequestMapping(value = "creditor/fullcontractlist/{id}/details", method = RequestMethod.GET)
    public ModelAndView showFullContract(@PathVariable long id, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Contract contract = contractService.findById(id);
        modelAndView.addObject("fullcontract", contract);
        httpSession.setAttribute("contract", contract);
        modelAndView.setViewName("creditor/fullcontract");
        return modelAndView;
    }

    @RequestMapping(value="/creditor/fullcontractlist", method = RequestMethod.POST)
    public ModelAndView getSearchedFullContractList(@RequestParam(value="searchedId", required = true) Long searchedId){
        Contract searchedContract = null;
        ModelAndView modelAndView = new ModelAndView();
        if (searchedId != null) {
            searchedContract = contractService.findById(searchedId);


            modelAndView.setViewName("creditor/fullcontractlist");
            modelAndView.addObject("fullcontractlist", searchedContract);

        } else {
            modelAndView = getFullContractList();
        }
        return modelAndView;
    }

    @RequestMapping(path = "creditor/fullcontractlist/{id}/print", method = RequestMethod.GET)
    public ModelAndView report(@PathVariable long id) {
        List<Object> contracts = new ArrayList<>();
        contracts.add(contractService.findById(id));

        JasperReportsPdfView view = new JasperReportsPdfView();
        view.setUrl("classpath:contract.jrxml");
        view.setApplicationContext(appContext);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", contracts);

        return new ModelAndView(view, params);
    }
}
