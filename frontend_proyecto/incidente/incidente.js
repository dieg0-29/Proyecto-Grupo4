// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    // Agrega un listener para el evento de envío del formulario
    document.getElementById('incidenteForm').addEventListener('submit', function(event) {
        // Previene el comportamiento por defecto del formulario
        event.preventDefault();
        const id = localStorage.getItem('id');
        const id_p = localStorage.getItem('id_p');

        if (id !== null && id_p !== null) {
            console.log(`User  ID: ${id}, Parent ID: ${id_p}`);
        } else {
            console.log('ID or Parent ID not found in local storage.');
        }   
        // Captura los valores de los campos del formulario
        const empleado = id;
        const programacion = id_p;
        const tipoIncidente = document.getElementById('tipo_incidente').value.trim();
        const fecha = document.getElementById('fecha').value.trim();
        const detalle = document.getElementById('detalle').value.trim();

        // Validación de datos
        if (!validarCampos(empleado, programacion, tipoIncidente, fecha, detalle)) {
            mostrarMensaje('Por favor, complete todos los campos obligatorios.', 'error');
            return;
        }

        // Crea un objeto con los datos a enviar
        const data = { empleado, programacion, tipoIncidente, fecha, detalle };

        // Envía los datos al servidor
        enviarDatos(data);
    });
});

// Función para validar los campos
function validarCampos(...campos) {
    return campos.every(campo => campo !== '');
}

// Función para enviar datos al servidor
function enviarDatos(data) {
    fetch('http://localhost:8080/api/incidente/registrar', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(data => {
                throw new Error(data.message || 'Error en la solicitud');
            });
        }
        return response.json();
    })
    .then(data => {
        // Muestra solo el mensaje del backend
        mostrarMensaje(data.message || 'Incidente registrado exitosamente.', 'success');
        document.getElementById('incidenteForm').reset();
    })
    .catch(error => {
        // Muestra solo el mensaje de error del backend
        mostrarMensaje(error.message, 'error');
    });
}

// Función para mostrar mensajes
function mostrarMensaje(mensaje, tipo) {
    const errorMessageElement = document.getElementById('error-message');
    const successMessageElement = document.getElementById('success-message');

    // Oculta ambos mensajes al inicio
    errorMessageElement.style.display = 'none';
    successMessageElement.style.display = 'none';

    // Muestra el mensaje correspondiente
    if (tipo === 'error') {
        errorMessageElement.innerText = mensaje;
        errorMessageElement.style.display = 'block';
    } else if (tipo === 'success') {
        successMessageElement.innerText = mensaje;
        successMessageElement.style.display = 'block';
    }
}