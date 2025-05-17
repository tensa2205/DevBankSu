# Plan de Migración a Microservicios - DevBankSu

## Fase 1: Preparación y Análisis
### 1. Identificación de Dominios y Bounded Contexts
- Microservicio de Clientes
- Microservicio de Cuentas
- Microservicio de Movimientos
- Microservicio de Reportes
- Nuevo Microservicio de Autenticación/Autorización

### 2. Definición de Arquitectura Base
- Implementar API Gateway (usando Spring Cloud Gateway)
- Configurar Service Discovery (Eureka)
- Implementar Config Server centralizado
- Definir Circuit Breakers (Resilience4j)
- Implementar Distributed Tracing (Spring Cloud Sleuth + Zipkin)

## Fase 2: Implementación Base
### 1. Crear Infraestructura Común
- Crear repositorio para Config Server
- Configurar Docker y Docker Compose
- Implementar base de datos separada para cada servicio
- Configurar Kubernetes (opcional)

### 2. Implementar Servicio de Autenticación
- Migrar lógica de autenticación del monolito
- Implementar JWT o OAuth2
- Crear endpoints de autenticación
- Migrar tabla de clientes relacionada a autenticación

## Fase 3: Migración de Servicios Core
### 1. Microservicio de Clientes
- Migrar `ClienteController`, `ClienteService` y entidades relacionadas
- Crear API REST para gestión de clientes
- Implementar eventos para cambios en clientes
- Crear base de datos dedicada

### 2. Microservicio de Cuentas
- Migrar `CuentaController`, `CuentaService` y entidades relacionadas
- Implementar comunicación con servicio de Clientes
- Crear eventos para cambios en cuentas
- Crear base de datos dedicada

### 3. Microservicio de Movimientos
- Migrar `MovimientoController`, `MovimientoService` y entidades relacionadas
- Implementar comunicación con servicio de Cuentas
- Crear eventos para nuevos movimientos
- Crear base de datos dedicada

### 4. Microservicio de Reportes
- Migrar `ReporteController` y `ReporteService`
- Implementar patrón CQRS para consultas
- Crear base de datos de solo lectura
- Implementar consumidor de eventos

## Fase 4: Implementación de Patrones y Mejoras
### 1. Implementar Event Sourcing
- Crear bus de eventos (Apache Kafka o RabbitMQ)
- Implementar productores y consumidores de eventos
- Mantener consistencia eventual entre servicios

### 2. Mejorar Resiliencia
- Implementar Circuit Breakers
- Configurar timeouts y retries
- Implementar fallbacks

### 3. Monitoreo y Observabilidad
- Configurar ELK Stack para logs centralizados
- Implementar métricas con Prometheus
- Configurar dashboards en Grafana
- Implementar health checks

## Fase 5: Testing y Documentación
### 1. Implementar Tests
- Tests unitarios por servicio
- Tests de integración
- Tests de contratos (Contract Testing)
- Tests de rendimiento

### 2. Documentación
- Documentar APIs (Swagger/OpenAPI)
- Documentar arquitectura
- Crear guías de desarrollo
- Documentar procesos de CI/CD

## Fase 6: Despliegue y Operación
### 1. Configurar CI/CD
- Implementar pipelines por servicio
- Configurar despliegue automático
- Implementar blue-green deployments

### 2. Monitoreo en Producción
- Configurar alertas
- Implementar logging distribuido
- Monitorear métricas de negocio
- Configurar dashboards operativos

## Recomendaciones Adicionales
1. **Implementar gradualmente**: No migrar todo de una vez. Comenzar con un servicio pequeño y menos crítico.
2. **Mantener el monolito**: Durante la migración, el monolito seguirá funcionando. Ir migrando funcionalidad poco a poco.
3. **Versionar APIs**: Implementar versionado de APIs desde el inicio.
4. **Seguridad**: Implementar seguridad en cada capa (API Gateway, servicios, bases de datos).
5. **Datos**: Considerar la estrategia de migración de datos históricos.

