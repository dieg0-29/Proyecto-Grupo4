
// Datos de ejemplo para llenar la tabla
const datos = [
    ["Camión 1", "Conductor 1", "Ruta A", "10:00", "20 km", "Editar", "Eliminar"],
    ["Camión 2", "Conductor 2", "Ruta B", "11:00", "15 km", "Editar", "Eliminar"],
    ["Camión 3", "Conductor 3", "Ruta C", "12:00", "25 km", "Editar", "Eliminar"],
    ["Camión 4", "Conductor 4", "Ruta D", "13:00", "30 km", "Editar", "Eliminar"],
    ["Camión 5", "Conductor 5", "Ruta E", "14:00", "10 km", "Editar", "Eliminar"],
];

// Obtener la referencia a la tabla
const tabla = document.getElementById('tablaDatos').getElementsByTagName('tbody')[0];

// Llenar la tabla con los datos
datos.forEach((fila) => {
    const newRow = tabla.insertRow();
    
    fila.forEach((dato) => {
        const newCell = newRow.insertCell();
        newCell.textContent = dato;
    });
    
    // Agregar botones de acción
    const editarCell = newRow.insertCell();
    const eliminarCell = newRow.insertCell();
    
    const editarButton = document.createElement('button');
    editarButton.textContent = 'Editar';
    editarButton.className = 'button';
    editarButton.onclick = () => {
        alert('Funcionalidad de editar no implementada.');
    };
    
    const eliminarButton = document.createElement('button');
    eliminarButton.textContent = 'Eliminar';
    eliminarButton.className = 'button';
    eliminarButton.onclick = () => {
        if (confirm('¿Estás seguro de que deseas eliminar esta fila?')) {
            tabla.deleteRow(newRow.rowIndex - 1); // Eliminar la fila
        }
    };
    
    editarCell.appendChild(editarButton);
    eliminarCell.appendChild(eliminarButton);
});

document.getElementById('inicioButton').addEventListener('click', () => {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
});

//document.getElementById('servicioButton').onclick = function() {
    //window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/Paginaprincipal/servicios.html';
//};

document.getElementById('cerrarSesionButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/login/login.html';
};
document.getElementById('nuevoRegistroButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal';
};
const username = localStorage.getItem('username');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}