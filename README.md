# Prueba-Lexart
# **Documentación de Prueba Técnica para Lexart**
## Introducción
Esta documentación detalla el desarrollo de un servicio API RESTful para el puesto de Full Stack Java Developer en Lexart. El objetivo principal del proyecto es diseñar y construir un servicio que gestione la administración de productos, permitiendo operaciones como el registro y la obtención de los mismos, así como la autenticación y consulta de datos de los usuarios.

## Descripción del Proyecto
### Arquitectura y Estructura del Proyecto
El proyecto está organizado utilizando una estructura de paquetes por componentes, lo que facilita la modularidad y el mantenimiento del código. Esta organización permite separar claramente la lógica de negocio, la persistencia de datos, y la interfaz de usuario, mejorando la escalabilidad y la gestión del código.

### Tecnologías y Herramientas Utilizadas
* **[Spring Boot:](https://start.spring.io/)** Utilizado para simplificar la configuración inicial del proyecto y la gestión de dependencias.
* **Spring Data JPA**: Facilita la integración con la base de datos PostgreSQL y simplifica las operaciones CRUD mediante el uso de interfaces de repositorio.
* **Spring Security**: Proporciona las capacidades de autenticación y autorización, asegurando que solo los usuarios autenticados puedan acceder a ciertas operaciones.
* **Spring Web**: Permite la creación de endpoints REST y maneja las solicitudes HTTP.
* **[PostgreSQL Database:](https://www.postgresql.org/)** Base de datos PostgreSQL utilizada para el desarrollo y pruebas, proporcionando persistencia confiable y escalable con una configuración sencilla.
* **Spring Validation**: Utilizado para asegurar que los datos ingresados por los usuarios cumplan con los criterios especificados antes de ser procesados o almacenados, lo cual es crucial para mantener la integridad de los datos.
* **Spring Web Test**: Proporciona herramientas para realizar pruebas de integración en los componentes web, permitiendo simular y verificar el comportamiento de los endpoints de la API bajo diversas condiciones.
* **[Swagger:](https://springdoc.org/#google_vignette)** Genera la documentación interactiva de la API, facilitando la prueba y visualización de los endpoints disponibles.
* **[jjwt:](https://github.com/jwtk/jjwt)** Utilizado para la generación y validación de tokens JWT, esencial para la gestión de sesiones y autenticación.

## Patrones de Diseño Implementados
* **Dependency Injection**: Ampliamente utilizado a través de Spring Framework, este patrón facilita la gestión de dependencias y la configuración del proyecto, promoviendo un código más limpio y mantenible.
* **Repository**: Implementado mediante Spring Data JPA para abstraer la lógica de acceso a datos. Esto simplifica las operaciones con la base de datos y mejora la mantenibilidad del código al separar la lógica de negocio de la de persistencia
* **Factory**: Empleado para la creación de objetos complejos. Este patrón permite una mayor flexibilidad y desacoplamiento en la creación de instancias, facilitando la extensión y el mantenimiento del código..
* **Singleton**: Utilizado para garantizar que una clase tenga una única instancia y proporcionar un punto de acceso global a ella. En este proyecto, AuthenticationManagerBuilder se configura como un singleton, asegurando que solo exista una instancia de este componente en la aplicación

## Principios SOLID
* **Principio de Responsabilidad Única (SRP)**: Cada clase se ha diseñado para tener una sola razón para cambiar, lo cual se logra segregando las funcionalidades en distintos servicios y repositorios, minimizando así las dependencias cruzadas.
* **Principio de Invesión de Dependencias (DIP)**: Se ha aplicado este principio para reducir la dependencia de implementaciones concretas y fomentar la dependencia de abstracciones. Esto se observa en cómo las capas superiores de la aplicación interactúan con interfaces en lugar de con implementaciones concretas, lo que facilita la sustitución de componentes y la realización de pruebas.

## Puesta en Marcha del Proyecto
El proyecto ha sido desplegado utilizando Docker y AWS para garantizar una infraestructura escalable y eficiente. A continuación, se detallan los pasos y configuraciones del despliegue:

**Descripción del Despliegue**

* **Base de Datos:** Se ha dockerizado una imagen de PostgreSQL para manejar la base de datos. Esta imagen se ejecuta en una instancia EC2 de AWS.

+ **Backend:** El backend del proyecto ha sido dockerizado y ejecutado en la misma instancia de EC2 que la base de datos. Esto permite una comunicación eficiente y rápida entre el servidor de aplicaciones y la base de datos.

* **Frontend:** La aplicación frontend ha sido desplegada en un bucket de S3, lo que permite un acceso rápido y seguro desde cualquier ubicación.

## Enlaces de Despliegue
* **Backend:** http://44.217.27.115/swagger-ui/index.html#/
* **Frontend:** http://latext-prueba.s3-website-us-east-1.amazonaws.com/

## Endpoints

### Autenticación de Usuarios
- **Descripción**: Autentica a los usuarios y retorna un token de acceso.
- **Method**: `POST`
- **Path** : `api/users/authenticate`
- **Códigos de Respuesta**:
- - `200`: Autenticación exitosa.
- - `401`: Autenticación fallida.

**Ejemplo de Uso:**

```bash
curl --request POST \
  --url http://44.217.27.115/api/users/authenticate
  --header 'Content-Type: application/json' \
  --data '{
	"email": "luis.ruz@gmail.com",
	"password": "Password123!"
}'
```

### Creación de Usuario
- **Descripción** : Registra un nuevo usuario en el sistema.
- **Método HTTP** : `POST`
- **Path** : `/api/users`
- **Códigos de Respuesta**:
- - `201`: Usuario creado exitosamente.
- - `400`: Error en los datos proporcionados.
- - `409`: El correo de xxx@xx.x ya se encuentra registrado.

**Ejemplo de Uso**
```bash
curl --request POST \
  --url http://44.217.27.115/api/users
  --header 'Authorization: Bearer [YourTokenHere]' \
  --header 'Content-Type: application/json' \
  --data '{
    "name": "Luis Ruz",
    "email": "luis@gmail.com",
    "password": "Password99",
    "role": "EXTERNO"
}'
```

### Listado de Usuarios
- **Descripción** : Retorna una lista de todos los usuarios registrados en el sistema.
- **Método HTTP** : `GET`
- **Path** : `/api/users`
- **Códigos de Respuesta** :
- - `200`: Listado de usuarios obtenido correctamente.
- - `403`: Acceso denegado.
-
**Ejemplo de Uso**:
```bash
curl --request GET \
  --url http://44.217.27.115/api/users 
  --header 'Authorization: Bearer [YourTokenHere]' \
  --header 'Content-Type: application/json' \
```


### Registro de Producto
- **Descripción** : Permite registrar un nuevo producto en el sistema.
- **Método HTTP** : `POST`
- **Path** : `/products`
- **Códigos de Respuesta** :
- - `200`: Producto registrado correctamente..
- - `403`: Acceso denegado.
    **Ejemplo de Uso**
```bash
curl --request POST \
  --url http://44.217.27.115/products
  --header 'Authorization: Bearer [YourTokenHere]' \
  --header 'Content-Type: application/json' \
  --data '{
    "name": "Producto X",
    "brand": "Marca X",
    "model": "Modelo X",
    "data": [
      {
        "price": 700.0,
        "color": "azul"
      }
    ]
  }'
```
### Listado de Productos
- **Descripción** : Retorna una lista de todos los productos registrados en el sistema.
- **Método HTTP** : `GET`
- **Path** : `/products`
- **Códigos de Respuesta** :
- - `200`: Listado de usuarios obtenido correctamente.
- - `403`: Acceso denegado.
-
**Ejemplo de Uso**:
```bash
curl --request GET \
  --url http://44.217.27.115/products 
  --header 'Authorization: Bearer [YourTokenHere]' \
  --header 'Content-Type: application/json' \
```

## Funcionalidades del Servicio
### **Autenticación de Usuarios**
* **Registro de Usuarios**: Permite a nuevos usuarios registrarse, almacenando sus credenciales de forma segura.
* **Login de Usuarios**: Autentica a los usuarios y genera un token JWT para sesiones seguras.
### Administración de Usuarios
* **Consulta de Información**: Los usuarios pueden consultar y actualizar su información personal después de autenticarse.
### Administración de Productos
* **Agregar Producto:** Permite registrar un nuevo producto en el sistema, especificando detalles como nombre, marca, modelo y características adicionales.
* **Mostrar Lista de Productos:** Permite obtener un listado de todos los productos registrados, mostrando detalles como precio y color.
## Pruebas Implementadas
Se desarrollaron pruebas unitarias para validar la funcionalidad y robustez de los servicios. Las pruebas unitarias se centraron en componentes individuales.


