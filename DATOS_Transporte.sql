INSERT INTO ESTADO (descripcion) VALUES 
('Disponible'),
('En Mantenimiento'),
('En Reparación'),
('Fuera de Servicio'),
('Reservado');
go
INSERT INTO CARRO (id_estado, placa, prox_mant) VALUES 
(1, 'ABC123', '2024-01-15'),
(2, 'DEF456', '2024-02-20'),
(1, 'GHI789', '2024-03-10'),
(3, 'JKL012', '2024-04-05'),
(4, 'MNO345', '2024-05-30');
go
INSERT INTO EST_MANTENIMIENTO (descripcion) VALUES 
('Programado'),
('En Proceso'),
('Completado'),
('Cancelado'),
('Pendiente');
go
INSERT INTO CONDUCTOR (nombre, apellido, dni, correo, telefono, salario) VALUES 
('Juan', 'Pérez', '12345678', 'juan.perez@example.com', '987654321', 1500.00),
('María', 'Gómez', '23456789', 'maria.gomez@example.com', '987654322', 1600.00),
('Carlos', 'Sánchez', '34567890', 'carlos.sanchez@example.com', '987654323', 1550.00),
('Ana', 'Martínez', '45678901', 'ana.martinez@example.com', '987654324', 1580.00),
('Luis', 'Torres', '56789012', 'luis.torres@example.com', '987654325', 1620.00);
go
INSERT INTO EMPLEADO (nombre, apellido, dni, correo, telefono, salario) VALUES 
('Pedro', 'Ramírez', '67890123', 'pedro.ramirez@example.com', '987654326', 1400.00),
('Laura', 'Hernández', '78901234', 'laura.hernandez@example.com', '987654327', 1450.00),
('Jorge', 'Díaz', '89012345', 'jorge.diaz@example.com', '987654328', 1420.00),
('Clara', 'Vásquez', '90123456', 'clara.vasquez@example.com', '987654329', 1480.00),
('Diego', 'Cruz', '01234567', 'diego.cruz@example.com', '987654330', 1500.00);
go
INSERT INTO RUTA (nombre_ruta, origen, destino, distancia_km) VALUES 
('Ruta 1', 'Ciudad A', 'Ciudad B', 120.50),
('Ruta 2', 'Ciudad B', 'Ciudad C', 150.75),
('Ruta 3', 'Ciudad C', 'Ciudad D', 200.00),
('Ruta 4', 'Ciudad D', 'Ciudad E', 175.25),
('Ruta 5', 'Ciudad E', 'Ciudad A', 300.00);
go
INSERT INTO TALLER (nombre_taller, direccion, telefono) VALUES 
('Taller Mecánico A', 'Av. Principal 123', '987654331'),
('Taller Mecánico B', 'Calle Secundaria 456', '987654332'),
('Taller Mecánico C', 'Calle Tercera 789', '987654333'),
('Taller Mecánico D', 'Av. Cuarta 321', '987654334'),
('Taller Mecánico E', 'Calle Quinta 654', '987654335');
go
INSERT INTO MANTENIMIENTO (id_taller, id_est_mant, id_carro, fecha_inicio, fecha_salida_programada, fecha_salida_real, costo) VALUES 
(1, 1, 1, '2024-01-10', '2024-01-15', '2024-01-14', 500.00),
(2, 2, 2, '2024-02-01', '2024-02-20', '2024-02-19', 600.00),
(3, 1, 3, '2024-03-01', '2024-03-10', '2024-03-09', 450.00),
(4, 3, 4, '2024-04-01', '2024-04-05', '2024-04-04', 700.00),
(5, 2, 5, '2024-05-01', '2024-05-30', '2024-05-29', 550.00);
go
INSERT INTO TIPO_INCIDENTE (descripcion) VALUES 
('Accidente'),
('Falla Mecánica'),
('Problema Eléctrico'),
('Incidente de Seguridad'),
('Daños por Vandalismo');
go
INSERT INTO PROGRAMACION (id_carro, id_empleado, id_conductor, id_ruta, fecha_asignacion, fecha_fin_programada, fecha_fin_real) VALUES 
(1, 1, 1, 1, '2024-01-01', '2024-01-15', '2024-01-14'),
(2, 2, 2, 2, '2024-02-01', '2024-02-20', '2024-02-19'),
(3, 3, 3, 3, '2024-03-01', '2024-03-10', '2024-03-09'),
(4, 4, 4, 4, '2024-04-01', '2024-04-05', '2024-04-04'),
(5, 5, 5, 5, '2024-05-01', '2024-05-30', '2024-05-29');
go
INSERT INTO INCIDENTE (id_programacion, id_tipo, fecha_incidente) VALUES 
(1, 1, '2024-01-12'),
(2, 2, '2024-02-15'),
(3, 3, '2024-03-05'),
(4, 4, '2024-04-03'),
(5, 5, '2024-05-25');
INSERT INTO REPARACION (id_incidente, id_taller, fecha_reparacion, calificacion, costo) VALUES 
(1, 1, '2024-01-13', 4, 200.00),
(2, 2, '2024-02-16', 5, 300.00),
(3, 3, '2024-03-06', 3, 250.00),
(4, 4, '2024-04-04', 4, 400.00),
(5, 5, '2024-05-26', 2, 350.00);
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
