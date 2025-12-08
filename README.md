# 🎬 Mis Películas - App Android

Una aplicación Android moderna desarrollada con **Jetpack Compose** para gestionar tu colección personal de películas.

## 📱 ¿Qué hace la app?

**Mis Películas** te permite llevar un registro organizado de tus películas favoritas con las siguientes funcionalidades:

- ✅ **Crear películas**: Añade nuevas películas con título y género
- 📝 **Editar películas**: Modifica la información de películas existentes
- 🗑️ **Eliminar películas**: Borra películas de tu lista
- ❤️ **Marcar favoritas**: Indica tus películas preferidas con un corazón
- 📋 **Listar todas**: Visualiza tu colección completa ordenada

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos

- **Android Studio** Hedgehog (2023.1.1) o superior
- **JDK** 17 o superior
- **SDK de Android** con API 24 (Android 7.0) o superior

### Pasos para ejecutar

1. **Clonar o descargar** el proyecto
2. **Abrir el proyecto** en Android Studio
3. **Esperar** a que Gradle sincronice las dependencias
4. **Conectar un dispositivo** Android o iniciar un emulador
5. **Ejecutar** la aplicación (botón ▶️ o Shift+F10)

## 🏗️ Arquitectura y decisiones técnicas

### Patrón arquitectónico

El proyecto sigue una arquitectura **MVVM (Model-View-ViewModel)** con capas bien definidas:

```
📦 Estructura del proyecto
├── ui/                          # Capa de presentación
│   ├── MainActivity.kt          # Actividad principal con navegación
│   ├── PeliculaViewModel.kt     # ViewModel (lógica de UI)
│   └── peliculas/
│       └── PeliculasScreens.kt  # Composables (UI)
├── logica/
│   ├── data/database/           # Capa de datos
│   │   ├── PeliculaEntity.kt    # Entidad Room
│   │   ├── PeliculaDao.kt       # Data Access Object
│   │   └── PeliculaDatabase.kt  # Configuración de BD
│   └── repositories/
│       └── PeliculaRepository.kt # Repositorio
└── navigation/
    └── Destinos.kt              # Rutas de navegación
```

### Tecnologías utilizadas

#### 🎨 **Jetpack Compose**
- UI declarativa 100% en Kotlin
- Material Design 3 para componentes modernos
- Composables reutilizables y mantenibles

#### 🗄️ **Room Database**
- Persistencia local de datos
- Consultas reactivas con `Flow`
- Migraciones automáticas con `fallbackToDestructiveMigration()`

#### 🧭 **Navigation Compose**
- Navegación type-safe con Kotlin Serialization
- Dos pantallas: Lista y Formulario
- Gestión del back stack automática

#### 🔄 **Coroutines y Flow**
- Operaciones asíncronas eficientes
- Flujo reactivo de datos desde la BD
- `StateFlow` para observar cambios en tiempo real

#### 🏭 **Inyección de Dependencias Manual**
- `PeliculaApp` como Application class
- ViewModelFactory personalizado
- Lazy initialization de database y repository

### Decisiones de diseño

#### **1. Entity única (PeliculaEntity)**
Se utiliza directamente la entidad de Room en la UI en lugar de crear un modelo de dominio separado. Esta decisión simplifica el código para un proyecto de este tamaño, aunque en proyectos más grandes se recomienda el patrón de mapeo Entity → Domain Model.

#### **2. Estado en el ViewModel**
Las variables `tituloActual` y `generoActual` se manejan con `mutableStateOf` en el ViewModel, permitiendo una vinculación directa con los TextField sin necesidad de un State wrapper adicional.

#### **3. Modo Edición/Creación en un solo formulario**
El `FormularioPeliculaScreen` maneja tanto la creación como la edición, diferenciando el comportamiento mediante la variable `idPeliculaEditando`. Esto reduce duplicación de código y mejora la mantenibilidad.

#### **4. Flow reactivo**
El uso de `Flow<List<PeliculaEntity>>` desde Room garantiza que cualquier cambio en la base de datos se refleje automáticamente en la UI sin necesidad de refrescar manualmente.

#### **5. Navegación Type-Safe**
Se utilizan objetos serializables (`@Serializable`) para definir rutas, eliminando errores de tipeo en strings y proporcionando seguridad de tipos en compilación.

## 🎯 Funcionalidades detalladas

### Pantalla de Lista
- **FloatingActionButton (+)**: Abre el formulario para crear una nueva película
- **Ícono de corazón**: Alterna entre favorita/no favorita (rojo/gris)
- **Ícono de lápiz**: Abre el formulario en modo edición
- **Ícono de papelera**: Elimina la película de la lista
- **Card con elevación**: Diseño Material 3 para cada ítem

### Pantalla de Formulario
- **Campo Título**: TextField para el nombre de la película
- **Campo Género**: TextField para el género (acción, drama, etc.)
- **Botón principal**: "Crear Película" o "Guardar Cambios" según el modo
- **Botón Cancelar**: Vuelve a la lista sin guardar
- **Validación**: No permite guardar con campos vacíos

## 📊 Base de datos

**Tabla: peliculas**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Int | Clave primaria autoincremental |
| titulo | String | Nombre de la película |
| genero | String | Género cinematográfico |
| isFavorite | Boolean | Marca de favorito |

## 🔧 Mejoras futuras

Algunas ideas para expandir el proyecto:

- 🔍 Búsqueda y filtrado de películas
- ⭐ Sistema de valoración por estrellas
- 🎭 Categorías predefinidas de géneros
- 📅 Fecha de visualización
- 🖼️ Imágenes o pósters de películas
- ☁️ Sincronización con backend (Firebase, etc.)
- 📱 Widget para la pantalla de inicio
- 🌙 Modo oscuro/claro

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

---

**Desarrollado con ❤️ usando Jetpack Compose**
