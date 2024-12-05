async function obtenerDatos(url, tablaBody, isSecundaria = false) {
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error en la red: ${response.status} ${response.statusText}`);
        }
        const data = await response.json();
        mostrarDatos(data, tablaBody, isSecundaria);
    } catch (error) {
        console.error('Hubo un problema con la petición Fetch:', error);
        alert('Error al cargar los datos.');
    }
}

function mostrarDatos(datos, tablaBody, isSecundaria) {
    tablaBody.innerHTML = ''; // Limpiar la tabla antes de agregar nuevos datos

    datos.forEach(item => {
        const fila = document.createElement('tr');
        if (isSecundaria) {
            // Llenar la tabla secundaria con la columna adicional "Fecha Fin Real"
            fila.innerHTML = `
                <td>${item.id_empleado}</td>
                <td>${item.id_incidente}</td>
                <td>${item.id_taller}</td>
                <td>${item.fecha_reparacion}</td>
                <td>${item.calificacion}</td>
                <td>${item.costo}</td>
                <td>${item.detalle}</td>
            `;
        } else {
            // Llenar la tabla principal
            fila.innerHTML = `
                <td>${item.Id_incidente}</td>
                <td>${item.Empleado}</td>
                <td>${item.Programacion}</td>
                <td>${item.Tipo}</td>
                <td>${item.Fecha}</td>
                <td>${item.Detalle}</td>
                <td><button class="button" onclick="ReportarReparacion(${item.id_incidente})">Reparacion</button></td>
                `;
        }
        tablaBody.appendChild(fila);
    });
}

// Evento para mostrar/ocultar la tabla secundaria
document.getElementById('mostrarTablaButton').addEventListener('click', function() {
    const tablaSecundaria = document.getElementById('tablaSecundaria');
    const tablaBody = document.querySelector('#tablaSecundaria tbody');
    const url = 'http://localhost:8080/api/reparacion/lista';

    // Alternar la visibilidad de la tabla secundaria
    if (tablaSecundaria.style.display === 'table') {
        tablaSecundaria.style.display = 'none'; // Ocultar la tabla
    } else {
        tablaSecundaria.style.display = 'table'; // Mostrar la tabla
        obtenerDatos(url, tablaBody, true); // Cargar datos para la tabla secundaria
    }
});

// Navegación a otras páginas
document.getElementById('inicioButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
};

document.getElementById('serviciosButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/Paginaprincipal/servicios.html';
};

document.getElementById('cerrarSesionButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/login/login.html';
};

async function ReportarReparacion(id_incidente) {
    console.log("ReportarReparacion llamado con id_incidente:", id_incidente); // Verifica que se llame correctamente
    // Guardar el id_programacion en localStorage
    localStorage.setItem('id_i', id_incidente);
    // Redirigir a la página de incidente
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/incidente/reparacion/reparacion.html';
}

// Mostrar mensaje de bienvenida
const username = localStorage.getItem('name');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`;
}
// Obtener datos iniciales al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    const url = 'http://localhost:8080/api/incidente/lista';
    const tablaBody = document.querySelector('#tablaDatos tbody');
    obtenerDatos(url, tablaBody); // Cargar datos para la tabla principal
});