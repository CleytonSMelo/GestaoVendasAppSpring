package com.appspring.appspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Categoria;
import com.appspring.appspring.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public ModelAndView CadastroCategoria() {
		ModelAndView mv = new ModelAndView("Home/Categoria/Cadastro");
		mv.addObject("categoriaobj", new Categoria());
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		return mv;
	}

	public ModelAndView SalvarCategoria(Categoria categoria, RedirectAttributes ra) {
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

	public ModelAndView EditarCategoria(Long idCategoria, RedirectAttributes ra) {
		Categoria categoria = categoriaRepository.buscarPorId(idCategoria);
		ModelAndView mv = new ModelAndView("Home/Categoria/Cadastro");
		mv.addObject("categoriaobj", categoria);
		mv.addObject("categorias", categoriaRepository.ListarCategorias());
		return mv;
	}

	public ModelAndView DeletarCategoria(Long idCategoria, RedirectAttributes ra) {
		Categoria categoria = categoriaRepository.buscarPorId(idCategoria);
		categoria.setDeletado(true);
		categoriaRepository.save(categoria);
		ra.addFlashAttribute("msg", "Categoria Deletada com Sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/Home/Categoria/Cadastro");
		return mv;
	}
}
