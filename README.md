# Plataforma de Fondos Voluntarios de Pensión (FPV) y Fondos de Inversión Colectiva (FIC)

## Descripción
Esta es una aplicación desarrollada en **Spring Boot** con **MongoDB** como base de datos, que permite a los clientes de **BTG Pactual** gestionar sus fondos voluntarios de pensión (FPV) y fondos de inversión colectiva (FIC). Los clientes pueden suscribirse a nuevos fondos, cancelar suscripciones, ver el historial de transacciones, y recibir notificaciones por correo electrónico o SMS después de cada acción.

## Características
- Suscripción a fondos.
- Cancelación de fondos.
- Ver historial de transacciones (aperturas y cancelaciones).
- Envío de notificaciones por email o SMS después de cada suscripción o cancelación.
- Validación de saldo disponible y monto mínimo de suscripción.

## Requisitos del sistema
- **Java 17** o superior.
- **Maven** o **Gradle** para gestionar dependencias.
- **MongoDB** instalado y configurado.
- **Node.js** y **npm** para gestionar el frontend (si se utiliza Angular).

## Tecnologías utilizadas
- **Backend**: Spring Boot, MongoDB
- **Frontend**: Angular 10 (opcional)
- **Notificaciones**: Email/SMS
- **Pruebas unitarias**: JUnit, Mockito

## Colecciones de MongoDB
La aplicación utiliza las siguientes colecciones de MongoDB:

- **clientes**: Contiene información de los clientes registrados en la plataforma.
  ```json
  {
  "_id": "1",
  "nombre": "Leonel Messi",
  "fondosSuscritos": [],
  "saldo": 500000,
  "email": "cliente@btg.com",
  "telefono": "3001234567",
  "_class": "com.btg.pactual.model.Cliente"
}

- **fondos**: Contiene información de los fondos disponibles en la plataforma.
  ```json
  [{
  "_id": 1,
  "categoria": "FPV",
  "nombre": "FPV_BTG_PACTUAL_RECAUDADORA",
  "montoMinimo": 75000
  },
  {
  "_id": 2,
  "nombre": "FPV_BTG_PACTUAL_ECOPETROL",
  "montoMinimo": 125000,
  "categoria": "FPV"
  },
  {
  "_id": 3,
  "nombre": "DEUDAPRIVADA",
  "montoMinimo": 50000,
  "categoria": "FIC"
  }]

- **transacciones**: Contiene información de las transacciones(suscripciones/cancelaciones) echas en la plataforma.
  ```json
  {
    "_id": "ObjectId",
    "nombre": "String",
    "saldo": "Integer",
    "suscripciones": [
      {
        "fondoId": "ObjectId",
        "nombreFondo": "String",
        "fechaSuscripcion": "Date",
        "monto": "Integer"
      }
    ]
  }

- **notificaciones**: Contiene información de los notificaciones enviadas.
  ```json
  {
    "_id": "ObjectId",
    "nombre": "String",
    "saldo": "Integer",
    "suscripciones": [
      {
        "fondoId": "ObjectId",
        "nombreFondo": "String",
        "fechaSuscripcion": "Date",
        "monto": "Integer"
      }
    ]
  }

## Configuracion de Email
#### La aplicación usa sistema de mensajeria Email, es necesario configurar en el application.properties

 spring.mail.username=tucorreo@gmail.com
 spring.mail.password=tu contraseña

 mail.enable=true -> para activar o desactivar el servicio de email

## Parte 2 de la prueba 

 En directorio bgt-pactual-back\sql esta el archivo
 consulta.sql con la solucion

## CloudFormation

 En el directorio bgt-pactual\cloudFormation esta el archivo
 BTG-Pactual-back-front-mongo-resources-template.yml
 con todas las instrucciones de creacion de recursos en aws

## Nota: 
Se debe generar el KeyPair desde la consola de aws de quien ejecute la prueba