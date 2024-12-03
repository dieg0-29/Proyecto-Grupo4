document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita que el formulario se envíe

    const username = document.getElementById('username').value; // Obtener el nombre de usuario
    const password = document.getElementById('password').value; // Obtener la contraseña

    // Realizar la solicitud al backend
    fetch(`http://localhost:8080/login?usuario=${encodeURIComponent(username)}&clave=${encodeURIComponent(password)}`, {
        method: 'GET', // Método de la solicitud
        headers: {
            'Content-Type': 'application/json' // Tipo de contenido
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw new Error(err); }); // Manejar errores
        }
        return response.json(); // Convertir la respuesta a JSON
    })
    .then(data => {
        // Guardar el id y el nombre del empleado en localStorage
        localStorage.setItem('id', data.id_empleado); // Guardar el id de empleado
        localStorage.setItem('name', data.nombre); // Guardar el nombre del empleado
        // Redirigir a la página principal
        window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
    })
    .catch(error => {
        alert(error.message); // Mostrar un mensaje de error
    });
});