## Orden Sugerido de Migración
1. Clientes (es la base de todo)
2. Autenticación (separarlo de Clientes)
3. Cuentas
4. Movimientos
5. Reportes (depende de todos los anteriores)

## Estrategia de Control de Versiones (Git)
### Estructura de Ramas
1. **Rama Principal de Microservicios**:
   ```bash
   main/microservices   # Rama base para la migración
   ```

2. **Ramas por Servicio**:
   ```bash
   microservices/auth-service      # Servicio de Autenticación
   microservices/cliente-service   # Servicio de Clientes
   microservices/cuenta-service    # Servicio de Cuentas
   microservices/movimiento-service # Servicio de Movimientos
   microservices/reporte-service   # Servicio de Reportes
   microservices/api-gateway       # API Gateway
   microservices/config-server     # Servidor de Configuración
   ```

### Proceso de Branching
1. **Crear Rama Base**:
   ```bash
   git checkout main
   git checkout -b main/microservices
   ```

2. **Crear Ramas de Infraestructura**:
   ```bash
   git checkout main/microservices
   git checkout -b microservices/config-server
   
   git checkout main/microservices
   git checkout -b microservices/api-gateway
   ```

3. **Crear Ramas de Servicios**:
   ```bash
   git checkout main/microservices
   git checkout -b microservices/auth-service
   
   git checkout main/microservices
   git checkout -b microservices/cliente-service
   # Repetir para cada servicio
   ```

### Ventajas de esta Estructura
- Mantiene el monolito funcionando en `main`
- Permite desarrollo paralelo de diferentes servicios
- Facilita el control de versiones por servicio
- Permite hacer merge gradual a `main/microservices`
- Facilita el rollback si algo sale mal

---
*Nota: Este plan puede tomar varios meses dependiendo del tamaño del equipo y la complejidad de cada servicio. Es importante mantener el sistema funcionando durante la migración y hacer pruebas exhaustivas en cada fase.*

## Estrategias de Repositorio Git

Existen dos enfoques principales para manejar el código durante la migración:

### 1. Enfoque Monorepo
```
DevBankSu/                      # Un solo repositorio
├── monolito/                   # Código original
│   ├── src/
│   ├── pom.xml
│   └── ... (otros archivos)
│
└── microservices/             # Nuevos microservicios
    ├── config-server/
    ├── api-gateway/
    ├── cliente-service/
    └── ... (otros servicios)
```

**Ventajas**:
- Más fácil de mantener durante la transición
- Control de versiones centralizado
- Facilita compartir código común
- Mejor para equipos pequeños/medianos
- Simplifica la gestión de dependencias
- CI/CD más simple inicialmente

**Desventajas**:
- El repositorio crece significativamente
- Puede ser más lento con Git
- Menos autonomía entre equipos

### 2. Enfoque Multi-repo
```
DevBankSu-Monolito/            # Repositorio original
DevBankSu-Config-Server/       # Repositorio independiente
DevBankSu-API-Gateway/         # Repositorio independiente
DevBankSu-Cliente-Service/     # Repositorio independiente
... (un repo por servicio)
```

**Ventajas**:
- Mayor autonomía entre equipos
- Mejor para equipos grandes
- Repositorios más pequeños y manejables
- Mejor alineado con microservicios puros
- Control de acceso más granular

**Desventajas**:
- Más complejo de mantener
- Requiere mejor gestión de versiones
- CI/CD más complejo
- Más difícil compartir código común

### Recomendación para DevBankSu
Para tu proyecto, recomiendo comenzar con el **enfoque Monorepo** por las siguientes razones:
1. Estás en fase de transición
2. Facilita la refactorización gradual
3. Permite mantener todo el código relacionado junto
4. Más fácil de manejar durante la migración
5. Puedes migrar a multi-repo más adelante si es necesario

### Proceso con Monorepo
1. **Reorganizar el Repositorio Actual**:
   ```bash
   # En la rama main/microservices
   mkdir monolito
   git mv src pom.xml mvnw* monolito/
   mkdir microservices
   ```

