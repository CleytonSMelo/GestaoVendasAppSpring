package com.appspring.appspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Categoria;
import com.appspring.appspring.repository.CategoriaRepository;
import com.appspring.appspring.service.CategoriaService;

@Controller
@RequestMapping("**/Home/Categoria/")
public class CategoriaController {
	
	private final CategoriaService categoriaService;
	
	@Autowired
	public CategoriaController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	@GetMapping("Cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = categoriaService.CadastroCategoria();
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Categoria categoria, RedirectAttributes ra) {
		ModelAndView mv = categoriaService.SalvarCategoria(categoria, ra);
		return mv;
	}
	
	@GetMapping("EditarCategoria/{idCategoria}")
	public ModelAndView editar(@PathVariable("idCategoria") Long idCategoria, RedirectAttributes ra) {
		ModelAndView mv = categoriaService.EditarCategoria(idCategoria, ra);
		return mv;
	}
	
	@GetMapping("DeletarCategoria/{idCategoria}")
	public ModelAndView deletar(@PathVariable("idCategoria") Long idCategoria, RedirectAttributes ra) {
		ModelAndView mv = categoriaService.DeletarCategoria(idCategoria, ra);
		return mv;
	}	
}
