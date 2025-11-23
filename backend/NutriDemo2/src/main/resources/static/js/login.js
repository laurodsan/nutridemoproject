document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");

  form.addEventListener("submit", function (event) {
    if (!form.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      // No prevenimos el submit, dejamos que Spring Boot lo maneje
      // Solo podemos agregar validación extra si queremos
      const email = document.getElementById("email").value.trim();
      const password = document.getElementById("password").value.trim();

      if (email === "" || password === "") {
        event.preventDefault(); // si algún campo está vacío
        alert("Por favor, completa todos los campos.");
        return;
      }
    }
    form.classList.add("was-validated");
  });
});

