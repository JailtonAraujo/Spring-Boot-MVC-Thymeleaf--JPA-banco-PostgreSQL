$(document).ready(function () {
      $('.sidenav').sidenav();
    });
    
    $(document).ready(function() {
        M.updateTextFields();
      });
    
    $(document).ready(function(){
        $('.modal').modal();
      });
      
      
    document.querySelector('.formValidate').addEventListener('submit',(e)=>{
	e.preventDefault();
	
	let elements = document.querySelectorAll('.formValidate > .valid');
	let valid = true;
		
	for (let i = 0;i<elements.length;i++){
		 let input = elements[i].querySelector('input');
		 let span = elements[i].querySelector('span');
		 
		 let elementValue = input.value;
		 
		 if(elementValue === null || elementValue.trim() === '' || elementValue === '0'){
			span.textContent = '*Campo Obrigatorio';
			valid = false;
			break;
		}	
	}	
		if(valid == true){
			document.querySelector('.formValidate').submit();
		}
	
})