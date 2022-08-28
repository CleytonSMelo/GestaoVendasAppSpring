	
	$('.table #remover').click( function (event) {
       event.preventDefault();
       var href = $(this).attr('href');
       $('#exampleModal #btnRemover').attr('href', href);
        $('#exampleModal').modal();
    });
		
	$(document).ready(function() {
        // $('#example').DataTable();
        $('#example').DataTable( {
            "language": {
                "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Portuguese-Brasil.json"
            }
        } );
    } );
    
    $(document).ready(function() {
    	  $(".alert").fadeTo(1, 1).removeClass('hidden');
    	  window.setTimeout(function() {
    	    $(".alert").fadeTo(500, 0).slideUp(500, function(){
    	        $(".alert").addClass('hidden');
    	    });
    	  }, 1000); 
    });
    
    function keypressed( obj , e ) {
	    var tecla = ( window.event ) ? e.keyCode : e.which;
	    var texto = document.getElementById("valorCompra").value
	    var indexvir = texto.indexOf(",")
	    var indexpon = texto.indexOf(".")
	   
	   if ( tecla == 8 || tecla == 0 )
	       return true;
	   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
	       return false;
	   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
	   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
	}
    
    var somenteNumeros = function(event) {
		  return ((event.charCode >= 48 && event.charCode <= 57))
	}
	