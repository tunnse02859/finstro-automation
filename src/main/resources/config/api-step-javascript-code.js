function toggleDetail(element) {
	element.classList.toggle("active");
	if(element.nextElementSibling.style.display === "none"){
		element.nextElementSibling.style.display = "block";
	}else{
		element.nextElementSibling.style.display = "none";
	}
}

function openBlock(evt, blockName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  var block = document.getElementsByClassName(blockName);
  for (i = 0; i < block.length; i++) {
    block[i].style.display = "block";
	block[i].className += " active";
  }
}