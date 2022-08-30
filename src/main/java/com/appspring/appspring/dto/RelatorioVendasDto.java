package com.appspring.appspring.dto;

import java.util.List;

public class RelatorioVendasDto {
        private List<String> fornecedores;
        private List<String> produtos;
        private List<CategoriaDto> categorias;
        
        
		public RelatorioVendasDto(List<String> fornecedores, List<String> produtos, List<CategoriaDto> categorias) {
			super();
			this.fornecedores = fornecedores;
			this.produtos = produtos;
			this.categorias = categorias;
		}
		
		
		public List<String> getFornecedores() {
			return fornecedores;
		}
		public void setFornecedores(List<String> fornecedores) {
			this.fornecedores = fornecedores;
		}
		public List<String> getProdutos() {
			return produtos;
		}
		public void setProdutos(List<String> produtos) {
			this.produtos = produtos;
		}
		public List<CategoriaDto> getCategorias() {
			return categorias;
		}
		public void setCategorias(List<CategoriaDto> categorias) {
			this.categorias = categorias;
		}
        
        
}
