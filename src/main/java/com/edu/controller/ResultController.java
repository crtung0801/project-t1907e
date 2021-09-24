package com.edu.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.dto.VaccineTypeIdNameDto;
import com.edu.model.Customer;
import com.edu.model.InjectionResult;
import com.edu.model.Place;
import com.edu.model.Prevention;
import com.edu.model.Vaccine;
import com.edu.repository.CustomerRespository;
import com.edu.service.CustomerService;
import com.edu.service.PlaceAndPrevention;
import com.edu.service.ResultService;
import com.edu.service.ScheduleService;
import com.edu.service.VaccineService;
import com.edu.service.VaccineTypeService;
import com.edu.utils.Constant;

@Controller
@RequestMapping("/admin/injection-result")
public class ResultController {

	@Autowired
	private ResultService resultService;

	@Autowired
	private CustomerRespository cusRepo;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private VaccineTypeService vaccineTypeService;

	@Autowired
	private ScheduleService scheduService;

	@Autowired
	private PlaceAndPrevention pApService;

	@GetMapping("/list")
	public String showList(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<InjectionResult> page = resultService.listResults(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<InjectionResult> listResults = page.getContent();
		List<Customer> customers = customerService.findAll();
		List<Vaccine> vaccines = vaccineService.findAll();
		List<Prevention> preventions = pApService.preventions();
		List<Place> places = pApService.places();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("listResults", listResults);
		model.addAttribute("customers", customers);
		model.addAttribute("vaccines", vaccines);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("places", places);
		model.addAttribute("preventions", preventions);
		return "list-injection-result";
	}

	@GetMapping("/create")
	public String showFormCreate(Model model) {
		List<Customer> customers = cusRepo.findCus();
		List<Prevention> preventions = pApService.preventions();
		List<Place> places = pApService.places();
		model.addAttribute("places", places);
		model.addAttribute("preventions", preventions);
		model.addAttribute("customers", customers);
		List<Vaccine> vaccines = vaccineService.findAll();
		model.addAttribute("vaccines", vaccines);
		InjectionResult injectionResult = new InjectionResult();
		model.addAttribute("resultInjection", injectionResult);
		return "create-injection-result";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("resultInjection") InjectionResult injectionResult,
			BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirect, Model model) {
		Date date = new Date();
		List<Customer> customers = cusRepo.findCus();
		List<Prevention> preventions = pApService.preventions();
		List<Place> places = pApService.places();
		model.addAttribute("places", places);
		model.addAttribute("preventions", preventions);
		model.addAttribute("customers", customers);
		List<Vaccine> vaccines = vaccineService.findAll();
		model.addAttribute("vaccines", vaccines);

		if (injectionResult.getInjectionDate() == null || injectionResult.getInjectionDate().before(date)) {
			injectionResult.setInjectionDate(date);
		}
		if (injectionResult.getNextInjectionDate() == null || injectionResult.getNextInjectionDate().before(date)) {
			injectionResult.setNextInjectionDate(date);
		}
		if (bindingResult.hasErrors()) {
			return "create-injection-result";
		}
		redirect.addFlashAttribute("message", "Success! your Injection result is complete");
		injectionResult.setStatus(1);
		resultService.save(injectionResult);
		return "redirect:/admin/injection-result/list";
	}

	@GetMapping("/update/{id}")
	public String ShowFormUpdateEmpl(@ModelAttribute("resultInjection") InjectionResult injectionResult,
			@PathVariable(name = "id") String id, Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);
		List<Prevention> preventions = pApService.preventions();
		List<Vaccine> vaccines = vaccineService.findAll();
		List<Place> places = pApService.places();
		model.addAttribute("places", places);
		model.addAttribute("preventions", preventions);
		model.addAttribute("vaccines", vaccines);
		injectionResult = resultService.findById(id);
		model.addAttribute("result", injectionResult);
		return "update-injection-result";
	}

	@GetMapping("/delete/{id}")

	public String deleteBrand(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		resultService.changeStatus(id);
		return "redirect:/admin/customer/list";
	}

	@PostMapping("/delete")
	public String deleteResult(@RequestParam(name = "idChecked", required = false) List<String> ids, Model model,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", "Invalid data - please recheck your selects!");
		} else {

			for (String id : ids) {

				resultService.changeStatus(id);
			}

		}
		return "redirect:/admin/injection-result/list";
	}
}
