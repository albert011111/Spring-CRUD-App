package com.kruczek.controller;

import com.kruczek.dao.CustomerDao;
import com.kruczek.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Patryk on 2017-09-28.
 */
@Controller
public class MainController {


    @Autowired
    private CustomerDao customerDao;

    @RequestMapping("/")
    public String mainMenu(Model theModel) {
//        System.out.println("In mainMenu()");
        return "index";
    }

    @RequestMapping("/loginForm")
    public String loginForm() {
//        System.out.println("In loginForm()");
        return "login-form";
    }

    @RequestMapping("/logoutPage")
    public String logoutPage() {
//        System.out.println("In logoutPage()");
        return "logout-page";
    }

    @RequestMapping("/list")
    public String customersList(Model model) {
        //get costumers from the customerService
//        System.out.println("In customerList() | customerDao=" + customerDao);
        List<Customer> theCustomers = customerDao.getCustomers();

        model.addAttribute("customers", theCustomers);
//        System.out.println(theCustomers);

        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showEmptyForm(Model model) {
        //get costumers from the customerService
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
//        System.out.println("In showEmptyForm() | Customer=" + customer);
        return "form-customer";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult result, HttpServletRequest request, Model model) {
        System.out.println("In saveCustomer() | customer=" + customer + " |result:" + result);


        if (result.hasErrors()) {
            return "form-customer";
        }


        customerDao.addCustomer(customer);
//        System.out.println("FALSE");

        MultipartFile multipartFile = customer.getMultipartFile();
        final String PATH = request.getSession().getServletContext().getRealPath("/");
        final String BEFORE_TARGET_PATH = org.apache.commons.lang3.StringUtils.substringBefore(PATH, "target");
        final String FINAL_PATH = BEFORE_TARGET_PATH + "\\src\\main\\webapp\\resources\\img\\" + customer.getId() + ".png";


        customer.setFilePath(FINAL_PATH);
        /*
        System.out.println("Customer FINAL_PATH: " + FINAL_PATH);
        System.out.println("Before target: " + BEFORE_TARGET_PATH);
        */

        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(FINAL_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return "redirect:/list";
    }

    @GetMapping("/deleteCustomer/customer")
    public String deleteCustomer(@RequestParam("customerId") long theId, HttpServletRequest request) {
//        System.out.println("In deleteCustomer() | customerId=" + theId);
        customerDao.deleteCustomer(theId);


        final String PATH = request.getSession().getServletContext().getRealPath("/");
        final String BEFORE_TARGET_PATH = org.apache.commons.lang3.StringUtils.substringBefore(PATH, "target");
        final String FINAL_PATH = BEFORE_TARGET_PATH + "\\src\\main\\webapp\\resources\\img\\" + theId + ".png";

        File file = new File(FINAL_PATH);

        if (Files.exists(file.toPath())) {

            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/list";
    }

    @GetMapping("/editCustomer/customer")
    public String editCustomer(@RequestParam("customerId") long theId, Model theModel) {

        //GET CUSTOMER FROM DB
        Customer theCustomer = customerDao.getCustomer(theId);
//        System.out.println("In editCustomer() | customer=" + theCustomer);
        //SET CUSTOMER AS A MODEL ATTRIBUTE TO PRE POPULATE FORM
        theModel.addAttribute("customer", theCustomer);
        //SEND OVER TO OUR FORM
        return "form-customer";
    }

    @GetMapping("/customerInfo/customer/{theId}")
    public String customerInfo(@PathVariable("theId") long id, Model theModel) {
//        System.out.println("In customerInfo | customerId: " + id);
        Customer customer = customerDao.getCustomer(id);
        theModel.addAttribute("customer", customer);
        return "customer-info";
    }
}
