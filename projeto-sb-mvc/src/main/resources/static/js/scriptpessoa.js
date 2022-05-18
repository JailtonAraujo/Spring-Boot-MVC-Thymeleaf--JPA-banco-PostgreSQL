
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
			valid = false;
		}
		
	}
	
	if(valid == true){
		var regExp = /^[0-9]{2,3}$/;
		
		if(regExp.test(idadeValue) != true){
			document.querySelector('#msgValidIdade').textContent = 'Idade inválida!';
		}else if(idadeValue < 18){
			document.querySelector('#msgValidIdade').textContent = 'A Idade precisa ser superior a 18 anos!';
		}
		
		else{
			document.querySelector('#formPerson').submit();
		}
	}
})


function buscarViaCep(){
	
	var cep = document.querySelector('#div-cep > input').value.replace(/\D/g,'');

		if(cep != null){
			
			document.querySelector('#div-cep > span').textContent = '';
			document.querySelector('#rua').value='...';
			document.querySelector('#bairro').value='...';
			document.querySelector('#cidade').value='...';
			document.querySelector('#uf').value='...';
		
		let regExp = /^[0-9]{8}$/;
		
		if(regExp.test(cep) === true ){
			
			const options = {
				method: 'GET',
				headers:{"Content-type": "application/json; charset=UTF-8"},
				mode:'cors',
   				cache:'default',
			}

			fetch(`https://viacep.com.br/ws/${cep}/json/`,options)
			.then(response => response.json())
			.then(function(json){

				document.querySelector('#rua').value = json.logradouro;
				document.querySelector('#bairro').value = json.bairro;
				document.querySelector('#cidade').value = json.localidade;
				document.querySelector('#uf').value = json.uf;
				
			}).catch(function(error){
				elert('Cep invalido!');
				limparInputs();
			})

		}else{
			document.querySelector('#div-cep > span').textContent = 'cep iválido!';
			limparInputs();
		}	
	}else{
			document.querySelector('#div-cep > span').textContent = 'cep iválido!';
			limparInputs();
		}
}

	

function limparInputs(){
	document.querySelector('#rua').value='';
	document.querySelector('#bairro').value='';
	document.querySelector('#cidade').value='';
	document.querySelector('#uf').value='';
}

