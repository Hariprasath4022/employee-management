package net.javaguides.springboot.controller;

import java.awt.Dialog.ModalExclusionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.validation.Valid;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.EmployeeService;
import net.javaguides.springboot.service.RecordAlreadyExistsException;

@Controller
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@InitBinder
	public void initBlinder(WebDataBinder dataBinder)
	{
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@Autowired
	private EmployeeService employeeService;

	// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model)
	{
		model.addAttribute("listEmployees", employeeService.getAllEmployees());
		return "index";
	}

	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model)
	{
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@Valid Employee employee, BindingResult bindingResult, Model model)
	{
		// save employee to database
		if (bindingResult.hasErrors()) {
			return "new_employee";
		}

		log.info(">> employee : {}", employee.toString());

		try {
			employeeService.saveEmployee(employee);
			return "redirect:/";
		} catch (RecordAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			return "new_employee"; // Redirect with error message as query parameter
		}
	}

	@GetMapping("/saveEmployee")
	public String saveEmployeeR(@Valid Employee employee, BindingResult bindingResult, Model model) {
		// save employee to database
		if (bindingResult.hasErrors()) {
			return "new_employee";
		}

		log.info(">> employee : {}", employee.toString());

		try {
			employeeService.saveEmployee(employee);
			return "redirect:/";
		} catch (RecordAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			return "new_employee"; // Redirect with error message as query parameter
		}
	}

	@PostMapping("/saveUpEmployee/{id}")
	public String saveupEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult,
			Model model)
	{
		// save employee to database
		if (bindingResult.hasErrors()) {
			return "update_employee";
		}

		log.info(">> employee : {}", employee.toString());

//		 if (!employeeService.isAnyFieldUpdated(employee))
//		 {
//	        return "redirect:/"; // No fields were updated, redirect to the employee list page
//	     }

		try {
			employeeService.saveupEmployee(employee);
			return "redirect:/";
		} catch (RecordAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			return "update_employee"; // Redirect with error message as query parameter
		}

	}

	@GetMapping("/saveUpEmployee/{id}")
	public String showFormForUpdateR(@PathVariable(value = "id") long id, Model model)
	{

		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model)
	{

		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}

	
	 
	  
	  //@RequestMapping(value = "/deleteconfirm/{id}", method = RequestMethod.POST)
	@GetMapping("/deleteconfirm/{id}")
	public String showDeleteConfirmationModal(@PathVariable Long id, Model model)
	{
		model.addAttribute("employee", employeeService.getEmployeeById(id)); 
		return "deleteconfirmation"; 
	}
	 

	 //@PostMapping("/delete/__${id}__")
	  //@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@PostMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable Long id,RedirectAttributes redirectAttributes, Model model)
	{
	  this.employeeService.deleteEmployeeById(id); 
	  redirectAttributes.addFlashAttribute("deleteSuccess", true);
	  return "redirect:/";
	}
	 

	/*
	 * @RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.GET)
	 * public String deleteEmployee(@PathVariable(value = "id") long id,
	 * RedirectAttributes redirectAttributes, Model model) {
	 * 
	 * // call delete employee method
	 * 
	 * this.employeeService.deleteEmployeeById(id);
	 * redirectAttributes.addFlashAttribute("deleteSuccess", true); return
	 * "redirect:/"; }
	 */
}
