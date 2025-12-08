// Alternar formularios
const btnCliente = document.getElementById("btnCliente");
const btnNutricionista = document.getElementById("btnNutricionista");
const formCliente = document.getElementById("formCliente");
const formNutricionista = document.getElementById("formNutricionista");

btnCliente.addEventListener("click", () => {
	formCliente.classList.remove("d-none");
	formNutricionista.classList.add("d-none");

	btnCliente.classList.add("btn-primary");
	btnCliente.classList.remove("btn-outline-primary");

	btnNutricionista.classList.add("btn-outline-secondary");
	btnNutricionista.classList.remove("btn-secondary");
});

btnNutricionista.addEventListener("click", () => {
	formNutricionista.classList.remove("d-none");
	formCliente.classList.add("d-none");

	btnNutricionista.classList.add("btn-secondary");
	btnNutricionista.classList.remove("btn-outline-secondary");

	btnCliente.classList.add("btn-outline-primary");
	btnCliente.classList.remove("btn-primary");
});

// Validación Bootstrap
(() => {
	'use strict';
	const forms = document.querySelectorAll('.needs-validation');

	Array.from(forms).forEach(form => {
		form.addEventListener('submit', event => {
			if (!form.checkValidity()) {
				event.preventDefault();
				event.stopPropagation();
			}
			form.classList.add('was-validated');
		});
	});
})();


