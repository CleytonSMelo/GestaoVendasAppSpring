package com.appspring.appspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.appspring.appspring.repository.EstoqueRepository;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.repository.VendasRepository;
import com.appspring.appspring.service.IndexService;

@Controller
@RequestMapping("/")
public class IndexController {

	private final IndexService indexService;

	@Autowired
	public IndexController(IndexService indexService) {
		this.indexService = indexService;
	}
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = indexService.index();
		return mv;
	}
}
