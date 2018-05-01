INSERT INTO `particulares`(`dni`, `nombre`, `apellidos`, `telefono`, `activo`, `email`, `password`) VALUES
('12345678L', 'Luis', 'Perez', 123456789, 1, 'luis@gmail.es', '$2a$10$qXgak7rkAqGqkJjzxT6/Ru0hHGgoGyN81Cobog4yZmO4v.y6cNrbO');
INSERT INTO `particulares`(`dni`, `nombre`, `apellidos`, `telefono`, `activo`, `email`, `password`)
VALUES ('04229123G', 'Chema', 'Perez', 123456789, 1,'chema@gmail.es', '$2a$10$qXgak7rkAqGqkJjzxT6/Ru0hHGgoGyN81Cobog4yZmO4v.y6cNrbO');
INSERT INTO `empresas` (`cif`, `nombre_empresa`, `telefono`, `activo`, `puntuacion`, `email`, `password`) VALUES
('A97329148', 'Endesa', 123456789, 1, 0, 'endesa@gmail.es', '$2a$10$qXgak7rkAqGqkJjzxT6/Ru0hHGgoGyN81Cobog4yZmO4v.y6cNrbO');

INSERT INTO `ofertas` (`id`, `activo`, `ciudad`, `contrato`, `descripcion`, `finalizada`, `jornada`, `nombre`, `salario`, `vacantes`, `empresa_id`, `anios_experiencia`) VALUES
(1, 1, 'Talavera de la Reina', 0, '', 0, 0, 'Java ', 1200, 24, 1, 1);
INSERT INTO `ofertas` (`id`, `activo`, `ciudad`, `contrato`, `descripcion`, `finalizada`, `jornada`, `nombre`, `salario`, `vacantes`, `empresa_id`, `anios_experiencia`) VALUES
(2, 1, 'Madrid', 0, '', 0, 0, 'HTML y CSS ', 1500, 50, 1, 3);

INSERT INTO `particulares_ofertas_inscritos` (`particulares_inscritos_id`, `ofertas_inscritos_id`) VALUES
('1', '1'), ('2', '1');