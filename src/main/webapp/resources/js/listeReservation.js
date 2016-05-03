function initReservation() {
	$(".btn-update-reservation").click( updateReservation );
}

function updateReservation() {
	$('#idAdherent').val($(this).data("idadherent"));
	$('#idOeuvrevente').val($(this).data("idoeuvrevente"));
	$('#updateForm').submit();
}

$(document).ready(initReservation);