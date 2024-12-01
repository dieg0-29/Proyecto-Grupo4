document.getElementById('loginForm').addEventListener('submit', function(event) {   
    event.preventDefault(); // Evita que el formulario se envíe
    
    const username = document.getElementById('username').value; // Obtener el nombre de usuario
    localStorage.setItem('username', username); // Guardar el nombre de usuario en localStorage
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html'; 
});
