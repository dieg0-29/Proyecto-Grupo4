
INSERT INTO EST_CARRO (descripcion) VALUES 
('Disponible'),
('En Mantenimiento'),
('En Reparación'),
('Fuera de Servicio'),
('En Ruta');
go
INSERT INTO CARRO (id_estado, placa, prox_mant) VALUES 
(5, 'ABC123', '2024-11-15'),
(5, 'DEF456', '2024-12-20'),
(1, 'GHI789', '2025-01-10'),
(1, 'JKL012', '2025-02-05'),
(5, 'MNO345', '2024-12-30'),
(1, 'PQR678', '2024-12-15'),
(1, 'STU901', '2025-01-22'),
(5, 'VXY234', '2025-01-14'),
(1, 'ZAB567', '2025-02-13'),
(1, 'CDE890', '2024-12-29'),
(1, 'FGH123', '2024-12-23');
go
INSERT INTO EST_MANTENIMIENTO (descripcion) VALUES 
('Programado'),
('En Proceso'),
('Completado'),
('Cancelado'),
('Pendiente');
go
INSERT INTO EST_CONDUCTOR (descripcion) VALUES 
('Disponible'),
('En Ruta'),
('Fuera de servicio');
go
INSERT INTO CONDUCTOR (id_estado, nombre, apellido, dni, correo, telefono) VALUES 
(2, 'Juan', 'Pérez', '12345678', 'jperez@gmail.com', '987654321'),
(2, 'María', 'Gómez', '23456789', 'mgomez@gmail.com', '987654322'),
(1, 'Carlos', 'Sánchez', '34567890', 'csanchez@gmail.comm', '987654323'),
(2, 'Ana', 'Martínez', '45678901', 'amartinez@gmail.com', '987654324'),
(1, 'Luis', 'Torres', '56789012', 'ltorres@gmail.com', '987654325'),
(1, 'Martin', 'Feliciano', '27831920', 'mfeliciano@gmail.com', '983439011'),
(2, 'Luz', 'Rodriguez', '45629102', 'lrodriguez@gmail.com', '982390121'),
(1, 'Karina', 'Hernandez', '28730021', 'khernandez@gmail.com', '94392300'),
(1, 'Leandro', 'Torres', '23789012', 'ltorres@gmail.com', '900234598'),
(1, 'Mauricio', 'Guerrero', '43189054', 'mguerrero@gmail.com', '943223441');
go
INSERT INTO EMPLEADO (nombre, apellido, dni, correo, telefono, usuario, clave) VALUES 
('Pedro', 'Ramírez', '67890123', 'pedro.ramirez@example.com', '987654326', 'pramirez', 'contraseña'),
('Laura', 'Hernández', '78901234', 'laura.hernandez@example.com', '987654327', 'lhernandez', 'contraseña'),
('Jorge', 'Díaz', '89012345', 'jorge.diaz@example.com', '987654328', 'jdiaz', 'contraseña'),
('Clara', 'Vásquez', '90123456', 'clara.vasquez@example.com', '987654329', 'cvasquez', 'contraseña'),
('Fabian', 'Cruz', '01234567', 'diego.cruz@example.com', '987654330', 'fcruz', 'contraseña');
go
INSERT INTO RUTA (nombre_ruta, origen, destino, distancia_km) VALUES 
('Ruta 1', 'Ciudad A', 'Ciudad B', 120.50),
('Ruta 2', 'Ciudad B', 'Ciudad C', 150.75),
('Ruta 3', 'Ciudad C', 'Ciudad D', 200.00),
('Ruta 4', 'Ciudad D', 'Ciudad E', 175.25),
('Ruta 5', 'Ciudad E', 'Ciudad A', 450.00),
('Ruta 6', 'Ciudad B', 'Ciudad D', 250.00),
('Ruta 7', 'Ciudad D', 'Ciudad A', 300.00),
('Ruta 8', 'Ciudad E', 'Ciudad B', 400.00);
go
INSERT INTO TALLER (nombre_taller, direccion, telefono, calificacion) VALUES 
('Taller Mecánico A', 'Av. Principal 123', '987654331',3),
('Taller Mecánico B', 'Calle Secundaria 456', '987654332',4),
('Taller Mecánico C', 'Calle Tercera 789', '987654333',3.5),
('Taller Mecánico D', 'Av. Cuarta 321', '987654334',4.5),
('Taller Mecánico E', 'Calle Quinta 654', '987654335',1.5);
go
INSERT INTO MANTENIMIENTO (id_taller, id_empleado , id_est_mant, id_carro, calificacion, fecha_inicio, fecha_salida_programada, fecha_salida_real, costo, detalle) VALUES 
(1, 1, 1, 1, 2,'2024-01-10', '2024-01-15', '2024-01-14', 500.00, 'Carro en buen estado, no se encontraron fallas'),
(2, 2, 2, 2, 3,'2024-02-01', '2024-02-20', '2024-02-19', 600.00, 'Se inflaron las llantas'),
(3, 3, 1, 3, 4,'2024-03-01', '2024-03-10', '2024-03-09', 450.00, 'Se realizó cambio de aceite'),
(4, 4, 3, 4, 5,'2024-04-01', '2024-04-05', '2024-04-04', 700.00, 'Carro en buen estado'),
(5, 5, 2, 5, 1,'2024-05-01', '2024-05-30', '2024-05-29', 550.00, 'Carro en buen estado, no se encontraron fallas');
go
INSERT INTO TIPO_INCIDENTE (descripcion) VALUES 
('Accidente en carretera'),
('Falla Mecánica'),
('Problema Eléctrico'),
('Incidente de Seguridad'),
('Daños por Vandalismo'),
('Tráfico'),
('Intervención policial'),
('Papeleta y/o multa');
go
INSERT INTO PROGRAMACION (id_carro, id_empleado, id_conductor, id_ruta, fecha_asignacion, fecha_fin_programada, fecha_fin_real) VALUES 
(1, 2, 3, 7, '2024-10-23', '2024-10-27', '2024-10-28'),
(2, 1, 2, 4, '2024-11-01', '2024-11-15', '2024-11-14'),
(7, 4, 8, 6, '2024-11-03', '2024-11-10', '2024-11-10'),
(6, 2, 1, 3, '2024-11-09', '2024-11-12', '2024-11-14'),
(4, 1, 7, 1, '2024-11-10', '2024-11-15', '2024-11-17'),
(1, 3, 7, 4, '2024-12-01', '2024-12-06', NULL),
(2, 2, 2, 7, '2024-11-29', '2024-12-07', NULL),
(8, 1, 4, 1, '2024-11-30', '2024-12-10', NULL),
(5, 1, 1, 3, '2024-11-02', '2024-12-05', NULL);
go
INSERT INTO INCIDENTE (id_empleado, id_programacion, id_tipo, fecha_incidente, detalle) VALUES 
(2, 1, 1, '2024-10-24', 'Ocurrió 30 minutos después de partir. No hubo heridos'),
(3, 2, 2, '2024-11-11', 'Fallo en el motor. No hubo heridos'),
(1, 4, 4, '2024-11-12', 'La puerta no cerraba correctamente'),
(1, 5, 5, '2024-11-13', 'Cinco adolescentes intervenidos. No hubo heridos');
INSERT INTO REPARACION (id_incidente, id_empleado, id_taller, fecha_reparacion, calificacion, costo, detalle) VALUES 
(1, 2, 1, '2024-11-23', 4, 200.00, 'Capot reparado'),
(2, 3, 2, '2024-11-11', 5, 300.00, 'Motor reparado'),
(3, 1, 4, '2024-11-12', 4, 400.00, 'Puerta reparada'),
(4, 1, 5, '2024-11-13', 2, 350.00, 'Ventanas cambiadas. Se aplicó una capa de pintura al carro');
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
select * From EST_CARRO;
select * From TALLER;
go


