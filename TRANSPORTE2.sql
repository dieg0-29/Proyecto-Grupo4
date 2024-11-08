use master;
CREATE DATABASE TRANSPORTE;
USE TRANSPORTE;

--Tabla de Estado
CREATE TABLE ESTADO (
	id_estado INT IDENTITY PRIMARY KEY,
	descripcion TEXT
);

-- Tabla de Carro
CREATE TABLE CARRO(
    id_carro INT IDENTITY PRIMARY KEY,
	id_estado INT FOREIGN KEY REFERENCES estado(id_estado),
    placa VARCHAR(15) NOT NULL UNIQUE,
	prox_mant DATE NOT NULL
);

--Tabla de estado_mantenimiento
CREATE TABLE EST_MANTENIMIENTO (
    id_est INT IDENTITY PRIMARY KEY,
	descripcion TEXT
);

-- Tabla de Mantenimientos
CREATE TABLE MANTENIMIENTO (
    id_mantenimiento INT IDENTITY PRIMARY KEY,
	id_est_mant INT FOREIGN KEY REFERENCES est_mantenimiento(id_est),
    id_carro INT FOREIGN KEY REFERENCES carro(id_carro),
    fecha_inicio DATE,
	fecha_salida_programada DATE,
	fecha_salida_real DATE,
    costo DECIMAL(10,2)
);
-- Tabla de Conductore
CREATE TABLE CONDUCTOR (
    id_conductor INT IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20),
    correo VARCHAR(150),
    telefono VARCHAR(20),
	salario DECIMAL(10,2)
);

-- Tabla de Rutas
CREATE TABLE RUTA (
    id_ruta INT IDENTITY PRIMARY KEY,
    nombre_ruta VARCHAR(100),
    origen VARCHAR(100),
    destino VARCHAR(100),
    distancia_km DECIMAL(5,2)
);

-- Tabla de Taller
CREATE TABLE TALLER (
    id_taller INT IDENTITY PRIMARY KEY,
    nombre_taller VARCHAR(100),
    direccion VARCHAR(150),
    telefono VARCHAR(20),
);

-- Tabla tipo-incidente
CREATE TABLE TIPO_INCIDENTE(
	id_tipo INT IDENTITY PRIMARY KEY,
	descripcion TEXT
);
-- Tabla de Historial de Asignación
CREATE TABLE PROGRAMACION(
    id_programacion INT IDENTITY PRIMARY KEY,
    id_vehiculo INT FOREIGN KEY REFERENCES carro(id_carro),
    id_conductor INT FOREIGN KEY REFERENCES conductor(id_conductor),
    id_ruta INT FOREIGN KEY REFERENCES ruta(id_ruta),
    fecha_asignacion DATE,
    fecha_fin_programada DATE,
	fecha_fin_real DATE
);
-- Tabla de Incidentes
CREATE TABLE INCIDENTE (
    id_incidente INT IDENTITY PRIMARY KEY,
    id_programacion INT FOREIGN KEY REFERENCES programacion(id_programacion),
    id_tipo INT FOREIGN KEY REFERENCES  tipo_incidente(id_tipo),
	fecha_incidente DATE,
);

-- Tabla de Reparaciones
CREATE TABLE REPARACION (
    id_reparacion INT IDENTITY PRIMARY KEY,
    id_carro INT FOREIGN KEY REFERENCES carro(id_carro),
    id_taller INT FOREIGN KEY REFERENCES taller(id_taller),
    fecha_reparacion DATE,
    descripcion TEXT,
	calificacion INT,
    costo DECIMAL(10,2)
);
