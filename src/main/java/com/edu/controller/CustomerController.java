package com.edu.controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.model.Customer;
import com.edu.repository.CustomerRespository;
import com.edu.service.CustomerService;
import com.edu.service.Impl.CustomerServiceImpl;
import com.edu.utils.Constant;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

	@InitBinder
	public void initBuilder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@Autowired
	private CustomerService customerService;

	private final CustomerServiceImpl customerServiceImpl;

	public final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public CustomerController(CustomerServiceImpl customerServiceImpl, BCryptPasswordEncoder passwordEncoder) {
		this.customerServiceImpl = customerServiceImpl;
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	private CustomerRespository customerRepo;

	@GetMapping("/list")
	public String showList(Model model ) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<Customer> page = customerService.listCuss(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		List<Customer> listCus = page.getContent();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("listCus", listCus);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-customer";
	}

	@GetMapping("/create")
	public String showFormCreate(Model model) {
		model.addAttribute("customer", new Customer());
		return "create-customer";
	}

	@PostMapping("/save")
	public String save(@Valid Customer customer, BindingResult result, HttpServletRequest request,
			RedirectAttributes redirect, Model model) {
		if (customer.getDateOfBirth() != null) {
			Date dob = customer.getDateOfBirth();
			Date date = new Date();
			if (dob.after(date)) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday Not Found"));

			}
			if (date.getYear() - dob.getYear() > 120) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday greater 0 and little 120 "));
			}
		}

		if (customer.getUsername() != null) {
			if (customerServiceImpl.userExists(customer.getUsername())) {
				result.addError(new FieldError("customer", "username", "Username already in use!"));
			}
		}
		if (customer.getEmail() != null) {
			if (customerServiceImpl.userEmail(customer.getEmail())) {
				result.addError(new FieldError("customer", "email", "Email already in use!"));
			}
		}
		if (customer.getPhone() != null) {
			if (customerServiceImpl.userExistPhone(customer.getPhone())) {
				result.addError(new FieldError("customer", "phone", "Phone already in use!"));
			}
		}
		if (customer.getPassword() != null && customer.getConfirmPassword() != null) {
			if (!customer.getPassword().equals(customer.getConfirmPassword())) {
				result.addError(new FieldError("customer", "confirmPassword", "Password must match"));
			}
		}
		if (customer.getIdentityCard() != null) {
			if (customerServiceImpl.userCard(customer.getIdentityCard())) {
				result.addError(new FieldError("customer", "identityCard", "Identity Card already in use!"));
			}
		}
		if (customer.getCaptcha() != null) {
			if (!customer.getCaptcha().equals(request.getSession().getAttribute("captcha"))) {
				result.addError(new FieldError("customer", "captcha", "Captcha must match"));
			}
		}
		if (result.hasErrors()) {
			return "create-customer";
		}
		redirect.addFlashAttribute("message", "Success! your registration is complete");
		String password = customer.getPassword();
		customer.setPassword(passwordEncoder.encode(password));
		customer.setStatus(1);
		customerServiceImpl.saveCustomer(customer);
		return "redirect:/admin/customer/list";
	}

	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid Customer customer, BindingResult result, HttpServletRequest request,
			RedirectAttributes redirect, Model model) {
		if (customer.getDateOfBirth() != null) {
			Date dob = customer.getDateOfBirth();
			Date date = new Date();
			if (dob.after(date)) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday Not Found"));

			}
			if (date.getYear() - dob.getYear() > 120) {
				result.addError(new FieldError("customer", "dateOfBirth", "Birthday greater 0 and little 120 "));
			}
		}

		if (customer.getPassword() != null && customer.getConfirmPassword() != null) {
			if (!customer.getPassword().equals(customer.getConfirmPassword())) {
				result.addError(new FieldError("customer", "confirmPassword", "Password must match"));
			}
		}
		if (customer.getCaptcha() != null) {
			if (!customer.getCaptcha().equals(request.getSession().getAttribute("captcha"))) {
				result.addError(new FieldError("customer", "captcha", "Captcha must match"));
			}
		}
		if (result.hasErrors()) {
			return "update-customer";
		}
		redirect.addFlashAttribute("message", "Success! update account  is complete");
		String password = customer.getPassword();
		customer.setPassword(passwordEncoder.encode(password));
		customer.setStatus(1);
		customerServiceImpl.saveCustomer(customer);
		return "redirect:/admin/customer/list";
	}

	@GetMapping("/update/{id}")
	public String ShowFormUpdateEmpl(@PathVariable(name = "id") String id, Model model) {
		Customer customer = customerService.findById(id);
		model.addAttribute("customer", customer);
		return "update-customer";
	}

	@GetMapping("/delete/{id}")

	public String deleteBrand(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		;
		customerService.deleteById(id);
		return "redirect:/admin/customer/list";
	}

	@PostMapping(value = "/delete")
	public String deleteCuss(@RequestParam(name = "idChecked", required = false) List<String> ids, Model model,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", "No selected to delete");
		} else {
			for (String idStr : ids) {
				customerService.deleteById(idStr);
			}
			redirectAttributes.addFlashAttribute("success", "Delete Success");
		}
		return "redirect:/admin/customer/list";

	}

}
