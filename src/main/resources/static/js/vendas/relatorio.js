function printdiv(printpage){
var headstr = "<html><head><title></title></head><body><div>";
var footstr = "</div></body>";
var newstr = document.all.item(printpage).innerHTML;
var oldstr = document.body.innerHTML;
document.body.innerHTML = headstr+newstr+footstr;

window.print(); 
document.body.innerHTML = oldstr;
return false;

}


	
	