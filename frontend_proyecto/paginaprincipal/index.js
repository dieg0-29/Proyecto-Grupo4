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
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/mantenimiento/pagmante/pagmant.html';
};

document.getElementById('camionesButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/camion/pagcamion/pagcamion.html';
};

document.getElementById('conductoresButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/conductor/pagconductor/pagconductor.html';
};

document.getElementById('tallerButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/taller/pagtaller/pagtaller.html';
};

document.getElementById('rutaButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/ruta/pagruta/pagruta.html';
};
// Mostrar el nombre de usuario si está guardado en localStorage
const username = localStorage.getItem('name');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}