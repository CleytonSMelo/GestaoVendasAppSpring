package com.appspring.appspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Fornecedor;
import com.appspring.appspring.repository.FornecedorRepository;

@Controller
@RequestMapping("**/Home/Fornecedor/")
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@GetMapping("Cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("Home/Fornecedor/Cadastro");
		mv.addObject("fornecedorobj", new Fornecedor());
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}
	
	@PostMapping("Salvar")
	public ModelAndView salvar(Fornecedor Fornecedor, RedirectAttributes ra) {
		if (Fornecedor.getId() == 0) {
			Fornecedor.setDeletado(false);
			fornecedorRepository.save(Fornecedor);
			ra.addFlashAttribute("msg", "Fornecedor Cadastrado com Sucesso");		
			ModelAndView mv = new ModelAndView("redirect:/Home/Fornecedor/Cadastro");
			return mv;
		}else {
			Fornecedor forncedorAtualizar = fornecedorRepository.buscarPorId(Fornecedor.getId());
			forncedorAtualizar.setDeletado(forncedorAtualizar.getDeletado());
			forncedorAtualizar.setNome(Fornecedor.getNome());
			fornecedorRepository.save(forncedorAtualizar);
			ra.addFlashAttribute("msg", "Fornecedor Atualizado com Sucesso");			
			ModelAndView mv = new ModelAndView("redirect:/Home/Fornecedor/Cadastro");
			return mv;
		}
	}
	
	@GetMapping("EditarFornecedor/{idFornecedor}")
	public ModelAndView editar(@PathVariable("idFornecedor") Long idFornecedor, RedirectAttributes ra) {
		Fornecedor Fornecedor = fornecedorRepository.buscarPorId(idFornecedor);
		ModelAndView mv = new ModelAndView("Home/Fornecedor/Cadastro");
		mv.addObject("fornecedorobj", Fornecedor);
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}
	
	@GetMapping("DeletarFornecedor/{idFornecedor}")
	public ModelAndView deletar(@PathVariable("idFornecedor") Long idFornecedor, RedirectAttributes ra) {
		Fornecedor Fornecedor = fornecedorRepository.buscarPorId(idFornecedor);
		Fornecedor.setDeletado(true);
		fornecedorRepository.save(Fornecedor);
		ra.addFlashAttribute("msg", "Fornecedor Deletado com Sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/Home/Fornecedor/Cadastro");
		return mv;
	}	
}
