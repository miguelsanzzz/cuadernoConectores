
CREATE TABLE PROFESOR (
    dni VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50),
    apellidos VARCHAR(80),
    direccion VARCHAR(120),
    fecha_nacimiento DATE,
    codigo_postal VARCHAR(10),
    telefono VARCHAR(15)
);

CREATE TABLE ALUMNO (
    dni VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50),
    apellidos VARCHAR(80),
    direccion VARCHAR(120),
    fecha_nacimiento DATE,
    codigo_postal VARCHAR(10),
    telefono VARCHAR(15)
);

CREATE TABLE CURSO (
    id_curso INT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL
);

CREATE TABLE ASIGNATURA (
    id_asignatura INT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    horas_semana INT,
    dni_profesor VARCHAR(9),
    id_curso INT,
    FOREIGN KEY (dni_profesor) REFERENCES PROFESOR(dni),
    FOREIGN KEY (id_curso) REFERENCES CURSO(id_curso)
);

CREATE TABLE AULA (
    id_aula INT PRIMARY KEY,
    piso INT,
    numero_pupitres INT
);

CREATE TABLE MATRICULA (
    dni_alumno VARCHAR(9),
    id_asignatura INT,
    calificacion DECIMAL(4,2),
    incidencias VARCHAR(500),
    PRIMARY KEY (dni_alumno, id_asignatura),
    FOREIGN KEY (dni_alumno) REFERENCES ALUMNO(dni),
    FOREIGN KEY (id_asignatura) REFERENCES ASIGNATURA(id_asignatura)
);

CREATE TABLE HORARIO (
    id_horario INT PRIMARY KEY,
    id_asignatura INT,
    id_aula INT,
    mes INT,
    dia INT,
    hora TIME,
    FOREIGN KEY (id_asignatura) REFERENCES ASIGNATURA(id_asignatura),
    FOREIGN KEY (id_aula) REFERENCES AULA(id_aula)
);
