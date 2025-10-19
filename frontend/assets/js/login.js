document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");

  form.addEventListener("submit", function (event) {
    event.preventDefault();
    if (!form.checkValidity()) {
      event.stopPropagation();
    } else {
      const email = document.getElementById("email").value.trim();
      const password = document.getElementById("password").value.trim();

      if (email === "" || password === "") {
        alert("Por favor, completa todos los campos.");
        return;
      }

      // Simulación: Redirigir según rol
      if (email.includes("nutri")) {
        window.location.href = "dashboard-nutricionista.html";
      } else {
        window.location.href = "dashboard-cliente.html";
      }
    }
    form.classList.add("was-validated");
  });
});
