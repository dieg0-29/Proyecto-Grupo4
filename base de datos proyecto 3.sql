use master;
CREATE DATABASE gestion_flotas;
USE gestion_flotas;

--Tabla de Estado
CREATE TABLE estado (
	id_estado INT IDENTITY PRIMARY KEY,
	descripcion TEXT
);
-- Tabla de Vehículos
CREATE TABLE vehiculos (
    id_vehiculo INT IDENTITY PRIMARY KEY,
	id_estado INT FOREIGN KEY REFERENCES estado(id_estado),
    placa VARCHAR(15) NOT NULL UNIQUE,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    año INT,
    capacidad INT,
    estado VARCHAR(20)
);

-- Tabla de Conductores
CREATE TABLE conductores (
    id_conductor INT IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
    licencia VARCHAR(20) UNIQUE NOT NULL,
    dni VARCHAR(20),
    correo VARCHAR(150),
    telefono VARCHAR(20)
);

-- Tabla de Rutas
CREATE TABLE rutas (
    id_ruta INT IDENTITY PRIMARY KEY,
    nombre_ruta VARCHAR(100),
    origen VARCHAR(100),
    destino VARCHAR(100),
    distancia_km DECIMAL(5,2)
);

-- Tabla de Talleres
CREATE TABLE talleres (
    id_taller INT IDENTITY PRIMARY KEY,
    nombre_taller VARCHAR(100),
    direccion VARCHAR(150),
    telefono VARCHAR(20),
    rating DECIMAL(3,2)
);

-- Tabla tipo-incidente
CREATE TABLE tipo_incidente(
	id_tipo INT IDENTITY PRIMARY KEY,
	descripcion TEXT
);
-- Tabla de Historial de Asignación
CREATE TABLE historial_asignacion (
    id_asignacion INT IDENTITY PRIMARY KEY,
    id_vehiculo INT FOREIGN KEY REFERENCES vehiculos(id_vehiculo),
    id_conductor INT FOREIGN KEY REFERENCES conductores(id_conductor),
    id_ruta INT FOREIGN KEY REFERENCES rutas(id_ruta),
    fecha_asignacion DATE,
    fecha_fin DATE
);
-- Tabla de Incidentes
CREATE TABLE incidentes (
    id_incidente INT IDENTITY PRIMARY KEY,
    id_asignacion INT FOREIGN KEY REFERENCES historial_asignacion(id_asignacion),
    id_tipo INT FOREIGN KEY REFERENCES  tipo_incidente(id_tipo),
	fecha_incidente DATE,
    tipo_incidente VARCHAR(50),
    descripcion TEXT
);

--Tabla de estado_mantenimiento
CREATE TABLE est_mantenimiento (
    id_est INT IDENTITY PRIMARY KEY,
	descripcion TEXT
);

-- Tabla de Mantenimientos
CREATE TABLE mantenimientos (
    id_mantenimiento INT IDENTITY PRIMARY KEY,
	id_est INT FOREIGN KEY REFERENCES est_mantenimiento(id_est),
    id_vehiculo INT FOREIGN KEY REFERENCES vehiculos(id_vehiculo),
    fecha_mantenimiento DATE,
    tipo_mantenimiento VARCHAR(50),
    descripcion TEXT,
    costo DECIMAL(10,2)
);

-- Tabla de Reparaciones
CREATE TABLE reparaciones (
    id_reparacion INT IDENTITY PRIMARY KEY,
    id_vehiculo INT FOREIGN KEY REFERENCES vehiculos(id_vehiculo),
    id_taller INT FOREIGN KEY REFERENCES talleres(id_taller),
    fecha_reparacion DATE,
    descripcion TEXT,
	calificacion INT,
    costo DECIMAL(10,2)
);



-- Insertar datos en Vehículos
INSERT INTO vehiculos (placa, marca, modelo, año, capacidad, estado) VALUES
('ABC123', 'Ford', 'Transit', 2020, 15, 'Activo'),
('DEF456', 'Toyota', 'Hiace', 2018, 12, 'Activo'),
('GHI789', 'Mercedes', 'Sprinter', 2019, 18, 'En Reparación');

-- Insertar datos en Conductores
INSERT INTO conductores (nombre, licencia, fecha_nacimiento, direccion, telefono) VALUES
('Juan Perez', 'L123456', '1985-06-15', 'Calle Falsa 123', '555-1234'),
('Maria Lopez', 'L654321', '1990-08-20', 'Av. Siempre Viva 456', '555-5678'),
('Carlos Diaz', 'L987654', '1978-02-11', 'Av. Central 789', '555-8765');

-- Insertar datos en Rutas
INSERT INTO rutas (nombre_ruta, origen, destino, distancia_km) VALUES
('Ruta 1', 'Ciudad A', 'Ciudad B', 120.50),
('Ruta 2', 'Ciudad B', 'Ciudad C', 98.75),
('Ruta 3', 'Ciudad C', 'Ciudad D', 200.20);

-- Insertar datos en Talleres
INSERT INTO talleres (nombre_taller, direccion, telefono, rating) VALUES
('Taller Mecanico A', 'Zona Industrial 123', '555-1111', 4.5),
('Taller de Reparaciones B', 'Avenida Talleres 456', '555-2222', 4.2);

