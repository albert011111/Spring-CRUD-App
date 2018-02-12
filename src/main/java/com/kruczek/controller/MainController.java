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

@Controller
public class MainController {

    @Autowired
    private CustomerDao customerDao;

    @RequestMapping("/")
    public String mainMenu() {
        return "index";
    }

    @RequestMapping("/loginForm")
    public String loginForm() {
        return "login-form";
    }

    @RequestMapping("/logoutPage")
    public String logoutPage() {
        return "logout-page";
    }

    @RequestMapping("/list")
    public String customersList(Model model) {
        List<Customer> theCustomers = customerDao.getCustomers();
        model.addAttribute("customers", theCustomers);
        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showEmptyForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "form-customer";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "form-customer";
        }
        customerDao.addCustomer(customer);
        uploadCustomerImage(customer, request);
        return "redirect:/list";
    }

    @GetMapping("/deleteCustomer/customer")
    public String deleteCustomer(@RequestParam("customerId") long customerId, HttpServletRequest request) {
        customerDao.deleteCustomer(customerId);
        deleteCustomerImage(customerId, request);
        return "redirect:/list";
    }

    private void uploadCustomerImage(@ModelAttribute("customer") @Valid Customer customer, HttpServletRequest request) {
        MultipartFile multipartFile = customer.getMultipartFile();
        final String path = request.getSession().getServletContext().getRealPath("/");
        final String beforeTargetPath = org.apache.commons.lang3.StringUtils.substringBefore(path, "target");
        final String finalPath = beforeTargetPath + "\\src\\main\\webapp\\resources\\img\\" + customer.getId() + ".png";
        customer.setFilePath(finalPath);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(finalPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteCustomerImage(@RequestParam("customerId") long customerId, HttpServletRequest request) {
        final String path = request.getSession().getServletContext().getRealPath("/");
        final String beforeTargetPath = org.apache.commons.lang3.StringUtils.substringBefore(path, "target");
        final String finalPath = beforeTargetPath + "\\src\\main\\webapp\\resources\\img\\" + customerId + ".png";

        File file = new File(finalPath);
        if (Files.exists(file.toPath())) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/editCustomer/customer")
    public String editCustomer(@RequestParam("customerId") long customerId, Model model) {
        Customer theCustomer = customerDao.getCustomer(customerId);
        model.addAttribute("customer", theCustomer);
        return "form-customer";
    }

    @GetMapping("/customerInfo/customer/{customerId}")
    public String customerInfo(@PathVariable("customerId") long customerId, Model model) {
        Customer customer = customerDao.getCustomer(customerId);
        model.addAttribute("customer", customer);
        return "customer-info";
    }
}
