// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    // Agrega un listener para el evento de envío del formulario
    document.getElementById('tallerForm').addEventListener('submit', function(event) {
        // Previene el comportamiento por defecto del formulario
        event.preventDefault();

        // Captura los valores de los campos del formulario
        const idEmpleado = document.getElementById('id_empleado').value;
        const idCarro = document.getElementById('id_carro').value;
        const idConductor = document.getElementById('id_conductor').value;
        const idRuta = document.getElementById('id_ruta').value;
        const fechaAsig = document.getElementById('fecha_inicio').value; 
        const fechaFin = document.getElementById('fecha_salida_programada').value;

        // Validación de datos
        if (id_empleado === '' || id_conductor === '' || id_carro === '' || id_ruta === '' || fecha_inicio === '' || fecha_salida_programada === '') {
            mostrarMensaje('Por favor, complete todos los campos obligatorios.');
            return;
        }

        // Convertir a formato DD/MM/YYYY
        const convertirFecha = (fecha) => {
            const [year, month, day] = fecha.split('-'); // Dividir la fecha en partes
            return `${day}/${month}/${year}`; // Reorganizar en formato DD/MM/YYYY
        };

        const fecha_inicio_formateada = convertirFecha(fecha_inicio);
        const fecha_salida_programada_formateada = convertirFecha(fecha_salida_programada);

        // Crea un objeto con los datos a enviar
        const data = {
            idEmpleado: idEmpleado,
            idConductor: idConductor,
            idCarro: idCarro,
            idRuta: idRuta,
            fechaAsig: fechaAsig,
            fechaFin: fechaFin,
        };

        // Envía los datos al servidor usando fetch
        fetch('http://localhost:8080/api/registrar/programar', {
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
            mostrarMensaje('Registro exitoso: ' + (data.message || 'Programación registrada.'));
            // Resetea el formulario
            document.getElementById('tallerForm').reset();
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
    mensajeElement.innerText = mensaje; // Establece el mensaje
    mensajeElement.style.display = 'block'; // Asegúrate de que el mensaje sea visible
}