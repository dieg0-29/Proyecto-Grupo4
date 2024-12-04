
async function obtenerDatos() {
    const url = 'http://localhost:8080/api/taller/listar'; // Reemplaza con tu URL de API

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
            <td>${item.idTaller}</td>
            <td>${item.nombre}</td>
            <td>${item.direccion}</td>
            <td>${item.telefono}</td>
            <td>${item.calificacion}</td>
            <td><button class="button" onclick="editarTaller(${item.idTaller},this)">Editar</button></td>
            <td><button class="button" onclick="eliminarTaller(${item.idTaller})">Eliminar</button></td>
        `;

        tablaBody.appendChild(fila);
    });
}

async function editarTaller(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td");

    // Cambiar cada celda (excepto la de ID y los botones) a un input
    for (let i = 1; i < cells.length - 3; i++) { // Excluye la primera celda (ID) y las últimas dos (Editar y Eliminar)
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
    button.setAttribute("onclick", `guardarTaller(${id}, this)`);
}

async function guardarTaller(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td"); // Cambiar a "td"
    // Crear el objeto updatedData
    const updatedData = {
        idTaller: id,
        nombre: cells[1].getElementsByTagName("input")[0].value, // Cambia 'Nombre' a 'nombre'
        direccion: cells[2].getElementsByTagName("input")[0].value, // Cambia 'Origen' a 'origen'
        telefono: cells[3].getElementsByTagName("input")[0].value, // Cambia 'Destino' a 'destino'

        
    };
    if (updatedData.nombre === '' || updatedData.direccion === '' || updatedData.telefono === '') {
        alert("Por favor, completa todos los campos correctamente.");
        return;
    }
    console.log(updatedData);
    console.log("Datos a enviar:", updatedData); // Log de los datos a enviar

    try {
        const response = await fetch(`http://localhost:8080/api/taller/modificar/${id}`, {
            method: 'PUT',
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
        mostrarDatos([updatedData]);

        // Cambiar el botón de vuelta a "Editar"
        button.innerHTML = "Editar";
        button.setAttribute("onclick", `editarTaller(${id}, this)`);
        console.log("El botón ha sido cambiado de vuelta a 'Editar'."); // Log de cambio de botón
    } catch (error) {
        console.error('Hubo un problema con la actualización:', error); // Log del error en la actualización
        alert('Error al actualizar la taller.');
    }
}
async function eliminarTaller(id) {
    const mensajeError = document.getElementById('mensaje-error'); // Obtén el elemento para mostrar el mensaje
    mensajeError.textContent = ''; // Limpia cualquier mensaje anterior

    try {
        const response = await fetch(`http://localhost:8080/api/taller/borrar/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Verifica si la respuesta no es exitosa
        if (!response.ok) {
            // Intenta obtener el mensaje de error del cuerpo de la respuesta
            const errorData = await response.json();
            throw new Error(`Error al eliminar la taller: ${errorData.message || response.statusText}`);
        }

        // Si la eliminación fue exitosa, puedes manejar la respuesta aquí
        const data = await response.json();
        console.log('Taller eliminado exitosamente:', data);
        mensajeError.textContent = 'Taller eliminado exitosamente.'; // Muestra un mensaje de éxito
    } catch (error) {
        // Muestra el mensaje de error específico en la página
        mensajeError.textContent = error.message; // Muestra el mensaje de error en el elemento
        console.error('Hubo un problema con la eliminación:', error.message);
    }
}

// Llamada a la función para obtener y mostrar los datos
obtenerDatos();


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
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/taller/taller.html';
};
const username = localStorage.getItem('name');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}