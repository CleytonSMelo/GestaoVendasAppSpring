package com.appspring.appspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.modelo.Fornecedor;
import com.appspring.appspring.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	public ModelAndView CadastroFornecedor() {
		ModelAndView mv = new ModelAndView("Home/Fornecedor/Cadastro");
		mv.addObject("fornecedorobj", new Fornecedor());
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}

	public ModelAndView SalvarFornecedor(Fornecedor fornecedor, RedirectAttributes ra) {
		if (fornecedor.getId() == 0) {
			fornecedor.setDeletado(false);
			fornecedorRepository.save(fornecedor);
			ra.addFlashAttribute("msg", "Fornecedor Cadastrado com Sucesso");
			ModelAndView mv = new ModelAndView("redirect:/Home/Fornecedor/Cadastro");
			return mv;
		} else {
			Fornecedor forncedorAtualizar = fornecedorRepository.buscarPorId(fornecedor.getId());
			forncedorAtualizar.setDeletado(forncedorAtualizar.getDeletado());
			forncedorAtualizar.setNome(fornecedor.getNome());
			fornecedorRepository.save(forncedorAtualizar);
			ra.addFlashAttribute("msg", "Fornecedor Atualizado com Sucesso");
			ModelAndView mv = new ModelAndView("redirect:/Home/Fornecedor/Cadastro");
			return mv;
		}
	}

	public ModelAndView EditarFornecedor(Long idFornecedor, RedirectAttributes ra) {
		Fornecedor Fornecedor = fornecedorRepository.buscarPorId(idFornecedor);
		ModelAndView mv = new ModelAndView("Home/Fornecedor/Cadastro");
		mv.addObject("fornecedorobj", Fornecedor);
		mv.addObject("fornecedores", fornecedorRepository.ListarFornecedores());
		return mv;
	}

	public ModelAndView DeletarFornecedor(Long idFornecedor, RedirectAttributes ra) {
		Fornecedor Fornecedor = fornecedorRepository.buscarPorId(idFornecedor);
		Fornecedor.setDeletado(true);
		fornecedorRepository.save(Fornecedor);
		ra.addFlashAttribute("msg", "Fornecedor Deletado com Sucesso");
		ModelAndView mv = new ModelAndView("redirect:/Home/Fornecedor/Cadastro");
		return mv;
	}
}
