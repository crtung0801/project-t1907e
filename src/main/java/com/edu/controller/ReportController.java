package com.edu.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.dto.ReportCustomerDto;
import com.edu.dto.VaccineTypeIdNameDto;
import com.edu.model.Customer;
import com.edu.model.InjectionResult;
import com.edu.model.Vaccine;
import com.edu.model.VaccineType;
import com.edu.repository.ReportVaccineRepository;
import com.edu.service.CustomerService;
import com.edu.service.ReportService;
import com.edu.service.VaccineService;
import com.edu.service.VaccineTypeService;
import com.edu.utils.Constant;

@Controller
@RequestMapping("/admin/report")
public class ReportController {
	@Autowired
	private ReportService reportService;

	@Autowired
	private ReportVaccineRepository reportVaccine;

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private VaccineTypeService vaccineTypeService;

	@Autowired
	private CustomerService customerService;



	@GetMapping("/vaccine")
	public String showListVaccines(Model model) {
		String startDate = null;
		String endDate = null;
		String vaccineTypeId = null;
		String origin = null;
		return listByPageVaccines(model, 1, startDate, endDate, vaccineTypeId, origin);
	}

	@GetMapping("/vaccine/{pageNumber}")
	public String listByPageVaccines(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("vaccineId") String vaccineId, @Param("origin") String origin) {
		System.out.println("===================controler" + endDate + origin);
		Page<Vaccine> page = reportService.reportVaccines(currentPage, vaccineId, origin, startDate, endDate);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<Vaccine> listVaccines = page.getContent();
		List<VaccineTypeIdNameDto> listVaccineTypes = vaccineTypeService.getAllVaccineType();
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listVaccines", listVaccines);
		model.addAttribute("listVaccineTypes", listVaccineTypes);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("vaccineId", vaccineId);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("origin", origin);

		return "report-vaccine";
	}


	@GetMapping("/chart/vaccine")
	public String getFormChartVaccine(Model model) {
		List<VaccineTypeIdNameDto> listVaccineTypes = vaccineTypeService.getAllVaccineType();
		model.addAttribute("listVaccineTypes", listVaccineTypes);
		return "report-vaccine-chart";
	}

	@PostMapping("/chart/vaccine/show")
	public String valueChartVaccine(Model model, @Param("year") String year, @Param("typeName") String typeName) {
		Map<Month, Integer> injectMap = new TreeMap<Month, Integer>();
		injectMap = reportService.numberInjectionByMonth(Integer.parseInt(year), typeName);
		System.out.println(injectMap);
		List<VaccineTypeIdNameDto> listVaccineTypes = vaccineTypeService.getAllVaccineType();
		model.addAttribute("listVaccineTypes", listVaccineTypes);
		model.addAttribute("year", year);
		model.addAttribute("typeName", typeName);
		model.addAttribute("injectMap", injectMap);
		return "report-vaccine-chart";
	}

}
