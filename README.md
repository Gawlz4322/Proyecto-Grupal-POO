# Control de Finanzas Personales (CFP)

Sistema de control de finanzas personales con autenticación de usuarios desarrollado en Java con Swing.

## 📋 Características

- ✅ Sistema de autenticación seguro (PBKDF2 con HMAC-SHA256)
- ✅ Registro de usuarios con validación
- ✅ Gestión de gastos e ingresos
- ✅ Historial de movimientos
- ✅ Persistencia de datos en JSON

## 🛠️ Tecnologías

- **Java 8+**
- **Maven** - Gestión de dependencias y construcción
- **Swing** - Interfaz gráfica
- **Gson 2.10.1** - Serialización JSON
- **JUnit 4.13.2** - Testing (configurado)

## 📁 Estructura del Proyecto

```
Proyecto-Grupal-POO/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Main.java              # Punto de entrada
│   │   │   ├── Controlador/           # Lógica de negocio
│   │   │   ├── Modelo/                # Entidades
│   │   │   └── Vista/                 # Interfaces gráficas
│   │   └── resources/
│   │       └── data/
│   │           └── users.json         # Base de datos de usuarios
│   └── test/
│       └── java/                      # Tests unitarios
├── pom.xml                            # Configuración Maven
└── README.md
```

## 🚀 Requisitos Previos

1. **Java JDK 8 o superior**
   ```bash
   java -version
   ```

2. **Apache Maven**
   - Descargar desde: https://maven.apache.org/download.cgi
   - Agregar Maven al PATH del sistema
   - Verificar instalación:
   ```bash
   mvn -version
   ```

## 📦 Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd Proyecto-Grupal-POO
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Empaquetar (crear JAR ejecutable)**
   ```bash
   mvn clean package
   ```

## ▶️ Ejecución

### Opción 1: Ejecutar con Maven
```bash
mvn exec:java -Dexec.mainClass="Main"
```

### Opción 2: Ejecutar JAR compilado
```bash
java -jar target/Proyecto-Grupal-POO-1.0-SNAPSHOT.jar
```

### Opción 3: Desde IDE
- Abrir el proyecto en IntelliJ IDEA, Eclipse o NetBeans
- Ejecutar la clase `Main.java`

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con reporte
mvn test jacoco:report
```

## 📝 Uso de la Aplicación

1. **Registro de Usuario**
   - Al iniciar, hacer clic en "¿No tienes cuenta? Regístrate"
   - Ingresar usuario (mínimo 3 caracteres, sin espacios)
   - Ingresar contraseña (mínimo 8 caracteres)
   - Confirmar contraseña

2. **Iniciar Sesión**
   - Ingresar usuario y contraseña
   - Hacer clic en "Ingresar"

3. **Gestión de Finanzas**
   - **Añadir gasto**: Registra un gasto y reduce el saldo
   - **Añadir ingreso**: Registra un ingreso y aumenta el saldo
   - **Ver historial**: Muestra todos los movimientos realizados

## 🔒 Seguridad

- Contraseñas hasheadas con **PBKDF2-HMAC-SHA256**
- **65,536 iteraciones** para mayor seguridad
- **Salt aleatorio** único por usuario
- Limpieza de contraseñas en memoria después de uso

## 🏗️ Comandos Maven Útiles

```bash
# Limpiar archivos compilados
mvn clean

# Compilar sin ejecutar tests
mvn compile -DskipTests

# Ver árbol de dependencias
mvn dependency:tree

# Actualizar dependencias
mvn versions:display-dependency-updates

# Generar documentación JavaDoc
mvn javadoc:javadoc
```

## 📂 Archivos de Datos

Los datos de usuarios se almacenan en:
```
src/main/resources/data/users.json
```

**Formato:**
```json
[
  {
    "id": "uuid",
    "username": "usuario",
    "passwordHash": "hash-base64",
    "salt": "salt-base64",
    "createdAt": "2025-11-27T22:55:00Z"
  }
]
```

## ⚠️ Notas Importantes

- Los datos financieros (saldo, historial) **NO se persisten** actualmente
- Al cerrar la aplicación, solo se mantienen los usuarios registrados
- El saldo se reinicia a $0 en cada sesión

## 🔧 Configuración del IDE

### IntelliJ IDEA
1. File → Open → Seleccionar carpeta del proyecto
2. Maven se detectará automáticamente
3. Esperar a que descargue dependencias
4. Run → Run 'Main'

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Seleccionar carpeta del proyecto
3. Right-click proyecto → Run As → Java Application

### VS Code
1. Instalar extensión "Extension Pack for Java"
2. Abrir carpeta del proyecto
3. F5 para ejecutar

## 📄 Licencia

Proyecto académico - Programación Orientada a Objetos

## 👥 Autores

Proyecto Grupal POO

---

**Última actualización:** 2025-11-27
