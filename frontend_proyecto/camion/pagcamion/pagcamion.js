
async function obtenerDatos() {
    const url = 'http://localhost:8080/api/carro/lista'; // Reemplaza con tu URL de API

    try {
        const response = await fetch(url);

        // Verifica si la respuesta es exitosa (código 200-299)
        if (!response.ok) {
            throw new Error(`Error en la red: ${response.status} ${response.statusText}`);
        }

        // Convierte la respuesta a JSON
        const data = await response.json();

        // Muestra los datos en la tabla
        mostrarDatos(data);

    } catch (error) {
        console.error('Hubo un problema con la petición Fetch:', error);
        alert('Error al cargar los datos.'); // Mensaje de error
    }
}

function mostrarDatos(datos) {
    const tablaBody = document.querySelector('#tablaDatos tbody');
    tablaBody.innerHTML = ''; // Limpiar la tabla antes de agregar nuevos datos

    // Asumiendo que 'datos' es un array de objetos
    datos.forEach(item => {
        const fila = document.createElement('tr');

        // Crear celdas para cada propiedad del objeto
        fila.innerHTML = `
            <td>${item.id_carro}</td>
            <td>${item.estado}</td>
            <td>${item.placa}</td>
            <td>${item.prox_mant}</td>
            <td><button class="button" onclick="registrar_mant(${item.id_carro})">Registrar Mantenimiento</button></td>
            <td><button class="button" onclick="editarCarro(${item.id_carro},this)">Editar</button></td>
            <td><button class="button" onclick="eliminarCarro('${item.placa}')">Eliminar</button></td>
        `;

        tablaBody.appendChild(fila);
    });
}
async function registrar_mant(id, button) {
    localStorage.setItem('id_carro', id);
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/mantenimiento/mantenimiento.html';
}
async function editarCarro(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td");

    // Cambiar cada celda (excepto la de ID y los botones) a un input
    for (let i = 1; i < 4; i = i + 2) { 
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
    button.setAttribute("onclick", `guardarCarro(${id}, this)`);
}
async function guardarCarro(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td"); // Obtener todas las celdas de la fila

    // Crear el objeto updatedData
    const updatedData = {
        idEstado: cells[1].getElementsByTagName("input")[0].value, // Obtener el valor del input
        placa: cells[2].innerText, // Obtener el valor de la celda directamente
        proxMant: cells[3].getElementsByTagName("input")[0].value, // Obtener el valor del input
    };

    // Validar idEstado
    if (updatedData.idEstado != 4 && updatedData.idEstado != 1) {
        alert("idEstado solo puede ser cambiado a 4: Fuera de Servicio o 1: Disponible");
        return; // Salir de la función si la validación falla
    }

    // Validar proxMant
    if (updatedData.proxMant === '') {
        alert("Por favor, completa todos los campos correctamente.");
        return;
    }
    console.log("Datos a enviar:", updatedData); // Log de los datos a enviar

    try {
        const response = await fetch(`http://localhost:8080/api/carro/editar`, { // Cambiar a la URL correcta
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedData) // Enviar updatedData en el cuerpo
        });

        console.log("Respuesta del servidor:", response); // Log de la respuesta del servidor

        if (!response.ok) {
            const errorText = await response.text(); // Leer el cuerpo de la respuesta
            console.error("Error en la respuesta del servidor:", errorText); // Log del error del servidor
            throw new Error(`Error en la actualización: ${response.status} ${response.statusText} - ${errorText}`);
        }

        // Actualiza la tabla con los nuevos datos
        console.log("Actualizando la tabla con los nuevos datos:", updatedData); // Log antes de actualizar la tabla
        mostrarDatos([updatedData]);

        // Cambiar el botón de vuelta a "Editar"
        button.innerHTML = "Editar";
        button.setAttribute("onclick", `editarCarro(${id}, this)`);
        console.log("El botón ha sido cambiado de vuelta a 'Editar'."); // Log de cambio de botón
    } catch (error) {
        console.error('Hubo un problema con la actualización:', error); // Log del error en la actualización
        alert('Error al actualizar el carro.');
    }
}
obtenerDatos()
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
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/camion/camion.html';
};
const username = localStorage.getItem('name');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}
async function eliminarCarro(placa) {
    const mensajeError = document.getElementById('mensaje-error'); // Obtén el elemento para mostrar el mensaje
    mensajeError.textContent = ''; // Limpia cualquier mensaje anterior

    try {
        const response = await fetch(`http://localhost:8080/api/carro/eliminar/${placa}`, { // Corregido aquí
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Verifica si la respuesta no es exitosa
        if (!response.ok) {
            // Intenta obtener el mensaje de error del cuerpo de la respuesta
            const errorData = await response.json();
            throw new Error(`Error al eliminar el carro: ${errorData.message || response.statusText}`);
        }

        // Si la eliminación fue exitosa, puedes manejar la respuesta aquí
        const data = await response.json();
        console.log('Carro eliminado exitosamente:', data);
        mensajeError.textContent = 'Carro eliminado exitosamente.'; // Muestra un mensaje de éxito
    } catch (error) {
        // Muestra el mensaje de error específico en la página
        mensajeError.textContent = error.message; // Muestra el mensaje de error en el elemento
        console.error('Hubo un problema con la eliminación:', error.message);
    }
}