async function obtenerDatos() {
    const url = 'http://localhost:8080/api/ruta/listar'; // Reemplaza con tu URL de API

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
            <td>${item.idRuta}</td>
            <td>${item.nombre}</td>
            <td>${item.origen}</td>
            <td>${item.destino}</td>
            <td>${item.distancia}</td>
            <td><button class="button" onclick="editarRuta(${item.idRuta}, this)">Editar</button></td>
            <td><button class="button" onclick="eliminarRuta(${item.idRuta})">Eliminar</button></td>
        `;

        tablaBody.appendChild(fila);
    });
}
obtenerDatos();
async function editarRuta(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td");

    // Cambiar cada celda (excepto la de ID y los botones) a un input
    for (let i = 1; i < cells.length - 2; i++) { // Excluye la primera celda (ID) y las últimas dos (Editar y Eliminar)
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
    button.setAttribute("onclick", `guardarRuta(${id}, this)`);
}

async function guardarRuta(id, button) {
    const row = button.parentNode.parentNode; // Obtener la fila
    const cells = row.getElementsByTagName("td"); // Cambiar a "td"

    // Crear el objeto updatedData
    const updatedData = {
        idRuta: id,
        nombre: cells[1].getElementsByTagName("input")[0].value, // Cambia 'Nombre' a 'nombre'
        origen: cells[2].getElementsByTagName("input")[0].value, // Cambia 'Origen' a 'origen'
        destino: cells[3].getElementsByTagName("input")[0].value, // Cambia 'Destino' a 'destino'
        distancia: parseFloat(cells[4].getElementsByTagName("input")[0].value), // Cambia 'Distancia' a 'distancia'
        
    };
    if (updatedData.nombre === '' || updatedData.origen === '' || updatedData.destino === '' || isNaN(updatedData.distancia) || updatedData.distancia <= 0) {
        alert("Por favor, completa todos los campos correctamente.");
        return;
    }
    console.log(updatedData);
    console.log("Datos a enviar:", updatedData); // Log de los datos a enviar

    try {
        const response = await fetch(`http://localhost:8080/api/ruta/modificar/${id}`, {
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
        button.setAttribute("onclick", `editarRuta(${id}, this)`);
        console.log("El botón ha sido cambiado de vuelta a 'Editar'."); // Log de cambio de botón
    } catch (error) {
        console.error('Hubo un problema con la actualización:', error); // Log del error en la actualización
        alert('Error al actualizar la ruta.');
    }
}


// Llamada a la función para obtener y mostrar los datos


document.getElementById('inicioButton').addEventListener('click', () => {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
});

document.getElementById('cerrarSesionButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/login/login.html';
};

document.getElementById('nuevoRegistroButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/ruta/ruta.html';
};

const username = localStorage.getItem('name');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}
async function eliminarRuta(id) {
    const mensajeError = document.getElementById('mensaje-error'); // Obtén el elemento para mostrar el mensaje
    mensajeError.textContent = ''; // Limpia cualquier mensaje anterior

    try {
        const response = await fetch(`http://localhost:8080/api/ruta/borrar/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Verifica si la respuesta no es exitosa
        if (!response.ok) {
            // Intenta obtener el mensaje de error del cuerpo de la respuesta
            const errorData = await response.json();
            throw new Error(`Error al eliminar la ruta: ${errorData.message || response.statusText}`);
        }

        // Si la eliminación fue exitosa, puedes manejar la respuesta aquí
        const data = await response.json();
        console.log('Ruta eliminada exitosamente:', data);
        mensajeError.textContent = 'Ruta eliminada exitosamente.'; // Muestra un mensaje de éxito
    } catch (error) {
        // Muestra el mensaje de error específico en la página
        mensajeError.textContent = error.message; // Muestra el mensaje de error en el elemento
        console.error('Hubo un problema con la eliminación:', error.message);
    }
}