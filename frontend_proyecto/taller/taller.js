document.addEventListener('DOMContentLoaded', function() {
    // Agrega un listener para el evento de envío del formulario
    document.getElementById('tallerForm').addEventListener('submit', function(event) {
        // Previene el comportamiento por defecto del formulario
        event.preventDefault();

        // Captura los valores de los campos del formulario
        const nombre = document.getElementById('nombre').value;
        const direccion = document.getElementById('direccion').value;
        const telefono = document.getElementById('telefono').value;

        // Validación de datos
        if (nombre === '' || direccion === '' || telefono === '' ) {
            mostrarMensaje('Por favor, complete todos los campos obligatorios.');
            return;
        }

        // Crea un objeto con los datos a enviar
        const data = {
            nombre : nombre,
            direccion : direccion,
            telefono : telefono,
        };

        // Envía los datos al servidor usando fetch
        fetch('http://localhost:8080/api/taller/registrar', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || 'Error en la solicitud');
                });
            }
            return response.json();
        })
        .then(data => {
            mostrarMensaje('Registro exitoso: ' + (data.message || 'Taller registrado.'));
            document.getElementById('tallerForm').reset(); // Asegúrate de que el ID sea correcto
        })
        .catch(error => {
            mostrarMensaje('Error: ' + error.message);
        });
    });
});
function mostrarMensaje(mensaje) {
    const mensajeElement = document.getElementById('mensaje');
    mensajeElement.innerText = mensaje; // Establece el mensaje
    mensajeElement.style.display = 'block'; // Asegúrate de que el mensaje sea visible
}