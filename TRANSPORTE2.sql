-- =============================================
-- Creación de la Base de Datos
-- =============================================

USE master;
go

IF( EXISTS ( SELECT name FROM master.sys.databases WHERE name = 'TRANSPORTE' ) )
BEGIN
	DROP DATABASE TRANSPORTE;
END;
go

CREATE DATABASE TRANSPORTE;
go


-- =============================================
-- Seleccionar la Base de Datos
-- =============================================

USE TRANSPORTE;
go

--Tabla de Estado
CREATE TABLE ESTADO(
	id_estado INT IDENTITY PRIMARY KEY,
	descripcion Varchar(50) NOT NULL
);
go
-- Tabla de Carro
CREATE TABLE CARRO(
    id_carro INT IDENTITY PRIMARY KEY,
	id_estado INT FOREIGN KEY REFERENCES estado(id_estado),
    placa VARCHAR(6) NOT NULL UNIQUE,
	prox_mant DATE NOT NULL
);
go
--Tabla de estado_mantenimiento
CREATE TABLE EST_MANTENIMIENTO (
    id_est_mant INT IDENTITY PRIMARY KEY,
	descripcion Varchar(50) NOT NULL
);
go

-- Tabla de Conductor
CREATE TABLE CONDUCTOR (
    id_conductor INT IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(8) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
	salario DECIMAL(10,2) NOT NULL
);
go
-- Tabla de Empleado
CREATE TABLE EMPLEADO (
    id_empleado INT IDENTITY PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
    dni VARCHAR(8) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
	salario DECIMAL(10,2) NOT NULL
);
go
-- Tabla de Rutas
CREATE TABLE RUTA (
    id_ruta INT IDENTITY PRIMARY KEY,
    nombre_ruta VARCHAR(100) NOT NULL,
    origen VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    distancia_km DECIMAL(5,2) NOT NULL
);
go
-- Tabla de Taller
CREATE TABLE TALLER (
    id_taller INT IDENTITY PRIMARY KEY,
    nombre_taller VARCHAR(50) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);
go
-- Tabla de Mantenimientos
CREATE TABLE MANTENIMIENTO (
    id_mantenimiento INT IDENTITY PRIMARY KEY,
	id_empleado INT FOREIGN KEY REFERENCES empleado(id_empleado),
	id_taller INT FOREIGN KEY REFERENCES taller(id_taller),
	id_est_mant INT FOREIGN KEY REFERENCES est_mantenimiento(id_est_mant),
    id_carro INT FOREIGN KEY REFERENCES carro(id_carro),
    fecha_inicio DATE NOT NULL,
	fecha_salida_programada DATE NOT NULL,
	fecha_salida_real DATE NOT NULL,
    costo DECIMAL(10,2) NOT NULL
);
go

-- Tabla tipo-incidente
CREATE TABLE TIPO_INCIDENTE(
	id_tipo INT IDENTITY PRIMARY KEY,
	descripcion VARCHAR(50) NOT NULL
);
go
-- Tabla de Historial de Asignación
CREATE TABLE PROGRAMACION(
    id_programacion INT IDENTITY PRIMARY KEY,
    id_carro INT FOREIGN KEY REFERENCES carro(id_carro),
	id_empleado INT FOREIGN KEY REFERENCES empleado(id_empleado),
    id_conductor INT FOREIGN KEY REFERENCES conductor(id_conductor),
    id_ruta INT FOREIGN KEY REFERENCES ruta(id_ruta),
    fecha_asignacion DATE NOT NULL,
    fecha_fin_programada DATE NOT NULL,
	fecha_fin_real DATE NOT NULL
);
go
-- Tabla de Incidentes
CREATE TABLE INCIDENTE (
    id_incidente INT IDENTITY PRIMARY KEY,
    id_programacion INT FOREIGN KEY REFERENCES programacion(id_programacion),
    id_tipo INT FOREIGN KEY REFERENCES  tipo_incidente(id_tipo) NOT NULL,
	fecha_incidente DATE NOT NULL
);
go
-- Tabla de Reparaciones
CREATE TABLE REPARACION (
    id_reparacion INT IDENTITY PRIMARY KEY,
	id_empleado INT FOREIGN KEY REFERENCES empleado(id_empleado),
    id_incidente INT FOREIGN KEY REFERENCES incidente(id_incidente),
    id_taller INT FOREIGN KEY REFERENCES taller(id_taller),
    fecha_reparacion DATE NOT NULL,
	calificacion INT NOT NULL,
    costo DECIMAL(10,2) NOT NULL
);
go
