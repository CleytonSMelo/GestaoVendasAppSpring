function printdiv(printpage){
var headstr = "<div>";
var footstr = "</div>";
var newstr = document.all.item(printpage).innerHTML;
var oldstr = document.body.innerHTML;
document.body.innerHTML = headstr+newstr+footstr;

window.print(); 
document.body.innerHTML = oldstr;
return false;

}


	
	