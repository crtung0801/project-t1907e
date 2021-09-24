package com.edu.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.dto.NewsDto;
import com.edu.model.News;
import com.edu.model.NewsType;
import com.edu.service.NewsService;
import com.edu.service.NewsTypeService;
import com.edu.utils.Constant;

@Controller
@RequestMapping("/admin/news")
public class NewsController {
	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsTypeService newsTypeService;

	@GetMapping("/list")
	public String showNews(Model model) {
		String keyword = null;
		return listByPage(model, 1, keyword);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("keyword") String keyword) {
		Page<NewsDto> page = newsService.listNews(currentPage, keyword);
		int totalItems = (int) page.getTotalElements();
		int totalPages = page.getTotalPages();
		long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
		long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		List<NewsDto> listNews = page.getContent();
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listNews", listNews);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		return "list-news";
	}

	@PostMapping("/delete")
	public String deleteResult(@RequestParam(name = "id", required = false) List<Long> ids, Model model,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("error", "Invalid data - please recheck your selects!");
			return "redirect:/admin/news/list";
		} else {

		}

		for (Long id : ids) {

			newsService.changeStatus(id);
		}
		return "redirect:/admin/news/list";
	}

	@GetMapping("/create")
	public String showFormCreateNews(Model model) {
		model.addAttribute("news", new News());
		return "create-news";
	}

	@PostMapping("/save")
	public String saveNews(@ModelAttribute @Valid News news, Errors errors, Model model, HttpServletRequest request) {
		if (null != errors && errors.getErrorCount() > 0) {
			return "create-news";
		} else {
			Optional<NewsType> optionNewsType = newsTypeService.findById("NT_1000000");
			NewsType newsType = optionNewsType.get();
			news.setNewsType(newsType);
			news.setStatus(1);
			news.setDatePost(String.valueOf(LocalDate.now()));
			newsService.addNews(news);
			return "redirect:/admin/news/list";
		}
	}

	@GetMapping("/update")
	public String showFormUpdateNews(Model model, @RequestParam(value = "newsId", required = false) String newsId) {
		System.out.println(newsId);
		Optional<News> optionNews = newsService.findNewsById(Long.valueOf(newsId));
		String newsTypeId = optionNews.get().getNewsType().getNewsTypeId();
		System.out.println("=====================id newstype" + newsTypeId);
		if (optionNews.isPresent()) {
			model.addAttribute("newsTypeId", newsTypeId);
			model.addAttribute("news", optionNews.get());
			return "update-news";
		}
		model.addAttribute("error", "Can not find News");
		return "redirect:/admin/news/list";
	}

	@PostMapping("/saveUpdate")
	public String saveUpdateNews(@ModelAttribute @Valid News news, Errors errors,
			@RequestParam(value = "newsTypeId", required = false) String newsTypeId, Model model) {
		if (null != errors && errors.getErrorCount() > 0) {
			return "update-news";
		} else {
			Optional<NewsType> optionNewsType = newsTypeService.findById(newsTypeId);
			NewsType newsType = optionNewsType.get();
			news.setNewsType(newsType);
			news.setStatus(1);
			news.setDatePost(String.valueOf(LocalDate.now()));
			newsService.addNews(news);
			return "redirect:/admin/news/list";
		}
	}
}
