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
            <td>${item.Id_ruta}</td>
            <td>${item.Nombre}</td>
            <td>${item.Origen}</td>
            <td>${item.Destino}</td>
            <td>${item.Distancia}</td>
            <td><button class="button" onclick="editarRuta(${item.Id_ruta})">Editar</button></td>
            <td><button class="button" onclick="eliminarRuta(${item.Id_ruta})">Eliminar</button></td>
        `;

        tablaBody.appendChild(fila);
    });
}

// Funciones para editar y eliminar rutas
function editarRuta(id) {
    // Lógica para editar la ruta
    console.log(`Editar ruta con ID: ${id}`);
}

function eliminarRuta(id) {
    // Lógica para eliminar la ruta
    console.log(`Eliminar ruta con ID: ${id}`);
}

// Llamada a la función para obtener y mostrar los datos
obtenerDatos();

document.getElementById('inicioButton').addEventListener('click', () => {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/paginaprincipal/index.html';
});

document.getElementById('cerrarSesionButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/login/login.html';
};

document.getElementById('nuevoRegistroButton').onclick = function() {
    window.location.href = 'http://127.0.0.1:5500/frontend_proyecto/ruta/ruta.html';
};

const username = localStorage.getItem('username');
if (username) {
    document.getElementById('welcomeMessage').innerText = `Bienvenido, ${username}!`; // Mensaje de bienvenida
}