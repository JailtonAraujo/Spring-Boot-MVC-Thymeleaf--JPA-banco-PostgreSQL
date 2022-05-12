  $(document).ready(function () {
      $('.sidenav').sidenav();
    });
    
    $(document).ready(function() {
        M.updateTextFields();
      });
    
    $(document).ready(function(){
        $('.modal').modal();
      });
      

function salvarFone(){
	let idPerson = $('#idPerson').val();
	
	let foneNumber = $('#foneNumber').val();
	let typeNumber = $('#typeNumber').val();
	
	 $.ajax({
        url:`telefone/salvar/${idPerson}`,
        method: 'POST',
        data: {numero:foneNumber, tipo:typeNumber},
        dataType: 'json'
    }).done(function(result){
		console.log(result);
        
    });
    
      $('#formTel').trigger("reset");
}