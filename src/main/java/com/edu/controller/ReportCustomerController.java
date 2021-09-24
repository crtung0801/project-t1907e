package com.edu.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.model.Customer;
import com.edu.model.InjectionResult;
import com.edu.service.CustomerService;
import com.edu.service.ReportService;
import com.edu.service.ResultService;

@Controller
@RequestMapping("/admin")
public class ReportCustomerController {

	@Autowired
	private ReportService reportService;

	private Date fDate;
	private Date tDate;
	private String addr;
	private String fName;

	@GetMapping("/report-customer")
	public String listCustomer(Model model) {
		return findPaginated(1, model);
	}

	@GetMapping(value = "/report-customer/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
		try {
			int pageSize = 5;
			Page<Customer> page = reportService.findPaginatedCus(pageNo, pageSize);
			List<Customer> listCustomer = page.getContent();
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listCustomer", listCustomer);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "report-customer-list";
	}

	@GetMapping("/customer-filter")
	public String filterCustomer(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
			@RequestParam(required = false) String fullName, @RequestParam(required = false) String address,
			Model model, RedirectAttributes redirect) {
		addr = address;
		fDate = fromDate;
		fName = fullName;
		tDate = toDate;
		return searchPaginated(1, model, fromDate, toDate, fullName, address);
	}

	@GetMapping(value = "/customer-filter/{pageNo}")
	public String searchPaginated(@PathVariable(value = "pageNo") int pageNo, Model model, Date fromDate, Date toDate,
			String fullName, String address) {
		address = addr;
		fullName = fName;
		fromDate = fDate;
		toDate = tDate;
		try {
			int pageSize = 5;
			Page<Customer> page = reportService.searchCustomerReport(pageNo, pageSize, fDate, tDate, fName, addr);
			List<Customer> listCustomer = page.getContent();

			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());

			model.addAttribute("listCustomer", listCustomer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "report-customer-list";
	}

	@GetMapping("/chart-customer")
	public String goodbye(@RequestParam(name = "year", defaultValue = "2021") Integer year, Model model) {
		Set<Integer> listYear = new HashSet<>();
		listYear.add(2021);
		List<InjectionResult> findAll = reportService.findAll();
		for (InjectionResult injectionResult : findAll) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(injectionResult.getInjectionDate());
			listYear.add(calendar.get(Calendar.YEAR));
			calendar.setTime(injectionResult.getNextInjectionDate());
			listYear.add(calendar.get(Calendar.YEAR));
			model.addAttribute("listYear", listYear);
		}
		String label[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"Octorber", "November", "December" };
		List<Integer> result = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			result.add(reportService.countCustomer(year, i));
		}
		model.addAttribute("label", label);
		model.addAttribute("data", result);
		model.addAttribute("year", year);
		return "report-customer-chart";
	}
}
