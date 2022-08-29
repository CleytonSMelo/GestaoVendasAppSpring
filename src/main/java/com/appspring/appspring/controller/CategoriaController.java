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

@Controller
@RequestMapping("**/Home/Categoria/")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("Cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("Home/Categoria/Cadastro");
		mv.addObject("categoriaobj", new Categoria());
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Categoria categoria, RedirectAttributes ra) {
		if (categoria.getId() == 0) {
			categoria.setDeletado(false);
			categoriaRepository.save(categoria);
			ra.addFlashAttribute("msg", "Categoria Cadastrada com Sucesso");		
			ModelAndView mv = new ModelAndView("redirect:/Home/Categoria/Cadastro");
			return mv;
		}else {
			Categoria categoraAtualizar = categoriaRepository.buscarPorId(categoria.getId());
			categoraAtualizar.setDeletado(categoraAtualizar.getDeletado());
			categoraAtualizar.setNome(categoria.getNome());
			categoriaRepository.save(categoraAtualizar);
			ra.addFlashAttribute("msg", "Categoria Atualizada com Sucesso");			
			ModelAndView mv = new ModelAndView("redirect:/Home/Categoria/Cadastro");
			return mv;
		}
	}
	
	@GetMapping("EditarCategoria/{idCategoria}")
	public ModelAndView editar(@PathVariable("idCategoria") Long idCategoria, RedirectAttributes ra) {
		Categoria categoria = categoriaRepository.buscarPorId(idCategoria);
		ModelAndView mv = new ModelAndView("Home/Categoria/Cadastro");
		mv.addObject("categoriaobj", categoria);
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		return mv;
	}
	
	@GetMapping("DeletarCategoria/{idCategoria}")
	public ModelAndView deletar(@PathVariable("idCategoria") Long idCategoria, RedirectAttributes ra) {
		Categoria categoria = categoriaRepository.buscarPorId(idCategoria);
		categoria.setDeletado(true);
		categoriaRepository.save(categoria);
		ra.addFlashAttribute("msg", "Categoria Deletada com Sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/Home/Categoria/Cadastro");
		return mv;
	}	
}
