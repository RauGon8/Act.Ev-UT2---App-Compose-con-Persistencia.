# Movie Watchlist - Android App with Persistence

## Descripción
Esta aplicación permite gestionar una lista de películas que deseas ver o que ya has visto. Sigue una arquitectura moderna en Android utilizando **Jetpack Compose**, **Room** para la base de datos local, **DataStore** para las preferencias de usuario y **Navegación Tipada**.

## Características Principales
- **Lista de Películas**: Visualización en una `LazyColumn` con componentes de Material 3.
- **Gestión CRUD**: Crear, editar y borrar películas de la base de datos local.
- **Favoritos**: Posibilidad de marcar películas como favoritas de forma persistente.
- **Preferencias de Usuario**:
    - **Modo Oscuro**: Ajuste persistente del tema visual.
    - **Orden de la Lista**: Alternar entre orden alfabético y orden por fecha de edición.
- **Navegación Segura**: Implementación de rutas tipadas con la librería de serialización de Kotlin.

## Arquitectura y Tecnologías
- **MVVM (Model-View-ViewModel)**: Separación clara de responsabilidades.
- **Room Database**: Persistencia local con soporte para flujos reactivos (`Flow`).
- **DataStore Preferences**: Almacenamiento eficiente de ajustes de usuario.
- **Jetpack Compose**: UI declarativa con Material 3.
- **Manual Dependency Injection**: Uso de un `AppContainer` para gestionar las dependencias del repositorio y DataStore.
- **Kotlin Coroutines & Flow**: Manejo de asincronía y reactividad en toda la aplicación.

## Decisiones de Diseño
- Se ha optado por un esquema de paquetes limpio (`data`, `domain`, `ui`) para facilitar la escalabilidad.
- El repositorio actúa como mediador, mapeando entidades de base de datos a modelos de dominio para mantener la UI independiente de la implementación de persistencia.
- El uso de `collectAsStateWithLifecycle()` (o `collectAsState`) asegura que la UI se actualice automáticamente cuando cambian los datos en Room o DataStore.

## Cómo Ejecutar
1. Clonar el repositorio.
2. Abrir el proyecto en **Android Studio Ladybug** (o superior).
3. Sincronizar Gradle.
4. Ejecutar en un emulador o dispositivo físico con Android 8.0+.

---
*Desarrollado como proyecto final para la UT2 de Programación Multimedia y Dispositivos Móviles.*
