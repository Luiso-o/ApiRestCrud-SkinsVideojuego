## Descripción del proyecto

Crear una API que permita a las personas usuarias consultar, adquirir, modificar y eliminar skins por
un videojuego.

## Requisitos:

* Definir el Modelo de Skin: Crea una estructura de datos para representar las skins. Debe
incluir campos como id, nombre, tipos, precio, color, etc.

* Implementar una función para leer las skins disponibles desde un archivo (por ejemplo, en
formato JSON).

* Configurar la Base de Datos: Establece una conexión a una base de datos donde se guarden
las skins que adquieren los usuarios. La base de datos puede ser MySQL o MongoDB.

* Crear las siguientes rutas de la API:

1- GET /skins/avaible - Devuelve una lista de todas las skins disponibles para comprar.
2- POST /skins/buy - Permite a los usuarios adquirir una skin y guardarla en la base de
datos.
3- GET /skins/myskins - Devuelve una lista de las skins compradas por el usuario.
4- PUT /skins/color - Permite a los usuarios cambiar el color de una skin comprada.
5- DELETE /skins/delete/{id} - Permite a los usuarios eliminar una skin comprada.
6- GET /skin/getskin/{id} – Devuelve una determinada skin.

## Requisitos Técnicos y Evaluación
* La aplicación cumple con los requisitos funcionales especificados.
* El código sigue buenas prácticas de programación.
* El proyecto sigue un patrón estructural.
* Las funciones tienen una única responsabilidad.
* El código es escalable y reutilizable.
* El código está optimizado y su rendimiento es eficiente.
* Proporcionar una documentación detallada en el Readme del repositorio.