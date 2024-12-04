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
                <td><button class="button" onclick="FinalizarProgramacion(${item.id_programacion},this)">Finalizar</button></td>
                `;
        }
        tablaBody.appendChild(fila);
    });
}
async function FinalizarProgramacion(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td");

    // Cambiar cada celda (excepto la de ID y los botones) a un input
    for (let i = 6; i < cells.length - 2; i++) { // Excluye la primera celda (ID) y las últimas dos (Editar y Eliminar)
        const cell = cells[i];
        const currentValue = cell.innerHTML;

        const input = document.createElement("input");
        input.type = "text";
        input.value = currentValue;

        cell.innerHTML = ""; // Limpiar el contenido de la celda
        cell.appendChild(input); // Agregar el input
    }

    // Cambiar el botón a "Guardar"
    button.innerHTML = "Guardar";
    button.setAttribute("onclick", `guardar(${id}, this)`);
}
async function guardar(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td"); // Cambiar a "td"
    
    // Crear el objeto updatedData
    const updatedData = {
        idProgramacion: id,
        fechaFinReal: cells[6].getElementsByTagName("input")[0].value, // Cambia 'Nombre' a 'nombre'   
    };

    if (updatedData.fechaFinReal === '') {
        alert("Por favor, completa todos los campos correctamente.");
        return;
    }

    console.log("Datos a enviar:", updatedData); // Log de los datos a enviar

    try {
        const response = await fetch(`http://localhost:8080/api/finalizar/programacion`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedData)
        });

        console.log("Respuesta del servidor:", response); // Log de la respuesta del servidor

        if (!response.ok) {
            const errorText = await response.text(); // Leer el cuerpo de la respuesta
            console.error("Error en la respuesta del servidor:", errorText); // Log del error del servidor
            throw new Error(`Error en la actualización: ${response.status} ${response.statusText} - ${errorText}`);
        }

        // Actualiza la tabla con los nuevos datos
        console.log("Actualizando la tabla con los nuevos datos:", updatedData); // Log antes de actualizar la tabla
        mostrarDatos([updatedData], document.querySelector('#tablaDatos tbody'), false); // Actualiza la tabla principal

        // Cambiar el botón de vuelta a "Finalizar"
        button.innerHTML = "Finalizar";
        button.setAttribute("onclick", `FinalizarProgramacion(${id}, this)`);
        console.log("El botón ha sido cambiado de vuelta a 'Finalizar'."); // Log de cambio de botón
    } catch (error) {
        console.error('Hubo un problema con la actualización:', error); // Log del error en la actualización
        alert('Error al finalizar programación.');
    }
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
function ReportarIncidente(id_programacion) {
    // Guardar el id_programacion en localStorage
    localStorage.setItem('id_p', id_programacion);
    // Redirigir a la página de incidente
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/incidente/incidente.html';
}

// Mostrar mensaje de bienvenida
const username = localStorage.getItem('name');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`;
}

// Obtener datos iniciales al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    const url = 'http://localhost:8080/api/programacion/lista-sin-fecha-fin-real';
    const tablaBody = document.querySelector('#tablaDatos tbody');
    obtenerDatos(url, tablaBody); // Cargar datos para la tabla principal
});