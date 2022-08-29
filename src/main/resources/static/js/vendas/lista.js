
var idProduto = $('#estoque').find(':selected').attr('id-estoque');
buscarproduto(idProduto);

$('#estoque').change(function(event) {
	var id = $(this).find(':selected').attr('id-estoque');
	buscarproduto(id);	
});

function buscarproduto(id) {
	var url = '/AppSpring/Home/Estoque/' + id;
	console.log(url);
	$.ajax({
		type : 'GET',
		url : url
	}).done(function(data) {
		if (data != null) {
			$("#categoria2").val(data.produto.categoria.nome);
			$("#fornecedor").val(data.produto.fornecedor.nome);
			$("#quantidade").val(data.quantidade);
			$("#valorVenda").val(data.valorVenda);
			$("#quantidadeselecionado").val("1");
			$("#produtoId").val(data.produto.id);
			
			verificarQtd();
		}

	}).fail(function(jqXHR, textStatus, errorThrown) {
		$("#categoria2").val("");
		$("#fornecedor").val("");
		$("#quantidade").val("0");
		$("#valorVenda").val("0.0");
		$("#quantidadeselecionado").val("0");
		$("#produtoId").val("");
		
		verificarQtd();
	});
}

$("#btnAdd").attr('disabled', true);

function verificarQtd(){
	if($("#quantidade").val() != "0" ){
		$("#btnAdd").attr('disabled', false);
	}else{
		$("#btnAdd").attr('disabled', true);
	}
}

$("#quantidadeselecionado").keyup(function(event) {
	if($(this).val() == 0 || $("#quantidade").val() == "0"){
		$("#btnAdd").attr('disabled', true);
	}else{
		$("#btnAdd").attr('disabled', false);
	}
});

function printdiv(printpage) {
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;

	window.print();
	document.body.innerHTML = oldstr;
	return false;
}


	
	