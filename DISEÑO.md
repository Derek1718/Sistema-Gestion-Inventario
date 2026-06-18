# Documento de Diseño — Sistema de Gestión de Inventario

**Materia:** Aplicaciones Web
**Universidad:** Universidad de Guayaquil
**Ciclo:** 2026-2027 Ciclo 1
**Docente:** Ing. Cesar Alcivar Aray
**Repositorio:** https://github.com/Derek1718/Sistema-Gestion-Inventario

---

## 1. Integrantes y División de Tareas

| Integrante | Módulo |
|------------|--------|
| Derek Gomez | Arquitectura base + CRUD Categorías |
| Ronald Rivera | CRUD Productos |
| Dario Pinde | CRUD Sucursales |
| Frank Villalta | Movimientos de Inventario |
| Harold Mera | Alertas de Stock + Reportes |
| Jostin Carpio | Exportación CSV + Paginación |

---

## 2. Arquitectura del Sistema

El proyecto utiliza la arquitectura en capas recomendada por Spring Boot:

```
src/
└── main/
    ├── java/
    │   └── com/inventario/
    │       ├── controller/     # Recibe peticiones HTTP y devuelve vistas
    │       ├── service/        # Lógica de negocio
    │       ├── repository/     # Acceso a la base de datos
    │       ├── model/          # Entidades JPA
    │       ├── dto/            # Objetos de transferencia de datos
    │       └── config/         # Configuraciones generales
    └── resources/
        ├── templates/          # Vistas Thymeleaf (HTML)
        ├── static/             # CSS, JS, imágenes
        └── application.properties  # Configuración de la aplicación
```

### Flujo de una petición

```
Navegador → Controller → Service → Repository → Base de Datos
                                                      ↓
Navegador ← Vista (Thymeleaf) ← Controller ← Service
```

---

## 3. Modelo de Datos

### Entidades principales

#### Categoria
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long (PK) | Identificador único |
| nombre | String | Nombre de la categoría |
| descripcion | String | Descripción opcional |

#### Producto
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long (PK) | Identificador único |
| nombre | String | Nombre del producto |
| descripcion | String | Descripción del producto |
| precio | BigDecimal | Precio unitario |
| stockMinimo | Integer | Stock mínimo permitido |
| categoria | Categoria (FK) | Categoría a la que pertenece |

#### Sucursal
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long (PK) | Identificador único |
| nombre | String | Nombre de la sucursal |
| direccion | String | Dirección física |
| telefono | String | Teléfono de contacto |

#### Inventario
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long (PK) | Identificador único |
| producto | Producto (FK) | Producto en inventario |
| sucursal | Sucursal (FK) | Sucursal donde se encuentra |
| stockActual | Integer | Cantidad actual en stock |

#### Movimiento
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long (PK) | Identificador único |
| inventario | Inventario (FK) | Inventario afectado |
| tipo | Enum | ENTRADA, SALIDA, AJUSTE |
| cantidad | Integer | Cantidad del movimiento |
| fecha | LocalDateTime | Fecha y hora del movimiento |
| observacion | String | Observación opcional |

### Diagrama de relaciones

```
Categoria  1 ──── N  Producto
Producto   N ──── N  Sucursal  (mediante Inventario)
Inventario 1 ──── N  Movimiento
```

---

## 4. Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.x | Framework principal |
| Spring Data JPA | 3.x | Acceso a datos |
| Hibernate | 6.x | ORM |
| PostgreSQL | 16 | Base de datos |
| Thymeleaf | 3.x | Motor de plantillas HTML |
| Bootstrap | 5.x | Estilos y diseño |
| Maven | 3.x | Gestión de dependencias |

---

## 5. Ramas de Trabajo en Git

| Rama | Responsable | Descripción |
|------|-------------|-------------|
| `main` | Todos | Rama principal estable |
| `feature/crud-productos` | Derek Gomez | CRUD de productos |
| `feature/crud-categorias` | Ronald Rivera | CRUD de categorías |
| `feature/crud-sucursales` | Dario Pinde | CRUD de sucursales |
| `feature/movimientos` | Frank Villalta | Movimientos de inventario |
| `feature/alertas-reportes` | Harold Mera | Alertas y reportes |
| `feature/exportacion-paginacion` | Jostin Carpio | CSV y paginación |