2. **Estructura Final**:
   ```
   DevBankSu/
   ├── monolito/              # Código original movido aquí
   │   ├── src/
   │   ├── pom.xml
   │   └── ... (otros archivos)
   │
   └── microservices/         # Nuevos servicios aquí
       ├── config-server/
       ├── api-gateway/
       └── ... (otros servicios)
   ```

3. **Gestión de Dependencias**:
   - Crear un `pom.xml` padre en la raíz (opcional)
   - Mantener versiones consistentes entre servicios
   - Compartir configuraciones comunes

Esta estructura permite una migración más controlada y mantiene todo el código en un solo lugar durante la transición.

## Estructura de Directorios Recomendada

### 1. Estructura Base
```
├── DevBankSu/                  # Proyecto Monolito Original
│   ├── src/
│   ├── pom.xml
│   └── ... (otros archivos del monolito)
│
└── microservices/             # Directorio para todos los microservicios
    ├── config-server/         # Servidor de Configuración
    ├── api-gateway/           # API Gateway
    ├── eureka-server/         # Servidor de Descubrimiento
    ├── cliente-service/       # Servicio de Clientes
    ├── cuenta-service/        # Servicio de Cuentas
    ├── movimiento-service/    # Servicio de Movimientos
    ├── reporte-service/       # Servicio de Reportes
    └── docker-compose.yml     # Compose file para todos los servicios
```

### 2. Proceso de Creación
1. **En la rama main/microservices**:
   ```bash
   # Crear directorio base de microservicios
   mkdir microservices
   cd microservices
   
   # Crear directorios para cada servicio
   mkdir config-server api-gateway eureka-server
   mkdir cliente-service cuenta-service movimiento-service reporte-service
   ```

2. **Estructura Interna de cada Servicio**:
   ```
   cliente-service/
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   └── resources/
   │   └── test/
   ├── Dockerfile
   ├── pom.xml
   └── README.md
   ```

3. **Docker Compose Central**:
   ```yaml
   # microservices/docker-compose.yml
   version: '3.8'
   services:
     config-server:
       build: ./config-server
       ports:
         - "8888:8888"
     
     eureka-server:
       build: ./eureka-server
       ports:
         - "8761:8761"
       depends_on:
         - config-server
     
     api-gateway:
       build: ./api-gateway
       ports:
         - "8080:8080"
       depends_on:
         - eureka-server
     
     cliente-service:
       build: ./cliente-service
       ports:
         - "8081:8081"
       depends_on:
         - api-gateway
     # ... otros servicios
   ```

### 3. Ventajas de esta Estructura
- Separación clara entre el monolito y los microservicios
- Facilita el desarrollo y despliegue independiente
- Permite tener un docker-compose centralizado
- Mejor organización de los repositorios
- Facilita la gestión de dependencias comunes
- Simplifica la configuración de CI/CD

## Guía de Extracción de Microservicio
Usando como ejemplo el Servicio de Clientes, estos son los pasos a seguir para extraer un microservicio del monolito:

### 1. Preparación del Nuevo Proyecto
1. **Crear Estructura Base**:
   ```bash
   mkdir cliente-service
   cd cliente-service
   ```

