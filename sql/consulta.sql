SELECT DISTINCT c.nombre, c.apellidos
FROM Cliente c
         JOIN Inscripción i ON c.id = i.idCliente
         JOIN Disponibilidad d ON i.idProducto = d.idProducto
         JOIN Visitan v ON c.id = v.idCliente AND d.idSucursal = v.idSucursal
         JOIN Sucursal s ON v.idSucursal = s.id
         JOIN Producto p ON i.idProducto = p.id
WHERE d.idProducto = i.idProducto;
