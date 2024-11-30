
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
document.getElementById('mostrarTablaButton').addEventListener('click', function() {
    const tablaSecundaria = document.getElementById('tablaSecundaria');
    const tbody = tablaSecundaria.querySelector('tbody');

    // Limpiar el contenido previo de la tabla
    tbody.innerHTML = '';

    // Agregar datos a la tabla
    const datos = [
        ['Dato A1', 'Dato B1', 'Dato C1', 'Dato D1', 'Dato E1'],
        ['Dato A2', 'Dato B2', 'Dato C2', 'Dato D2', 'Dato E2'],
        ['Dato A3', 'Dato B3', 'Dato C3', 'Dato D3', 'Dato E3']
    ];

    datos.forEach(fila => {
        const tr = document.createElement('tr');
        fila.forEach(dato => {
            const td = document.createElement('td');
            td.textContent = dato;
            tr.appendChild(td);
        });
        tbody.appendChild(tr);
    });

    // Mostrar la tabla
    if (tablaSecundaria.classList.contains('table-hidden')) {
        tablaSecundaria.classList.remove('table-hidden');
        tablaSecundaria.classList.add('table-visible');
    } else {
        tablaSecundaria.classList.remove('table-visible');
        tablaSecundaria.classList.add('table-hidden');
    }
});


document.getElementById('inicioButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
};

//document.getElementById('serviciosButton').onclick = function() {
    //window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/Paginaprincipal/servicios.html';
//};

document.getElementById('cerrarSesionButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/login/login.html';
};
document.getElementById('nuevoRegistroButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/mantenimiento/mantenimiento.html';
};
const username = localStorage.getItem('username');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}