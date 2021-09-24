package com.edu.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edu.dto.Account;
import com.edu.model.Employee;
import com.edu.model.GooglePojo;
import com.edu.service.EmployeeService;
import com.edu.utils.Constant;
import com.edu.utils.FbUtils;
import com.edu.utils.GoogleUtils;

@Controller
public class LoginController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private GoogleUtils googleUtils;

	@Autowired
	private FbUtils fbUtils;

	@GetMapping("/")
	public String login() {
		return "redirect:login";
	}

	@GetMapping("/home-page")
	public String showDashBoard(Model model) {
		String role = "";
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			role = authority.getAuthority();
		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		if (Constant.ROLE_ADMIN.equalsIgnoreCase(role)) {
//			Optional<Employee> optional = employeeService.findByUsername(username, 1);
//
//			//Optional<Account> optional = employeeService.findByUsername(username);
//			if (optional.isPresent()) {
//				model.addAttribute("email", optional.get().getEmail());
//				System.out.println( optional.get().getEmail());
//			}
			return "redirect:/admin";
		} else {
			return "user";
		}
	}

	@GetMapping("/login-google")
	public String loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}

		String accessToken = googleUtils.getToken(code);

		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
		UserDetails userDetail = googleUtils.buildUser(googlePojo);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
				userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/admin";
	}

	@GetMapping("/login-facebook")
	public String loginFacebook(HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "redirect:/login?facebook=error";
		}
		String accessToken = fbUtils.getToken(code);

		com.restfb.types.User user = fbUtils.getUserInfo(accessToken);
		UserDetails userDetail = fbUtils.buildUser(user);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
				userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/admin";
	}
}
