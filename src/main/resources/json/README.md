# Directorio JSON

Este directorio guarda varios archivos json de prueba para que testeen la aplicación usando postman.

Por cada archivo JSON, voy a especificar la URL a la cual se le deberá hacer el request y el método HTTP. Junto a la respuesta. Y en algunos casos, una NOTA.

### crear_cliente.json
* URL: http://localhost:8080/clientes
* Método: POST
* Respuesta: 201 Created
```
{
    "id": {ID DE LA BASE DE DATOS},
    "nombre": "Pedro",
    "genero": "Masculino",
    "edad": 18,
    "identificacion": "documento123",
    "direccion": "Calle Falsa 123",
    "telefono": "12-32123323",
    "password": "123456",
    "estado": true
}
```

### update_cliente.json
* URL: http://localhost:8080/clientes
* Método: PUT
* Respuesta: 200 OK
```
{
    "id": {ID DE LA BASE DE DATOS},
    "nombre": "Pedro",
    "genero": "Masculino",
    "edad": 18,
    "identificacion": "documento123",
    "direccion": "Nuevo Domicilio 123",
    "telefono": "12-32123323",
    "password": "123456",
    "estado": true
}
```


### crear_cliente_con_pocos_datos.json
* URL: http://localhost:8080/clientes
* Método: POST
* Respuesta: 400 Bad Request
```
[
    "Telefono no puede ser vacío"
]
```

### crear_cliente_menor_de_edad.json
* URL: http://localhost:8080/clientes
* Método: POST
* Respuesta: 400 Bad Request
```
[
    "Debe ser mayor de 18 años"
]
```

### crear_cuenta.json
* URL: http://localhost:8080/cuentas/{idClienteCreadoPreviamente}
* Método: POST
* Respuesta: 201 Created
* NOTA: para obtener el ID a usar:
  * http://localhost:8080/clientes
  * Método GET
  * Agarrar valor del campo ID Cliente
```
{
    "ID Cuenta": {ID DE LA BASE DE DATOS},
    "Cliente": "{NOMBRE DEL CLIENTE CON EL ID ESPECIFICADO}",
    "Numero de Cuenta": 123456,
    "Tipo de cuenta": "CORRIENTE",
    "Saldo Inicial": 2000,
    "Saldo Disponible": 2000,
    "Estado": true
}
```

### update_cuenta.json
* URL: http://localhost:8080/cuentas/{idClienteCreadoPreviamente}
* Método: PUT
* Respuesta: 200 OK
* NOTA: para obtener el ID a usar:
  * http://localhost:8080/clientes
  * Método GET
  * Agarrar valor del campo ID Cliente
```
{
    "ID Cuenta": {ID DE LA BASE DE DATOS},
    "Cliente": "{NOMBRE DEL CLIENTE CON EL ID ESPECIFICADO}",
    "Numero de Cuenta": 123456,
    "Tipo de cuenta": "CORRIENTE",
    "Saldo Inicial": 1500,
    "Saldo Disponible": 2000,
    "Estado": true
}
```

### agregar_retiro_erroneo_a_cuenta.json
* Hay un cupo diario de retiro de dinero de 1000 por dia
* URL: http://localhost:8080/movimientos/{idCuentaCreadaPreviamente}
* Método: POST
* Respuesta: 400 Bad Request
* NOTA: para obtener el ID a usar:
    * http://localhost:8080/cuentas
    * Método GET
    * Agarrar valor del campo ID Cliente
```
Cupo diario excedido
```

### agregar_deposito_a_cuenta.json
* URL: http://localhost:8080/movimientos/{idCuentaCreadaPreviamente}
* Método: POST
* Respuesta: 201 Created
* NOTA: para obtener el ID a usar:
    * http://localhost:8080/cuentas
    * Método GET
    * Agarrar valor del campo ID Cliente
```
{
    "Fecha": "Fri Jul 21 21:00:00 ART 2023",
    "Numero de Cuenta": {NUMERO DE CUENTA ASOCIADO AL ID DE CUENTA},
    "Tipo": "DEPOSITO",
    "Valor": 3212,
    "Saldo Disponible": {SALDO DISPONIBLE DE LA CUENTA DESPUÉS DE LA OPERACION},
    "ID Movimiento": {ID DE LA BASE DE DATOS}
}
```

### agregar_retiro_a_cuenta.json
* URL: http://localhost:8080/movimientos/{idCuentaCreadaPreviamente}
* Método: POST
* Respuesta: 201 Created
* NOTA: para obtener el ID a usar:
    * http://localhost:8080/cuentas
    * Método GET
    * Agarrar valor del campo ID Cliente
```
{
    "Fecha": "Sat Jul 29 21:00:00 ART 2023",
    "Numero de Cuenta": {NUMERO DE CUENTA ASOCIADO AL ID DE CUENTA},
    "Tipo": "RETIRO",
    "Valor": 950,
    "Saldo Disponible": {SALDO DISPONIBLE DE LA CUENTA DESPUÉS DE LA OPERACION},
    "ID Movimiento": {ID DE LA BASE DE DATOS}
}
```