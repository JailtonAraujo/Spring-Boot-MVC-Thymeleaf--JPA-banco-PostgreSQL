
document.querySelector('#formPerson').addEventListener('submit',(e)=>{
	e.preventDefault();
	
	let elements = document.querySelectorAll('#formPerson > div');
	let valid = true;
	let idadeValue = document.querySelector('#idade').value;
		
	for (let i = 0;i<elements.length;i++){
		 let input = elements[i].querySelector('.valid > input');
		 let span = elements[i].querySelector('.valid > span');
		 
		 let elementValue = input.value;
		 
		 if(elementValue === null || elementValue.trim() === ''){
			span.textContent = '*Campo Obrigatorio';
			span.style.color = 'red';
			valid = false;
		}
		
	}
	
	if(valid == true){
		var regExp = /^[0-9]{2,3}$/;
		
		if(regExp.test(idadeValue) != true){
			document.querySelector('#msgValidIdade').textContent = 'Idade inválida!';
			document.querySelector('#msgValidIdade').style.color = 'red';
		}else if(idadeValue < 18){
			document.querySelector('#msgValidIdade').textContent = 'A Idade precisa ser superior a 18 anos!';
			document.querySelector('#msgValidIdade').style.color = 'red';
		}
		
		else{
			document.querySelector('#formPerson').submit();
		}
	}
})

function buscarViaCep(){
	
	let divCep = document.querySelector('.cep');
	let inputCep = divCep.querySelector('input');

	if(inputCep.value == null || inputCep.value.trim() == ''){
		
		let regExp = /^[0-9]{8}$/;
		
		if(regExp.test(inputCep.value == true)){
			
			const options = {
				method: 'GET'
			}

			fetch()

		}
		
	}
}	


