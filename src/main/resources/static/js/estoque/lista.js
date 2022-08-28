
var idProduto = $('#produto').val();
buscarproduto(idProduto);

$('#produto').change(function(event) {
	var id = $(this).val();
	buscarproduto(id);
});

function buscarproduto(id) {
	var url = '/AppSpring/Home/Produto/' + id;
	console.log(url);
	$.ajax({
		type : 'GET',
		url : url
	}).done(function(data) {
		//console.log(data);
		if (data != null) {
			$("#categoria2").val(data.categoria.nome);
			$("#fornecedor").val(data.fornecedor.nome);
			$("#valorCompra").val(data.valorCompra);
		}

	}).fail(function(jqXHR, textStatus, errorThrown) {
		$("#categoria2").val("");
		$("#fornecedor").val("");
		$("#valorCompra").val("0.0");

	});
}
	
	