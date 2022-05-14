document.querySelector('#formFone').addEventListener('submit',(e)=>{
	e.preventDefault();
	
	let inputfone = document.querySelector('#foneNumber');
	let regExp = /^[0-9]{11}$/;
	
	if(inputfone.value === null || inputfone.value.trim() === ''){
		document.querySelector('#validateMsg01').textContent = '*Numero Obrigatorio!'
		document.querySelector('#validateMsg01').style.color ='red';
	}else if(regExp.test(inputfone.value) != true ){
		document.querySelector('#validateMsg01').textContent = '*Numero Inválido!'
		document.querySelector('#validateMsg01').style.color ='red';
	}else{
		document.querySelector('#formFone').submit();
	}
	
})
