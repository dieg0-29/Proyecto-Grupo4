// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    // Agrega un listener para el evento de envío del formulario
    document.getElementById('mantenimientoForm').addEventListener('submit', function(event) {
        // Previene el comportamiento por defecto del formulario
        event.preventDefault();

        // Captura los valores de los campos del formulario
        const id_empleado = document.getElementById('id_empleado').value;
        const id_carro = document.getElementById('id_carro').value;
        const id_taller = document.getElementById('id_taller').value;
        const calificacion = document.getElementById('calificacion').value;
        const fecha_inicio = document.getElementById('fecha_inicio').value;
        const fecha_salida_programada = document.getElementById('fecha_salida_programada').value;
        const costo = document.getElementById('costo').value;
        const detalle = document.getElementById('detalle').value;

        // Crea un objeto con los datos a enviar
        const data = {
            id_empleado: id_empleado,
            id_carro: id_carro,
            id_taller: id_taller,
            calificacion: calificacion,
            fecha_inicio: fecha_inicio,
            fecha_salida_programada: fecha_salida_programada,
            costo: costo,
            detalle: detalle
        };

        // Envía los datos al servidor usando fetch
        fetch('http://localhost:8080/api/registrar/mantenimiento', {
            method: 'POST', // Método de la solicitud
            headers: {
                'Content-Type': 'application/json' // Tipo de contenido
            },
            body: JSON.stringify(data) // Convierte el objeto a JSON
        })
        .then(response => {
            // Verifica si la respuesta es exitosa
            if (!response.ok) {
                // Si no es exitosa, intenta convertir la respuesta a JSON
                return response.json().then(err => {
                    // Lanza un error con los mensajes de validación
                    throw new Error(err.errors.join(', '));
                });
            }
            return response.json(); // Convierte la respuesta a JSON
        })
        .then(data => {
            // Muestra un mensaje de éxito
            document.getElementById('mensaje').innerText = 'Mantenimiento registrado con éxito';
            // Resetea el formulario
            document.getElementById('mantenimientoForm').reset();
        })
        .catch(error => {
            // Maneja errores y muestra un mensaje
            document.getElementById('mensaje').innerText = 'Error: ' + error.message;
        });
    });
});