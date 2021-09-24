package com.edu.controller;

import java.util.List;

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

import com.edu.dto.VaccineDto1;
import com.edu.exception.HandlerDateException;
import com.edu.exception.HandlerIdException;
import com.edu.model.Vaccine;
import com.edu.service.ReadFileService;
import com.edu.service.VaccineService;
import com.edu.service.VaccineTypeService;
import com.edu.utils.Constant;
import com.edu.utils.Utils;

@Controller
@RequestMapping("/admin/vaccine")
public class VaccineController {

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private ReadFileService readFileService;

	@Autowired
	private VaccineTypeService vaccineTypeService;

	@RequestMapping("/list")
	public String showList(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<VaccineDto1> page = vaccineService.getPages(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<VaccineDto1> listVaccines = page.getContent();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("listVaccines", listVaccines);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-vaccine";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("vaccine", new Vaccine(true));
		model.addAttribute("vaccineTypes", vaccineTypeService.findAllByIdAndName());
		return "create-vaccine";
	}

	@PostMapping(value = "/save")
	public String save(@Valid @ModelAttribute("vaccine") Vaccine vaccine, BindingResult bindResult, Model model,
			RedirectAttributes redirect) {

		model.addAttribute("vaccineTypes", vaccineTypeService.findAllByIdAndName());
		model.addAttribute("vaccine", vaccine);
		if (bindResult.hasErrors()) {
			return "create-vaccine";

		}

		try {
			vaccineService.save(vaccine, true);
			redirect.addFlashAttribute("message", "Create Vaccine Success!!");
			return "redirect:/admin/vaccine/list";
		} catch (HandlerIdException e) {
			bindResult.addError(new FieldError("vaccine", "vaccineId", e.getMessage()));

		} catch (HandlerDateException e) {
			bindResult.addError(new FieldError("vaccine", "timeBeginNextInjection", e.getMessage()));

		} catch (Exception e) {
			e.printStackTrace();

		}
		return "create-vaccine";
	}

	@PostMapping(value = "/update")
	public String update(@Valid @ModelAttribute("vaccine") Vaccine vaccine, BindingResult bindResult, Model model,
			RedirectAttributes redirect) {

		model.addAttribute("vaccineTypes", vaccineTypeService.findAllByIdAndName());
		model.addAttribute("vaccine", vaccine);
		if (bindResult.hasErrors()) {
			return "update-vaccine";

		}

		try {
			vaccineService.save(vaccine, false);
			redirect.addFlashAttribute("message", "Create Vaccine Success!!");
			return "redirect:/admin/vaccine/list";
		} catch (HandlerDateException e) {
			bindResult.addError(new FieldError("vaccine", "timeBeginNextInjection", e.getMessage()));

		} catch (Exception e) {
			e.printStackTrace();

		}
		return "update-vaccine";

	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable(name = "id") String id, Model model) {
		Vaccine vaccine = vaccineService.findById(id);
		model.addAttribute("vaccine", vaccine);
		model.addAttribute("vaccineTypes", vaccineTypeService.findAllByIdAndName());
		return "update-vaccine";
	}

	@PostMapping("/active")
	public String makeIsActive(@RequestParam(name = "id", required = false) List<String> ids,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", Constant.INVALID_NO_CHOOSE);

		} else {
			if (!Utils.checkInActive(ids, vaccineService)) {
				redirectAttributes.addFlashAttribute("error", Constant.INVALID_CHOOSE);
			} else {
				for (String id : ids) {
					vaccineService.makeInActive(id);
				}
			}
		}
		return "redirect:/admin/vaccine/list";
	}
	
	@GetMapping("/upload-file")
	public String home(Model model) {
		model.addAttribute("vaccine", new Vaccine());
		List<Vaccine> vaccines = readFileService.findAll();
		model.addAttribute("vaccines", vaccines);
		return "import-vaccine";
	}

	@PostMapping("/save-file")
	public String uploadFile(@ModelAttribute Vaccine vaccine, RedirectAttributes redirectAttributes) {
		
		try {
			if(readFileService.save(vaccine.getFile())) {
				redirectAttributes.addFlashAttribute("successmessage", Constant.SUCCESS_FILE);
			} else {
				redirectAttributes.addFlashAttribute("errormessage", Constant.FAIL_FILE);
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errormessage", e.getMessage());
		}
		
		return "redirect:/admin/vaccine/upload-file";
		
	}
}
