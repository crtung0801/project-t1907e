package com.edu.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.dto.ScheduleDto;
import com.edu.exception.HandlerDateException;
import com.edu.exception.HandlerEmailException;
import com.edu.exception.HandlerFileException;
import com.edu.exception.HandlerIdException;
import com.edu.exception.HandlerPhoneException;
import com.edu.model.Employee;
import com.edu.model.InjectionSchedule;
import com.edu.model.Role;
import com.edu.model.Vaccine;
import com.edu.service.ScheduleService;
import com.edu.service.VaccineService;
import com.edu.utils.Constant;

@Controller
@RequestMapping("/admin/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private VaccineService vaccineService;

	@GetMapping("/list")
	public String showList(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<ScheduleDto> page = scheduleService.list(currentPage, keyword);
		System.out.println(page);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<ScheduleDto> schedules = page.getContent();
		List<Vaccine> vaccines = vaccineService.findAll();
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("schedules", schedules);
		model.addAttribute("vaccines", vaccines);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-schedule";
	}

	@GetMapping("/create")
	public String showFormCreate(ModelMap modelMap) {
		// add object schedule
		modelMap.addAttribute("schedule", new InjectionSchedule());
		modelMap.addAttribute("disabled", true);
		modelMap.addAttribute("action", "create");
		// add vaccine name
		modelMap.addAttribute("vaccines", vaccineService.findAllByIdAndName());
		return "create-schedule";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("schedule") InjectionSchedule schedule, BindingResult bindingResult,
			ModelMap modelMap, HttpServletRequest request) {

		boolean isCreate = (request.getParameter("action").equalsIgnoreCase("create")) ? true : false;
		modelMap.addAttribute("vaccines", vaccineService.findAllByIdAndName());
		modelMap.addAttribute("disabled", isCreate);
		modelMap.addAttribute("action", request.getParameter("action"));

		if (bindingResult.hasErrors()) {
			return "create-schedule";

		}

		try {
			scheduleService.save(schedule, isCreate);
			return "redirect:/admin/schedule/list";

		} catch (HandlerDateException e) {
			modelMap.addAttribute("messageDate", e.getMessage());
//			modelMap.addAttribute("disabled", isCreate);
//			modelMap.addAttribute("action", request.getParameter("action"));

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return "create-schedule";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") String id, ModelMap modelMap) {

		// add vaccine name
		modelMap.addAttribute("vaccines", vaccineService.findAllByIdAndName());
		modelMap.addAttribute("disabled", false);
		modelMap.addAttribute("action", "update");
		// add schedule
		modelMap.addAttribute("schedule", scheduleService.findById(id));

		return "create-schedule";
	}

}
