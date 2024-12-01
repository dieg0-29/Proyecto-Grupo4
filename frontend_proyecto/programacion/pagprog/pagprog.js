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
                <td>${item.id_programacion}</td>
                <td>${item.id_carro}</td>
                <td>${item.id_empleado}</td>
                <td>${item.id_conductor}</td>
                <td>${item.id_ruta}</td>
                <td>${item.fecha_asignacion}</td>
                <td>${item.fecha_fin_programada}</td>
                <td>${item.fecha_fin_real}</td> <!-- Columna adicional -->
            `;
        } else {
            // Llenar la tabla principal
            fila.innerHTML = `
                <td>${item.id_programacion}</td>
                <td>${item.id_carro}</td>
                <td>${item.id_empleado}</td>
                <td>${item.id_conductor}</td>
                <td>${item.id_ruta}</td>
                <td>${item.fecha_asignacion}</td>
                <td>${item.fecha_fin_programada}</td>
                <td><button class="button" onclick="ReportarIncidente(${item.id_programacion})">Incidente</button></td>
                <td><button class="button" onclick="FinalizarProgramacion(${item.id_programacion})">Finalizar</button></td>
                `;
        }
        tablaBody.appendChild(fila);
    });
}

// Evento para mostrar/ocultar la tabla secundaria
document.getElementById('mostrarTablaButton').addEventListener('click', function() {
    const tablaSecundaria = document.getElementById('tablaSecundaria');
    const tablaBody = document.querySelector('#tablaSecundaria tbody');
    const url = 'http://localhost:8080/api/programacion/lista-con-fecha-fin-real';

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

document.getElementById('nuevoRegistroButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/programacion/registrarprogramacion/prog.html';
};

// Mostrar mensaje de bienvenida
const username = localStorage.getItem('username');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`;
}

// Obtener datos iniciales al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    const url = 'http://localhost:8080/api/programacion/lista-sin-fecha-fin-real';
    const tablaBody = document.querySelector('#tablaDatos tbody');
    obtenerDatos(url, tablaBody); // Cargar datos para la tabla principal
});