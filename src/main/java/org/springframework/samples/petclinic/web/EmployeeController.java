package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

	private static final String VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM = "employees/createOrUpdateEmployeeForm";
	
	private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService, UserService userService, AuthoritiesService authoritiesService) {
		this.employeeService = employeeService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/employees/new")
	public String initCreationForm(Map<String, Object> model) {
		Employee employee = new Employee();
		model.put("employee", employee);
		return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/employees/new")
	public String processCreationForm(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating client, user and authorities
			this.employeeService.saveEmployee(employee);
			
			return "redirect:/employees/" + employee.getId();
		}
	}

	@GetMapping(value = "/employees/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("employee", new Employee());
		return "employees/findEmployees";
	}

	@GetMapping(value = "/employees")
	public String processFindForm(Employee employee, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (employee.getLastName() == null) {
			employee.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Employee> results = this.employeeService.findEmployeeByLastName(employee.getLastName());
		if (results.isEmpty()) {
			// no clients found
			result.rejectValue("lastName", "notFound", "not found");
			return "employees/findEmployees";
		}
		else if (results.size() == 1) {
			// 1 client found
			employee = results.iterator().next();
			return "redirect:/employees/" + employee.getId();
		}
		else {
			// multiple clients found
			model.put("selections", results);
			return "employees/employeesList";
		}
	}

	@GetMapping(value = "/employees/{employeeId}/edit")
	public String initUpdateEmployeeForm(@PathVariable("employeeId") int employeeId, Model model) {
		Employee employee = this.employeeService.findEmployeeById(employeeId);
		model.addAttribute(employee);
		return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/employees/{employeeId}/edit")
	public String processUpdateEmployeeForm(@Valid Employee employee, BindingResult result,
			@PathVariable("employeeId") int employeeId) {
		if (result.hasErrors()) {
			return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
		}
		else {
			employee.setId(employeeId);
			this.employeeService.saveEmployee(employee);
			return "redirect:/employees/{employeeId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/employees/{employeeId}")
	public ModelAndView showEmployee(@PathVariable("employeeId") int employeeId) {
		ModelAndView mav = new ModelAndView("employees/employeesDetails");
		mav.addObject(this.employeeService.findEmployeeById(employeeId));
		return mav;
	}
	
	@GetMapping(value = "/personalDataEmployee/{name}")
	public ModelAndView processInitPersonalDataForm(@PathVariable("name") String name, Model model) {
		ModelAndView mav = new ModelAndView("employees/employeesDetails");
		mav.addObject(this.employeeService.findEmployeeByUserName(name));
		return mav;
	}
	
	@GetMapping(value = "/personalDataEmployee/{employeeId}/edit")
	public String initUpdatepersonalDataForm(@PathVariable("employeeId") int employeeId, Model model) {
		Employee employee= this.employeeService.findEmployeeById(employeeId);
		model.addAttribute(employee);
		return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/personalDataEmployee/{employeeId}/edit")
	public String processUpdatePersonalDataForm(@Valid Employee employee, BindingResult result, @PathVariable("employeeId") int employeeId) {
		if (result.hasErrors()) {
			return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
		}
		else {
			employee.setId(employeeId);
			this.employeeService.saveEmployee(employee);
			SecurityContextHolder.clearContext();
			return "redirect:/";
		}
	}
}
