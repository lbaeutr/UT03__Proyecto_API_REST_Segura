-- Insertar datos en la tabla Usuario
INSERT INTO usuario (`id`, `username`, `password`, `roles`) VALUES (1, 'Pedro SANTE', '123456', 'ENTRENADOR');
INSERT INTO usuario (`id`, `username`, `password`, `roles`) VALUES (2, 'Adeli Wick', '123456', 'CLIENTE');
INSERT INTO usuario (`id`, `username`, `password`, `roles`) VALUES (3, 'Luis Baena', '123456', 'CLIENTE');
INSERT INTO usuario (`id`, `username`, `password`, `roles`) VALUES (4, 'Gilmar SANTE', '123456', 'ENTRENADOR');
INSERT INTO usuario (`id`, `username`, `password`, `roles`) VALUES (5, 'Diego Linares', '123456', 'CLIENTE');

-- Insertar datos en la tabla PlanEntrenamiento
INSERT INTO plan_entrenamiento (`id`, `nombre`, `descripcion`, `duracion`, `entrenador_id`) VALUES
    (1, 'Plan de Fuerza', 'Un plan de entrenamiento de fuerza para principiantes', 30, 1);

-- Insertar datos en la tabla Ejercicio
INSERT INTO ejercicio (`id`, `nombre`, `descripcion`, `categoria`, `plan_id`) VALUES
    (1, 'Sentadillas', 'Ejercicio para fortalecer piernas', 'Fuerza', 1);
INSERT INTO ejercicio (`id`, `nombre`, `descripcion`, `categoria`, `plan_id`) VALUES
    (2, 'Flexiones', 'Ejercicio para fortalecer pecho y brazos', 'Fuerza', 1);
