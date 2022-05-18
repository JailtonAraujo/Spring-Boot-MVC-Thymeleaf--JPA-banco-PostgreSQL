
document.querySelector('#formPerson').addEventListener('submit',(e)=>{
	e.preventDefault();
	
	let elements = document.querySelectorAll('#formPerson > .valid');
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
			document.querySelector('#formPerson').submit();
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
				
			}).catch(error => {
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

