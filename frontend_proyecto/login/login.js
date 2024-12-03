document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !password) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    fetch(`http://localhost:8080/login?usuario=${encodeURIComponent(username)}&clave=${encodeURIComponent(password)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw new Error(err.message); });
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem('id', data.id_empleado);
        localStorage.setItem('name', data.nombre);
        window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
    })
    .catch(error => {
        alert("Error al iniciar sesión: " + error.message);
    });
});
