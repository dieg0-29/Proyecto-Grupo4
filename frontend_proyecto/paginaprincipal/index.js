document.getElementById('inicioButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
};

document.getElementById('serviciosButton').onclick = function() {
};

document.getElementById('cerrarSesionButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/login/login.html';
};

document.getElementById('ProgramacionButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/programacion/pagprog/pagprog.html';
};

document.getElementById('MantenimientoButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/node_modules/pruebas/Pag_principal';
};

document.getElementById('camionesButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/node_modules/pruebas/Pag_principal/bicicleta.html';
};

document.getElementById('conductoresButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/node_modules/pruebas/Pag_principal/transporte_publico.html';
};

document.getElementById('tallerButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/node_modules/pruebas/Pag_principal/camion_carga.html';
};

document.getElementById('rutaButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/node_modules/pruebas/Pag_principal';
};
// Mostrar el nombre de usuario si está guardado en localStorage
const username = localStorage.getItem('username');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}