-- Insertar datos en Incidentes
INSERT INTO incidentes (id_vehiculo, id_conductor, id_ruta, fecha_incidente, tipo_incidente, descripcion) VALUES
(1, 1, 1, '2023-01-15', 'Colisión', 'Colisión menor en ruta 1'),
(2, 2, 2, '2023-02-10', 'Avería', 'Avería de motor en ruta 2'),
(3, 1, 1, '2023-03-05', 'Colisión', 'Colisión con otro vehículo en ruta 1');

-- Insertar datos en Mantenimientos
INSERT INTO mantenimientos (id_vehiculo, fecha_mantenimiento, tipo_mantenimiento, descripcion, costo) VALUES
(1, '2023-01-10', 'Preventivo', 'Cambio de aceite y revisión general', 150.00),
(2, '2023-02-05', 'Correctivo', 'Reemplazo de neumáticos', 200.00);

-- Insertar datos en Reparaciones
INSERT INTO reparaciones (id_vehiculo, id_taller, fecha_reparacion, descripcion, costo) VALUES
(1, 1, '2023-01-12', 'Reparación de motor', 300.00),
(3, 2, '2023-03-06', 'Reparación de transmisión', 450.00);

-- Insertar datos en Historial de Asignación
INSERT INTO historial_asignacion (id_vehiculo, id_conductor, id_ruta, fecha_asignacion, fecha_fin) VALUES
(1, 1, 1, '2023-01-01', '2023-01-31'),
(2, 2, 2, '2023-02-01', '2023-02-28'),
(3, 3, 3, '2023-03-01', NULL);

-- Incidente de Tránsito Más Frecuente
SELECT TOP 1 tipo_incidente, COUNT(*) AS frecuencia
FROM incidentes
GROUP BY tipo_incidente
ORDER BY frecuencia DESC;

-- Conductor con Más Incidentes en un Rango de Fecha
DECLARE @fecha_inicio DATE = '2023-01-01';
DECLARE @fecha_fin DATE = '2023-12-31';

SELECT TOP 1 c.nombre, COUNT(i.id_incidente) AS total_incidentes
FROM incidentes i
JOIN conductores c ON i.id_conductor = c.id_conductor
WHERE i.fecha_incidente BETWEEN @fecha_inicio AND @fecha_fin
GROUP BY c.nombre
ORDER BY total_incidentes DESC;

-- Ruta con Mayor Cantidad de Incidentes
SELECT TOP 1 r.nombre_ruta, COUNT(i.id_incidente) AS total_incidentes
FROM incidentes i
JOIN rutas r ON i.id_ruta = r.id_ruta
GROUP BY r.nombre_ruta
ORDER BY total_incidentes DESC;

-- Taller que Más Vehículos ha Atendido
SELECT TOP 1 t.nombre_taller, COUNT(DISTINCT r.id_vehiculo) AS total_vehiculos_atendidos
FROM reparaciones r
JOIN talleres t ON r.id_taller = t.id_taller
GROUP BY t.nombre_taller
ORDER BY total_vehiculos_atendidos DESC;

-- Historial de Mantenimientos por Vehículo
SELECT v.placa, m.fecha_mantenimiento, m.tipo_mantenimiento, m.descripcion, m.costo
FROM mantenimientos m
JOIN vehiculos v ON m.id_vehiculo = v.id_vehiculo
WHERE v.placa = 'ABC123'
ORDER BY m.fecha_mantenimiento DESC;

-- Incidentes del conductor
SELECT c.nombre, i.fecha_incidente, i.tipo_incidente, i.descripcion
FROM incidentes i
JOIN conductores c ON i.id_conductor = c.id_conductor
WHERE c.nombre = 'Juan Perez'
ORDER BY i.fecha_incidente DESC;

-- Vehículos en Mantenimiento o Reparación Actualmente
SELECT placa, marca, modelo, estado
FROM vehiculos
WHERE estado IN ('En Reparación', 'Mantenimiento');

-- Costos Totales de Mantenimiento y Reparación por Vehículo
SELECT v.placa,
       COALESCE(SUM(m.costo), 0) AS total_mantenimiento,
       COALESCE(SUM(r.costo), 0) AS total_reparacion,
       COALESCE(SUM(m.costo), 0) + COALESCE(SUM(r.costo), 0) AS total_general
FROM vehiculos v
LEFT JOIN mantenimientos m ON v.id_vehiculo = m.id_vehiculo
LEFT JOIN reparaciones r ON v.id_vehiculo = r.id_vehiculo
GROUP BY v.placa
ORDER BY total_general DESC;

-- Vehículos y Conductores Actuales por Ruta
SELECT r.nombre_ruta, v.placa, c.nombre AS conductor, ha.fecha_asignacion, ha.fecha_fin
FROM historial_asignacion ha
JOIN vehiculos v ON ha.id_vehiculo = v.id_vehiculo
JOIN conductores c ON ha.id_conductor = c.id_conductor
JOIN rutas r ON ha.id_ruta = r.id_ruta
WHERE r.nombre_ruta = 'Ruta 1' AND (ha.fecha_fin IS NULL OR ha.fecha_fin > GETDATE())
ORDER BY ha.fecha_asignacion;

-- Incidentes por año
SELECT YEAR(fecha_incidente) AS año, COUNT(*) AS total_incidentes
FROM incidentes
GROUP BY YEAR(fecha_incidente)
ORDER BY año;

select * from conductores;
select * from historial_asignacion;
select * from incidentes;
select * from mantenimientos;
select * from reparaciones;
select * from rutas;
select * from talleres;
select * from vehiculos;
go