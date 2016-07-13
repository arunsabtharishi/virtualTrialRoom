package com.epsilon.vtr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.service.EmployeeService;
import com.epsilon.vtr.vo.EmployeeVO;
import com.epsilon.vtr.vo.FileBucket;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    EmployeeService service;

    @Autowired
    MessageSource messageSource;

    /*
     * This method will list all existing employees.
     */
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listEmployees(ModelMap model) {

        List<Employee> employees = service.findAllEmployees();
        List<EmployeeVO> employeeVOList = new ArrayList<>();
        employees.forEach( employee  -> {
            EmployeeVO empVO = new EmployeeVO();
            empVO.setName(employee.getName());
            empVO.setJoiningDate(employee.getJoiningDate());
            empVO.setSalary(employee.getSalary());
            empVO.setSsn(employee.getSsn());
            if(employee.getPhoto()!=null) {
                FileBucket fileBucket = new FileBucket();
                empVO.setPhoto(fileBucket);
            }
            employeeVOList.add(empVO);
        });
        model.addAttribute("employees", employeeVOList);
        return "allemployees";
    }

    /*
     * This method will provide the medium to add a new employee.
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public String newEmployee(ModelMap model) {
        EmployeeVO empVO = new EmployeeVO();
        empVO.setPhoto(new FileBucket());
        model.addAttribute("employeeVO", empVO);
        model.addAttribute("edit", false);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST )
    public String saveEmployee(@Valid EmployeeVO employeeVO, BindingResult result,
            ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

        /*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
         * and applying it on field [ssn] of Model class [Employee].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        Employee employee = new Employee();
        if(!service.isEmployeeSsnUnique(employee.getId(), employeeVO.getSsn())){
            FieldError ssnError =new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{employeeVO.getSsn()}, Locale.getDefault()));
            result.addError(ssnError);
            return "registration";
        }
        employee.setName(employeeVO.getName());
        employee.setSsn(employeeVO.getSsn());
        employee.setSalary(employeeVO.getSalary());
        employee.setJoiningDate(employeeVO.getJoiningDate());
        if(employeeVO.getPhoto() !=null && !employeeVO.getPhoto().getFile().isEmpty()) {
            try {
                employee.setPhoto(employeeVO.getPhoto().getFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.saveEmployee(employee);

        model.addAttribute("success", "Employee " + employee.getName() + " registered successfully");
        return "success";
    }


    /*
     * This method will provide the medium to update an existing employee.
     */
    @RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.GET)
    public String editEmployee(@PathVariable String ssn, ModelMap model) {
        Employee employee = service.findEmployeeBySsn(ssn);

        EmployeeVO empVO = new EmployeeVO();
        empVO.setName(employee.getName());
        empVO.setJoiningDate(employee.getJoiningDate());
        empVO.setSalary(employee.getSalary());
        empVO.setSsn(employee.getSsn());
        if(employee.getPhoto()!=null) {
            FileBucket fileBucket = new FileBucket();
            empVO.setPhoto(fileBucket);
        }

        model.addAttribute("employeeVO", empVO);
        model.addAttribute("edit", true);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.POST)
    public String updateEmployee(@Valid EmployeeVO employeeVO, BindingResult result,
            ModelMap model, @PathVariable String ssn) {

        if (result.hasErrors()) {
            return "registration";
        }

        Employee employee = service.findEmployeeBySsn(ssn);

        if(!service.isEmployeeSsnUnique(employee.getId(), employee.getSsn())){
            FieldError ssnError =new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
            result.addError(ssnError);
            return "registration";
        }

        employee.setName(employeeVO.getName());
        employee.setSsn(employeeVO.getSsn());
        employee.setSalary(employeeVO.getSalary());
        employee.setJoiningDate(employeeVO.getJoiningDate());
        if(employeeVO.getPhoto() !=null && !employeeVO.getPhoto().getFile().isEmpty()) {
            try {
                employee.setPhoto(employeeVO.getPhoto().getFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.updateEmployee(employee);

        model.addAttribute("success", "Employee " + employee.getName()    + " updated successfully");
        return "success";
    }


    /*
     * This method will delete an employee by it's SSN value.
     */
    @RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable String ssn) {
        service.deleteEmployeeBySsn(ssn);
        return "redirect:/list";
    }

}
