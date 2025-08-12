# 🚨 Sistema de Alarma Personal - JavaFX

## 📋 Descripción del Proyecto

Sistema de alarma personal desarrollado en Java con interfaz gráfica moderna usando JavaFX. 
## 🎯 Características Principales

- **Reloj Digital en Tiempo Real**: Visualización de hora, minutos y segundos
- **Gestión de Alarmas**: Crear, editar y eliminar alarmas programadas
- **Notificaciones de Audio**: Reproducción de archivos de audio personalizados (.wav)
- **Persistencia de Datos**: Base de datos PostgreSQL con JPA/Hibernate
- **Arquitectura MVC**: Separación clara de responsabilidades

## 🛠️ Stack Tecnológico

### **Backend & Base de Datos**
- **Java 16** - Lenguaje principal
- **Spring Boot 2.6.2** - Framework de desarrollo
- **Spring Data JPA** - Persistencia de datos
- **Hibernate 5.4.0** - ORM
- **PostgreSQL 42.3.1** - Base de datos relacional
- **JPA 2.2** - API de persistencia

### **Frontend & UI**
- **JavaFX 16** - Framework de interfaz gráfica
- **JFoenix 9.0.0** - Componentes Material Design para JavaFX
- **FontAwesomeFX 8.9** - Iconografía moderna
- **FXML** - Diseño de interfaces declarativo
- **CSS** - Estilos personalizados

### **Herramientas de Desarrollo**
- **Maven 3.8.0** - Gestión de dependencias y build
- **Log4j2** - Sistema de logging
- **Jersey 2.34** - Framework REST (preparado para futuras APIs)

## 🏗️ Arquitectura del Sistema

### **Patrón MVC (Model-View-Controller)**
```
src/main/java/
├── controller/          # Controladores de la interfaz
│   ├── AlarmaController.java    # Controlador principal de alarmas
│   ├── Principal.java          # Punto de entrada de la aplicación
│   └── emergente/              # Controladores de ventanas emergentes
├── Entidades/          # Modelos de datos
│   └── Alarma.java            # Entidad principal con JPA
└── Util/               # Utilidades del sistema
    ├── JPAUtil.java           # Configuración de JPA
    └── FileImagUtil.java      # Utilidades para archivos
```

### **Base de Datos**
- **Tabla**: `alarma`
- **Campos**: id, hora, asunto, nameAudio, urlAudio
- **Configuración**: PostgreSQL local con usuario `postgres`

## 📁 Estructura del Proyecto

```
Personal-alarma/
├── src/main/java/           # Código fuente Java
├── src/main/resources/      # Recursos de la aplicación
│   ├── controller/          # Archivos FXML
│   ├── css/                # Estilos CSS
│   ├── imagenes/           # Imágenes e iconos
│   ├── audio/              # Archivos de audio
│   └── META-INF/           # Configuración JPA
├── target/                  # Archivos compilados
├── pom.xml                  # Configuración Maven
└── README.md               # Este archivo
```

## 🔧 Configuración y Ejecución

### **Requisitos Previos**
- Java 16 o superior
- Maven 3.6+
- PostgreSQL 12+
- IDE compatible con JavaFX (IntelliJ IDEA, Eclipse, NetBeans)

### **Configuración de Base de Datos**
```sql
-- Crear base de datos
CREATE DATABASE alarma;

-- Configuración en persistence.xml
jdbc:postgresql://localhost:5432/alarma
Usuario: postgres
Password: admin
```

### **Ejecución del Proyecto**
```bash
# Compilar y ejecutar
mvn clean javafx:run

# Solo compilar
mvn clean compile

# Ejecutar con dependencias
mvn clean package
java -jar target/Alarma-1.jar
```

## 📝 Licencia

Proyecto desarrollado ee portafolio personal.

---

**Desarrollado con ❤️ usando Java, JavaFX y Spring Boot**
