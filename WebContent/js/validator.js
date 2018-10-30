$.validator.addMethod("dateValid", function(value, element, params) {
	if(value === '' || $(params).val() === ''){
		return true
	}
	
	if (!/Invalid|NaN/.test(new Date(value))) {
		return new Date(value) > new Date($(params).val());
	}

	return isNaN(value) && isNaN($(params).val())
			|| (Number(value) > Number($(params).val()));
}, 'Erreur de saisie');

$(document).ready(function() {
	$("#addForm").validate({
		rules : {
			computerName : {
				required : true
			},
			discontinued: { dateValid: "#introduced",required : false }
		},
		messages : {
			computerName : "Champ requis",
			//discontinued : "La date doit être postérieur à celui de la date d'introduction"
		}
	});

	$("#editForm").validate({
		rules : {
			computerName : {
				required : true
			},
			discontinued: { dateValid: "#introduced",required : false }
		},
		messages : {
			computerName : "Champ requis",
			discontinued : "La date doit être postérieur à celui de la date d'introduction"
		}
	});

});
