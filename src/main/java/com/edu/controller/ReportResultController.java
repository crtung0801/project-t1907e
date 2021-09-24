package com.edu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.model.InjectionResult;
import com.edu.model.Vaccine;
import com.edu.service.ReportService;
import com.edu.service.VaccineService;

@Controller
@RequestMapping("/admin")
public class ReportResultController {
	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private ReportService injectionResultService;

	Vaccine vc;
	InjectionResult ir;

	@GetMapping("/report-result")
	public String findPaginated(Model model) {
		InjectionResult ir = new InjectionResult();
		model.addAttribute("injectionResult", ir);
		List<Vaccine> listVaccine = vaccineService.findAll();
		model.addAttribute("listVaccine", listVaccine);
		return findPaginated(1, model);

	}

	@GetMapping(value = "/report-result/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
		try {
			int pageSize = 5;

			List<Vaccine> listVaccine = vaccineService.findAll();
			model.addAttribute("listVaccine", listVaccine);
			Page<InjectionResult> page = injectionResultService.findPaginated(pageNo, pageSize);
			List<InjectionResult> listInjectionResult = page.getContent();
			InjectionResult ir = new InjectionResult();
			model.addAttribute("injectionResult", ir);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listInjectionResult", listInjectionResult);

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "report-result-list";
	}

	@GetMapping(value = "/result-filter/{pageNo}")
	public String filterResult(@PathVariable(value = "pageNo") int pageNo, Model model,
			InjectionResult injectionResult) {
		injectionResult = ir;
		try {
			int pageSize = 5;
			List<Vaccine> listVaccine = vaccineService.findAll();
			model.addAttribute("listVaccine", listVaccine);
			Page<InjectionResult> page = injectionResultService.filterPaginated(ir, pageNo, pageSize);
			List<InjectionResult> listResult = page.getContent();

			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());

			model.addAttribute("listResult", listResult);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "report-result-filter";
	}

	@PostMapping("/result-filter")
	public String filter(Model model, InjectionResult injectionResult) {
		ir = injectionResult;
		List<Vaccine> listVaccine = (List<Vaccine>) vaccineService.findAll();
		model.addAttribute("listVaccine", listVaccine);
		return filterResult(1, model, injectionResult);
	}

	@GetMapping("/chart-result")
	public String chartResult(@RequestParam(name = "year", defaultValue = "2021") Integer year, Model model) {

		List<Integer> listYear = injectionResultService.listYear();
		if (!listYear.contains(2021)) {
			listYear.add(2021);
		}

		String label[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"Octorber", "November", "December" };
		List<Integer> result = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			result.add(injectionResultService.countInjectResult(year, i));
		}

		model.addAttribute("label", label);
		model.addAttribute("data", result);
		model.addAttribute("year", year);
		model.addAttribute("listYear", listYear);
		return "report-result-chart";
	}

}
