package com.edu.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.dto.EmployeeDto;
import com.edu.dto.UploadDto;
import com.edu.exception.HandlerDateException;
import com.edu.exception.HandlerEmailException;
import com.edu.exception.HandlerFileException;
import com.edu.exception.HandlerIdException;
import com.edu.exception.HandlerPhoneException;
import com.edu.model.Employee;
import com.edu.model.Role;
import com.edu.service.EmployeeService;
import com.edu.service.RoleService;
import com.edu.utils.Constant;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

	@Value("${avatar.base.folder}")
	private String uploadFolder;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/employee/list")
	public String showEmpoyee(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/employee/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<EmployeeDto> page = employeeService.getPages(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<EmployeeDto> listEmployee = page.getContent();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listEmployee", listEmployee);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-employee";
	}

	@GetMapping("/employee/create")
	public String ShowFormCreateEmpl(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("readonly", false);
		model.addAttribute("action", "create");
		return "create-employee";
	}

	@PostMapping("/employee/save")
	public String save(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, UploadDto uploadDto,
			HttpServletRequest request, ModelMap modelMap) {

		List<Role> roles = new ArrayList<>();
		List<Employee> employees = new ArrayList<>();
		boolean isCreate = (request.getParameter("action").equalsIgnoreCase("create")) ? true : false;
		
		if (result.hasErrors()) {
			modelMap.addAttribute("readonly", !isCreate);
			modelMap.addAttribute("action", request.getParameter("action"));
			return "create-employee";
		}
//		modelMap.addAttribute("replaceImage", request.getParameter("image").toString());
		Long id = Long.parseLong(request.getParameter("idOfRole"));
		Role role = roleService.findById(id);
		roles.add(role);
		employee.setRoles(roles);

		try {
			employee.setStatus(1);
			employee.setPassword(bCryptPasswordEncoder.encode("123456"));
			employee.setUsername(employee.getEmail());
			employeeService.save(employee, uploadDto, isCreate);
			employees.add(employee);
			role.setEmployees(employees);
			roleService.saveRole(role);
//			modelMap.addAttribute("messagecheck", Constant.SUCCESS_ADD);
			return "redirect:/admin/employee/list";
			
		} catch (HandlerIdException e) {
			modelMap.addAttribute("messageId", e.getMessage());
			modelMap.addAttribute("readonly", !isCreate);
			modelMap.addAttribute("action", request.getParameter("action"));

		} catch (HandlerDateException e) {
			modelMap.addAttribute("messageBirthDate", e.getMessage());
			modelMap.addAttribute("readonly", !isCreate);
			modelMap.addAttribute("action", request.getParameter("action"));

		} catch (HandlerPhoneException e) {
			modelMap.addAttribute("messagePhone", e.getMessage());
			modelMap.addAttribute("readonly", !isCreate);
			modelMap.addAttribute("action", request.getParameter("action"));

		} catch (HandlerEmailException e) {
			modelMap.addAttribute("messageEmail", e.getMessage());
			modelMap.addAttribute("readonly", !isCreate);
			modelMap.addAttribute("action", request.getParameter("action"));
	
		} catch (HandlerFileException e) {
			modelMap.addAttribute("messageFile", e.getMessage());
			modelMap.addAttribute("readonly", !isCreate);
			modelMap.addAttribute("action", request.getParameter("action"));

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return "create-employee";
	}

	@GetMapping("/employee/update/{id}")
	public String update(@PathVariable("id") String id, ModelMap modelMap) {
		Employee employee =  employeeService.findById(id);
		modelMap.addAttribute("employee", employeeService.findById(id));
		modelMap.addAttribute("readonly", true);
		modelMap.addAttribute("action", "update");
		modelMap.addAttribute("imageName", employee.getImage());
		return "create-employee";
	}

	@GetMapping("/employee/delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {

		if (!employeeService.delete(id)) {
			redirectAttributes.addFlashAttribute("error", Constant.INVALID_DELETE_ADMIN + id);

		}

		return "redirect:/admin/employee/list";
	}

	@PostMapping(value = "/employee/delete")
	public String delete(@RequestParam(name = "checked", required = false) List<String> ids, Model model,
			RedirectAttributes redirectAttributes) {

		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", Constant.INVALID_NO_CHOOSE);
		} else {
			for (String id : ids) {
				if (!employeeService.delete(id)) {
					redirectAttributes.addFlashAttribute("error", Constant.INVALID_DELETE_ADMIN + id);

				}
			}
		}
		return "redirect:/admin/employee/list";
	}

	@RequestMapping(value = "/image/{imageName}", method = RequestMethod.GET)
	public @ResponseBody byte[] showImage(@PathVariable("imageName") String imageName) throws IOException {
		File file = new File(uploadFolder + imageName);
		try (InputStream in = new FileInputStream(file)) {
			return toByteArray(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}

		return os.toByteArray();
	}

}
