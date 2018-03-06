INSERT INTO `roles`(`nombre`) VALUES ('Usuario');
INSERT INTO `roles`(`nombre`) VALUES ('Empresa');
INSERT INTO `roles`(`nombre`) VALUES ('Administrador');

INSERT INTO `usuarios`(`nombre`, `apellidos`, `email`, `activo`, `password`) VALUES ('Administrador', '', 'admin@admin.es', 1, '$2a$10$qXgak7rkAqGqkJjzxT6/Ru0hHGgoGyN81Cobog4yZmO4v.y6cNrbO');
INSERT INTO `usuario_rol`(`id_rol`, `id_usuario`) VALUES (3, 1);