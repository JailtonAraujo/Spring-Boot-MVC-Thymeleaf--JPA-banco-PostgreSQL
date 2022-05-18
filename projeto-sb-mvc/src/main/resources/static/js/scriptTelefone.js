document.querySelector('#formFone').addEventListener('submit',(e)=>{
	e.preventDefault();
	
	let inputfone = document.querySelector('#foneNumber');
	var foneNumber = inputfone.value;
	let regExp = /^[0-9]{11}$/;
	
	foneNumber = inputfone.value.replace(/\D/g,'');
	
	if(foneNumber === null || foneNumber.trim() === ''){
		document.querySelector('#validateMsg01').textContent = '*Numero Obrigatorio!'
		document.querySelector('#validateMsg01').style.color ='red';
	}else if(regExp.test(foneNumber) != true ){
		document.querySelector('#validateMsg01').textContent = '*Numero Inválido!'
		document.querySelector('#validateMsg01').style.color ='red';
	}else{
		document.querySelector('#formFone').submit();
	}
	
})

document.querySelector('#foneNumber').addEventListener('keypress', (e)=>{
	let fone = document.querySelector('#foneNumber');
	fone.value = fone.value.replace( /^(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");

})