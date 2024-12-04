
// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    // Agrega un listener para el evento de envío del formulario
    document.getElementById('conductorForm').addEventListener('submit', function(event) {
        // Previene el comportamiento por defecto del formulario
        event.preventDefault();

        // Captura los valores de los campos del formulario
        const Nombre = document.getElementById('nombre').value.trim();
        const Apellido = document.getElementById('apellido').value.trim();
        const Dni = document.getElementById('dni').value.trim();
        const Correo = document.getElementById('correo').value.trim();
        const Telefono = document.getElementById('telefono').value.trim();
        
        // Validación de datos
        if (Nombre === '' || Apellido === '' || Dni === '' || 
            Correo === '' || Telefono === '') {
            mostrarMensaje('Por favor, complete todos los campos obligatorios.', 'error');
            return;
        }

        // Crea un objeto con los datos a enviar
        const data = {
            nombre: Nombre,
            apellido: Apellido,
            dni: Dni,
            correo: Correo,
            telefono: Telefono,
        };

        // Envía los datos al servidor usando fetch
        fetch('http://localhost:8080/api/conductor/registrar', {
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
            mostrarMensaje(data.message || 'Conductor registrado exitosamente.', 'success');
            document.getElementById('conductorForm').reset();
        })
        .catch(error => {
            mostrarMensaje(error.message, 'error');
        });
    });
});

// Función para mostrar mensajes
function mostrarMensaje(mensaje, tipo) {
    const mensajeElement = document.getElementById('error-message');
    const successElement = document.getElementById('success-message');

    // Limpiar mensajes previos
    mensajeElement.style.display = 'none';
    successElement.style.display = 'none';

    if (tipo === 'error') {
        mensajeElement.innerText = mensaje;
        mensajeElement.style.display = 'block';
    } else if (tipo === 'success') {
        successElement.innerText = mensaje;
        successElement.style.display = 'block';
    }
}