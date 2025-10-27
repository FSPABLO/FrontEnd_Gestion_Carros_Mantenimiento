# FrontEnd_Gestion_Carros_Mantenimiento
# 🚗 Frontend – Java Swing (MVC)

Gestor de **Carros y Mantenimientos** que consume un backend por **sockets**. Permite iniciar sesión, listar carros del usuario y, al **seleccionar un carro**, ver/crear/editar/borrar sus **mantenimientos**. La UI usa **SwingWorker** (no bloquea la interfaz), **Observer** para refrescar tablas y un **overlay** de carga.

---

## ✨ Características
- **Login** contra backend (obtención de `userId`).
- **Cars**: listado del usuario; selección fija el **carro activo**.
- **Maintenances**: CRUD ligado al carro activo (`REPAIR | MOD | ROUTINE`).
- **Mensajería**: suscripción a broadcasts (puerto 7001).

---

## 🧱 Arquitectura y Tecnologías
- **Java 21**, **Swing**.
- **MVC** + **Observer** (eventos `CREATED / UPDATED / DELETED`).
- **Gson** para DTOs JSON.
- **Servicios** que envían `RequestDto(controller, request, data, token)` por socket.

---

## 📁 Estructura
Presentation/
Controllers/  -> LoginController, CarsController, MaintenanceController
Models/       -> CarsTableModel, MaintenanceTableModel
Views/        -> LoginView, MainView, CarsView, LoadingOverlay
Services/
BaseService.java, AuthService, CarService, MaintenanceService
Domain/Dtos/
auth/, cars/, maintenance/ (Request/Response DTOs)
cr.ac.una.Main -> punto de entrada (abre LoginView)

---

## 🔌 Puertos y controladores
- **Requests**: `7070`  → controladores `"Auth"`, `"Cars"`, `"Maintenance"`
- **Mensajes**: `7001`  → broadcasts (login, etc.)

---

## ▶️ Ejecución
**Requisitos**: JDK 21, Maven, backend corriendo (DB configurada).

1. Abre el proyecto (IntelliJ/Eclipse) y usa **SDK 21**.
2. Ajusta host/puertos si lo necesitas en `LoginController.openMainView()` (por defecto `localhost:7070` y `7001`).
3. Ejecuta `cr.ac.una.Main` → se abre **Login**.

---

## 🕹️ Uso
1. Inicia sesión.
2. Ve a **Cars** → selecciona un carro (queda como *carro activo*).
3. En **Maintenance** (panel inferior): escribe **Descripción**, elige **Tipo** y pulsa **Agregar**.
4. Para editar o borrar: selecciona un mantenimiento en la tabla y usa **Update** / **Delete**.

---

## 🧰 Solución de problemas
- **No aparecen carros**: confirma backend activo y usuario con datos; revisa credenciales.
- **No carga mantenimientos**: asegúrate de **seleccionar un carro** primero.
- **“Unknown controller”**: usa exactamente `"Maintenance"` (sin “s”) en `RequestDto.controller`.
- **Enum inválido**: el tipo debe ser **REPAIR / MOD / ROUTINE**.
- **Imports en rojo**: selecciona **Project SDK 21** y sincroniza dependencias (Gson).

---

## 🗺️ Roadmap breve
- Paginación/ordenamiento de tablas.
- Validaciones y notificaciones más amigables.
- Manejo de sesión/token real desde login.