2. **Inicializar Nuevo Proyecto Spring Boot**:
   - Usar Spring Initializr (https://start.spring.io/)
   - Dependencias iniciales:
     - Spring Web
     - Spring Data JPA
     - Spring Cloud Config Client
     - Spring Cloud Netflix Eureka Client
     - Spring Cloud Sleuth
     - Spring Boot Actuator
     - PostgreSQL Driver (o el driver de tu BD)
     - Lombok
     - Validation

3. **Configuración Base**:
   ```yaml
   # application.yml
   spring:
     application:
       name: cliente-service
     cloud:
       config:
         uri: http://config-server:8888
         fail-fast: true
     datasource:
       url: jdbc:postgresql://cliente-db:5432/cliente_db
     jpa:
       hibernate:
         ddl-auto: validate
   
   eureka:
     client:
       serviceUrl:
         defaultZone: http://eureka-server:8761/eureka/
   ```

### 2. Migración del Código
1. **Identificar y Copiar Clases Necesarias**:
   - `Cliente.java`
   - `ClienteController.java`
   - `ClienteService.java`
   - `ClienteRepository.java`
   - `ClienteDTO.java`
   - `ClienteMapper.java`
   - Excepciones relacionadas
   - Utilidades necesarias

2. **Adaptar el Código**:
   - Actualizar los imports
   - Remover dependencias del monolito
   - Adaptar anotaciones de Spring Cloud
   - Implementar nuevos endpoints si son necesarios
   - Agregar validaciones

3. **Implementar Nuevas Funcionalidades**:
   ```java
   @RestController
   @RequestMapping("/api/v1/clientes")  // Agregar versionado
   public class ClienteController {
       // Endpoints existentes con nuevas características
       // Nuevos endpoints para comunicación entre servicios
   }
   ```

### 3. Base de Datos
1. **Migración de Schema**:
   - Crear script Flyway/Liquibase para la estructura
   - Incluir solo las tablas necesarias
   - Agregar nuevos campos si son necesarios

2. **Migración de Datos**:
   ```sql
   -- V1__init_schema.sql
   CREATE TABLE clientes (
       id SERIAL PRIMARY KEY,
       nombre VARCHAR(255) NOT NULL,
       -- otros campos
   );
   
   -- V2__migrate_data.sql
   INSERT INTO clientes (id, nombre, ...)
   SELECT id, nombre, ...
   FROM monolito.clientes;
   ```

### 4. Configuración de API Gateway
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: cliente-service
          uri: lb://cliente-service
          predicates:
            - Path=/api/v1/clientes/**
```

### 5. Pruebas
1. **Implementar Tests**:
   ```java
   @SpringBootTest
   class ClienteServiceTests {
       // Tests unitarios
       // Tests de integración
       // Tests de contratos
   }
   ```

2. **Pruebas de Integración**:
   - Probar comunicación con otros servicios
   - Verificar registro en Eureka
   - Validar configuración desde Config Server

### 6. Despliegue
1. **Dockerfile**:
   ```dockerfile
   FROM openjdk:17-jdk-slim
   COPY target/*.jar app.jar
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```

2. **Docker Compose**:
   ```yaml
   version: '3.8'
   services:
     cliente-service:
       build: .
       ports:
         - "8081:8081"
       environment:
         - SPRING_PROFILES_ACTIVE=docker
   ```

### 7. Monitoreo
1. **Actuator Endpoints**:
   ```yaml
   management:
     endpoints:
       web:
         exposure:
           include: health,metrics,prometheus
   ```

2. **Logging**:
   ```yaml
   logging:
     pattern:
       console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} trace_id=%X{traceId} span_id=%X{spanId} - %msg%n"
   ```

### 8. Circuit Breaker
```java
@CircuitBreaker(name = "clienteService", fallbackMethod = "fallbackMethod")
public ClienteDTO obtenerCliente(Long id) {
    // Implementación
}
```

### 9. Actualización del Monolito
1. **Remover Código Migrado**:
   - Marcar clases como deprecated
   - Redirigir llamadas al nuevo servicio
   - Mantener temporalmente endpoints duplicados

2. **Implementar Cliente Feign**:
   ```java
   @FeignClient(name = "cliente-service")
   public interface ClienteClient {
       @GetMapping("/api/v1/clientes/{id}")
       ClienteDTO obtenerCliente(@PathVariable Long id);
   }
   ```

### 10. Validación Final
- Pruebas de carga
- Monitoreo de métricas
- Verificación de logs
- Pruebas de resilencia
- Documentación de APIs

Este proceso se repite para cada microservicio, adaptando los detalles específicos según las necesidades de cada uno. 