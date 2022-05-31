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
			.then(response => {response.json()
				.then(json => {

				document.querySelector('#rua').value = json.logradouro;
				document.querySelector('#bairro').value = json.bairro;
				document.querySelector('#cidade').value = json.localidade;
				document.querySelector('#uf').value = json.uf;
				
				})
			}).catch(error => alert("Cep inválido!"))

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



let imgPefil = document.querySelector('#imgPerfil');
let fotoPerfil = document.querySelector('#fotoPerfil');

imgPefil.addEventListener('click', (e)=>{
	fotoPerfil.click();
})

fotoPerfil.addEventListener('change', (e) =>{
	let reader = new FileReader();
	reader.onload = () =>{
		imgPefil.src = reader.result;
	}
	reader.readAsDataURL(fotoPerfil.files[0]);
})

