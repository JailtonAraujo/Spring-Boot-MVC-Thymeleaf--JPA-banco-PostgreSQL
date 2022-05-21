document.querySelector('#formFone').addEventListener('submit',(e)=>{
	e.preventDefault();
	
	let inputfone = document.querySelector('#foneNumber');
	var foneNumber = inputfone.value;
	let regExp = /^[0-9]{11}$/;
	
	foneNumber = inputfone.value.replace(/\D/g,'');
	
	if(regExp.test(foneNumber) == true){
		document.querySelector('#formFone').submit();
	}else{
		document.querySelector('.valid > span').textContent = 'Numero de Telefone inválido';
		document.querySelector('.valid > span').style.color = 'red';
	}
	
	
	
})



document.querySelector('#foneNumber').addEventListener('keypress', (e)=>{
	let fone = document.querySelector('#foneNumber');
	fone.value = fone.value.replace( /^(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");

})