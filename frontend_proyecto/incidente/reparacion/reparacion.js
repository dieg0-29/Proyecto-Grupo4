// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    // Agrega un listener para el evento de envío del formulario
    document.getElementById('reparacionForm').addEventListener('submit', function(event) {
        // Previene el comportamiento por defecto del formulario
        event.preventDefault();
        const id = localStorage.getItem('id');
        const id_i= localStorage.getItem('id_i');
        // Captura los valores de los campos del formulario
        const idempleado = id;
        const idincidente = id_i;
        const idtaller = document.getElementById('id_taller').value;
        const fechareparacion = document.getElementById('fecha_reparacion').value;
        const calificacion = document.getElementById('calificacion').value;
        const costo = document.getElementById('costo').value;
        const detalle = document.getElementById('detalle').value;

        // Crea un objeto con los datos a enviar
        const data = {
            idEmpleado: idempleado, // Asegúrate de que las claves coincidan con lo que espera tu API
            idIncidente: idincidente,
            idTaller: idtaller,
            fechaReparacion: fechareparacion,
            calificacion: calificacion,
            costo: costo,
            detalle: detalle,
        };

        // Envía los datos al servidor usando fetch
        fetch('http://localhost:8080/api/reparacion/registrar', {
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
                    // Extrae solo el mensaje del error
                    const errorMessage = JSON.parse(text).message || 'Error desconocido';
                    throw new Error(errorMessage);
                });
            }
            return response.json(); // Parsear la respuesta como JSON
        })
        .then(data => {
            // Muestra solo el mensaje del backend
            mostrarMensaje(data.message || 'Reparación registrada exitosamente.');
            // Limpiar el formulario después del envío
            document.getElementById('reparacionForm').reset();
        })
        .catch(error => {
            // Muestra solo el mensaje de error del backend
            mostrarMensaje(error.message);
        });
    });
});

// Función para mostrar mensajes
function mostrarMensaje(mensaje) {
    const mensajeElement = document.getElementById('message'); // Asegúrate de que el ID sea correcto
    mensajeElement.innerText = mensaje; // Establece el mensaje
    mensajeElement.style.display = 'block'; // Asegúrate de que el mensaje sea visible
}