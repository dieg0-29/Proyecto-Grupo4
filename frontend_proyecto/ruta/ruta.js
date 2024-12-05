// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    // Agrega un listener para el evento de envío del formulario
    document.getElementById('rutaForm').addEventListener('submit', function(event) {
        // Previene el comportamiento por defecto del formulario
        event.preventDefault();

        // Captura los valores de los campos del formulario
        const nombre = document.getElementById('nombre').value;
        const origen = document.getElementById('origen').value;
        const destino = document.getElementById('destino').value;
        const distancia = document.getElementById('distancia').value;

        // Validación de datos
        if (nombre === '' || origen === '' || destino === '' || distancia === '' ) {
            mostrarMensaje('Por favor, complete todos los campos obligatorios.');
            return;
        }

        // Crea un objeto con los datos a enviar
        const data = {
            nombre : nombre,
            origen : origen,
            destino : destino,
            distancia : distancia
        };

        // Envía los datos al servidor usando fetch
        fetch('http://localhost:8080/api/ruta/registrar', {
            method: 'POST', // Método de la solicitud
            headers: {
                "Content-Type": "application/json" // Tipo de contenido
            },
            body: JSON.stringify(data) // Convierte el objeto a JSON
        })
        .then(response => {
            // Verifica si la respuesta fue exitosa
            if (!response.ok) {
                // Si la respuesta no es exitosa, lanza un error
                return response.text().then(text => {
                    throw new Error(text || 'Error en la solicitud');
                });
            }
            return response.json(); // Devuelve la respuesta en formato JSON
        })
        .then(data => {
            // Muestra un mensaje de éxito
            mostrarMensaje('Registro exitoso: ' + (data.message || 'Ruta registrada.'));
            // Resetea el formulario
            document.getElementById('rutaForm').reset();
        })
        .catch(error => {
            // Maneja errores y muestra un mensaje
            mostrarMensaje('Error: ' + error.message);
        });
    });
});

// Función para mostrar mensajes
function mostrarMensaje(mensaje) {
    const mensajeElement = document.getElementById('mensaje');
    mensajeElement.innerText = mensaje; //Establece el mensaje
    mensajeElement.style.display = 'block'; //Asegúrate de que el mensaje sea visible
}