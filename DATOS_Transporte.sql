
INSERT INTO ESTADO (descripcion) VALUES 
('Disponible'),
('En Mantenimiento'),
('En Reparaci�n'),
('Fuera de Servicio'),
('Reservado');
go
INSERT INTO CARRO (id_estado, placa, prox_mant) VALUES 
(1, 'ABC123', '2024-11-15'),
(2, 'DEF456', '2024-12-20'),
(1, 'GHI789', '2025-01-10'),
(3, 'JKL012', '2025-02-05'),
(4, 'MNO345', '2024-12-30');
go
INSERT INTO EST_MANTENIMIENTO (descripcion) VALUES 
('Programado'),
('En Proceso'),
('Completado'),
('Cancelado'),
('Pendiente');
go
INSERT INTO CONDUCTOR (nombre, apellido, dni, correo, telefono) VALUES 
('Juan', 'P�rez', '12345678', 'juan.perez@example.com', '987654321'),
('Mar�a', 'G�mez', '23456789', 'maria.gomez@example.com', '987654322'),
('Carlos', 'S�nchez', '34567890', 'carlos.sanchez@example.com', '987654323'),
('Ana', 'Mart�nez', '45678901', 'ana.martinez@example.com', '987654324'),
('Luis', 'Torres', '56789012', 'luis.torres@example.com', '987654325');
go
INSERT INTO EMPLEADO (nombre, apellido, dni, correo, telefono) VALUES 
('Pedro', 'Ram�rez', '67890123', 'pedro.ramirez@example.com', '987654326'),
('Laura', 'Hern�ndez', '78901234', 'laura.hernandez@example.com', '987654327'),
('Jorge', 'D�az', '89012345', 'jorge.diaz@example.com', '987654328'),
('Clara', 'V�squez', '90123456', 'clara.vasquez@example.com', '987654329'),
('Fabian', 'Cruz', '01234567', 'diego.cruz@example.com', '987654330');
go
INSERT INTO RUTA (nombre_ruta, origen, destino, distancia_km) VALUES 
('Ruta 1', 'Ciudad A', 'Ciudad B', 120.50),
('Ruta 2', 'Ciudad B', 'Ciudad C', 150.75),
('Ruta 3', 'Ciudad C', 'Ciudad D', 200.00),
('Ruta 4', 'Ciudad D', 'Ciudad E', 175.25),
('Ruta 5', 'Ciudad E', 'Ciudad A', 300.00);
go
INSERT INTO TALLER (nombre_taller, direccion, telefono, calificacion) VALUES 
('Taller Mec�nico A', 'Av. Principal 123', '987654331',3),
('Taller Mec�nico B', 'Calle Secundaria 456', '987654332',4),
('Taller Mec�nico C', 'Calle Tercera 789', '987654333',3.5),
('Taller Mec�nico D', 'Av. Cuarta 321', '987654334',4.5),
('Taller Mec�nico E', 'Calle Quinta 654', '987654335',1.5);
go
INSERT INTO MANTENIMIENTO (id_taller, id_empleado , id_est_mant, id_carro, calificacion, fecha_inicio, fecha_salida_programada, fecha_salida_real, costo, detalle) VALUES 
(1, 1, 1, 1, 2,'2024-01-10', '2024-01-15', '2024-01-14', 500.00, 'Carro en buen estado, no se encontraron fallas'),
(2, 2, 2, 2, 3,'2024-02-01', '2024-02-20', '2024-02-19', 600.00, 'Se inflaron las llantas'),
(3, 3, 1, 3, 4,'2024-03-01', '2024-03-10', '2024-03-09', 450.00, 'Se realiz� cambio de aceite'),
(4, 4, 3, 4, 5,'2024-04-01', '2024-04-05', '2024-04-04', 700.00, 'Carro en buen estado'),
(5, 5, 2, 5, 1,'2024-05-01', '2024-05-30', '2024-05-29', 550.00, 'Carro en buen estado, no se encontraron fallas');
go
INSERT INTO TIPO_INCIDENTE (descripcion) VALUES 
('Accidente en carretera'),
('Falla Mec�nica'),
('Problema El�ctrico'),
('Incidente de Seguridad'),
('Da�os por Vandalismo');
go
INSERT INTO PROGRAMACION (id_carro, id_empleado, id_conductor, id_ruta, fecha_asignacion, fecha_fin_programada, fecha_fin_real) VALUES 
(1, 1, 1, 1, '2024-01-01', '2024-01-15', '2024-01-14'),
(2, 2, 2, 2, '2024-02-01', '2024-02-20', '2024-02-20'),
(3, 3, 3, 3, '2024-03-01', '2024-03-10', '2024-03-09'),
(4, 4, 4, 4, '2024-04-01', '2024-04-05', '2024-04-04'),
(5, 5, 5, 5, '2024-05-01', '2024-05-30', '2024-05-30');
go
INSERT INTO INCIDENTE (id_empleado, id_programacion, id_tipo, fecha_incidente, detalle) VALUES 
(1, 1, 1, '2024-01-12', 'Ocurri� 30 minutos de partir. No hubo heridos'),
(2, 2, 2, '2024-02-15', 'Fallo en el motor. No hubo heridos'),
(3, 3, 3, '2024-03-05', 'Problema con la bater�a. No hubo heridos'),
(4, 4, 4, '2024-04-03', 'La puerta no cerraba correctamente'),
(5, 5, 5, '2024-05-25', 'Cinco adolescentes intervenidos. No hubo heridos');
INSERT INTO REPARACION (id_incidente, id_empleado, id_taller, fecha_reparacion, calificacion, costo, detalle) VALUES 
(1, 1, 1, '2024-01-13', 4, 200.00, 'Capot reparado'),
(2, 2, 2, '2024-02-16', 5, 300.00, 'Motor reparado'),
(3, 3, 3, '2024-03-06', 3, 250.00, 'Cambio de bater�a'),
(4, 4, 4, '2024-04-04', 4, 400.00, 'Puerta reparada'),
(5, 5, 5, '2024-05-26', 2, 350.00, 'Ventanas cambiadas. Se aplic� una capa de pintura al carro');
go
select * From CARRO;
select * From CONDUCTOR;
select * From EMPLEADO;
select * From REPARACION;
select * From MANTENIMIENTO;
select * From EST_MANTENIMIENTO;
select * From RUTA;
select * From PROGRAMACION;
select * from INCIDENTE;
select * From TIPO_INCIDENTE;
select * From ESTADO;
select * From TALLER;
go