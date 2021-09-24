package com.edu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.dto.UploadDto;
import com.edu.dto.VaccineTypeDto;
import com.edu.exception.HandlerFileException;
import com.edu.exception.HandlerIdException;
import com.edu.model.VaccineType;
import com.edu.service.VaccineTypeService;
import com.edu.utils.Constant;
import com.edu.utils.Utils;

@Controller
@RequestMapping("/admin/vaccine-type")
public class VaccineTypeController {

	@Value("${avatar.base.folder}")
	private String uploadFolder;

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
		Page<VaccineTypeDto> page = vaccineTypeService.getPages(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<VaccineTypeDto> listVaccineTypes = page.getContent();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("listVaccineTypes", listVaccineTypes);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-vaccine-type";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("vaccineType", new VaccineType(true));
		return "create-vaccine-type";
	}

	@PostMapping("/save")
	public String save(@Valid VaccineType vaccineType, BindingResult bindingResult, UploadDto uploadDto,
			Model model, HttpServletRequest request) {
		
		if (bindingResult.hasErrors()) {
			return "create-vaccine-type";
		}
		
		try {
			vaccineTypeService.save(vaccineType, uploadDto, true);
			return "redirect:/admin/vaccine-type/list";
			
		} catch (HandlerIdException e) {
			model.addAttribute("messagecheckid", e.getMessage());
			
		} catch (HandlerFileException e) {
			model.addAttribute("errorImage", e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		return "create-vaccine-type";

	}

	@PostMapping("/update")
	public String update(@Valid VaccineType vaccineType, BindingResult bindingResult, UploadDto uploadDto,
			HttpServletRequest request, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("replaceImage", request.getParameter("image").toString());
			return "update-vaccine-type";
		}
		
		try {
			vaccineTypeService.save(vaccineType, uploadDto, false);
			return "redirect:/admin/vaccine-type/list";
			
		} catch (HandlerFileException e) {
			model.addAttribute("errorImage", e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "update-vaccine-type";

	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") String id, Model model) {
		VaccineType vaccineType = vaccineTypeService.findById(id);
		model.addAttribute("vaccineType", vaccineType);
		model.addAttribute("imageName", vaccineType.getImage());
		return "update-vaccine-type";
	}

	@PostMapping("/active")
	public String makeIsActive(@RequestParam(name = "id", required = false) List<String> ids,
			RedirectAttributes redirectAttributes) {
		
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", Constant.INVALID_NO_CHOOSE);
			
		} else {
			if (!Utils.checkInActive(ids, vaccineTypeService)) {
				redirectAttributes.addFlashAttribute("error", Constant.INVALID_CHOOSE);
				
			} else {
				for (String id : ids) {
					vaccineTypeService.makeInActive(id);
				}
			}
		}
		return "redirect:/admin/vaccine-type/list";
	}
}
