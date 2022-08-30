package com.appspring.appspring.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appspring.appspring.ReportUtil;
import com.appspring.appspring.dto.CategoriaDto;
import com.appspring.appspring.dto.RelatorioVendasDto;
import com.appspring.appspring.modelo.Estoque;
import com.appspring.appspring.modelo.Vendas;
import com.appspring.appspring.repository.EstoqueRepository;
import com.appspring.appspring.repository.FornecedorRepository;
import com.appspring.appspring.repository.ProdutoRepository;
import com.appspring.appspring.repository.VendasRepository;

@Service
public class VendasService {

	@Autowired
	private VendasRepository vendasRepository;

	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private ReportUtil reports;

	public ModelAndView cadastrarVenda() {
		ModelAndView mv = new ModelAndView("Home/Vendas/Cadastro");
		mv.addObject("vendasobj", new Vendas());
		mv.addObject("vendas", vendasRepository.ListarVendas());
		mv.addObject("estoque", estoqueRepository.ListarEstoque());
		return mv;
	}

	public ModelAndView SalvarVenda(Vendas venda, RedirectAttributes ra) {
		Long total = vendasRepository.totalVendas();
		String codVenda = "VEN" + LocalDate.now().getYear() + total;

		venda.setCodVenda(codVenda);
		venda.setDeletado(false);
		venda.setValorVenda((venda.getValorVenda() * venda.getQuantidade()));
		Vendas v = vendasRepository.save(venda);

		Estoque estoque = estoqueRepository.buscarPorId(v.getEstoque().getId());
		estoque.setQuantidade((estoque.getQuantidade() - venda.getQuantidade()));
		estoqueRepository.save(estoque);

		ra.addFlashAttribute("msg", "Venda Efetuada com Sucesso");
		ModelAndView mv = new ModelAndView("redirect:/Home/Vendas/ListarVendas");
		return mv;
	}

	public ModelAndView ListarVenda() {
		ModelAndView mv = new ModelAndView("Home/Vendas/ListarVendas");
		mv.addObject("vendas", vendasRepository.ListarVendas());
		return mv;
	}

	public ModelAndView DeletarVenda(Long idVendas, RedirectAttributes ra) {
		Vendas vendas = vendasRepository.buscarPorId(idVendas);
		Estoque estoque = estoqueRepository.buscarPorId(vendas.getEstoque().getId());
		estoque.setQuantidade((estoque.getQuantidade() + vendas.getQuantidade()));
		estoqueRepository.save(estoque);

		vendas.setDeletado(true);
		vendasRepository.save(vendas);
		ra.addFlashAttribute("msg", "Item deletado com Sucesso");
		ModelAndView mv = new ModelAndView("redirect:/Home/Vendas/ListarVendas");
		return mv;
	}

	public ModelAndView RelatorioVenda() {
		List<String> listafornecedores = fornecedorRepository.ListadeProdutosSemEstoque();
		List<String> listaprodutos = produtoRepository.ListadeProdutosSemEstoque();
		List<Object[]> categorias = estoqueRepository.buscarCategoriaComEstoque();
		List<CategoriaDto> categoriaDto = new ArrayList<CategoriaDto>();
		for (Object[] categoria : categorias) {
			CategoriaDto c = new CategoriaDto();
			c.setNome(categoria[0].toString());
			c.setQtdEstoque(categoria[1].toString());
			categoriaDto.add(c);
		}

		ModelAndView mv = new ModelAndView("Home/Vendas/Relatorio");
		mv.addObject("categorias", categoriaDto);
		mv.addObject("produtos", listaprodutos);
		mv.addObject("fornecedores", listafornecedores);
		return mv;
	}

	public ModelAndView RelatorioVendasJasper(RedirectAttributes ra, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("Home/Vendas/RelatorioJasper");
		List<Object[]> categoriaobj = null;
		List<String> produtos = new ArrayList<String>();
		List<String> fornecedores = new ArrayList<String>();

		categoriaobj = estoqueRepository.buscarCategoriaComEstoque();
		produtos = produtoRepository.ListadeProdutosSemEstoque();
		fornecedores = fornecedorRepository.ListadeProdutosSemEstoque();

		List<RelatorioVendasDto> relatorio = new ArrayList<RelatorioVendasDto>();
		relatorio = converterObjetoParaRelatorio(fornecedores, produtos, categoriaobj);

		if (!relatorio.isEmpty()) {
			byte[] pdf = reports.gerarRelatorio(relatorio, "RelatorioVendasGeral", request.getServletContext());
			response.setContentLength(pdf.length);
			response.setContentType("application/pdf");
			String headkey = "Content-Disposition";
			String headvalue = String.format("inline; filename=\"RelatorioVendasGeral.pdf\"");
			response.setHeader(headkey, headvalue);
			response.getOutputStream().write(pdf);
		} else {
			ra.addFlashAttribute("msgAlert", "Nenhum Registro encontrado");
			ModelAndView modelAndView = new ModelAndView("redirect:/Home/Vendas/Relatorio");
			return modelAndView;
		}
		return mv;
	}

	private List<RelatorioVendasDto> converterObjetoParaRelatorio(List<String> Fornecedores, List<String> produto,
			List<Object[]> categoriaobj) {
		List<RelatorioVendasDto> relatorioVendasDto = new ArrayList<>();
		List<CategoriaDto> catDto = new ArrayList<CategoriaDto>();
		for (Object[] obj : categoriaobj) {
			CategoriaDto cat = new CategoriaDto();
			cat.setNome(obj[0].toString());
			cat.setQtdEstoque(obj[1].toString());
			catDto.add(cat);
		}

		RelatorioVendasDto relatorioVenda = new RelatorioVendasDto(Fornecedores, produto, catDto);
		relatorioVendasDto.add(relatorioVenda);

		return relatorioVendasDto;
	}
}
