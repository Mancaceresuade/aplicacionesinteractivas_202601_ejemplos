# Guía: clase 1 — Java de consola y Spring Boot

Esta guía resume en **dos partes** el material de la carpeta **`Ejemplosjava`** (ejemplo en Java “puro”, orientado a VS Code) y la carpeta **`demo`** (API REST mínima con **Spring Boot**).

## Qué vas a ver en este repo

- **Parte 1:** colecciones, **Streams** de Java y un repaso de **polimorfismo** con una interfaz (`IComprable`).
- **Parte 2:** mismo dominio modelado en clases, expuesto por HTTP con **`GET /ping`** y **`GET /clientes`**.

---

# Parte 1: carpeta `Ejemplosjava`

La práctica vive en **`Ejemplosjava/ejemplos`**. Es un workspace pensado para **Visual Studio Code** con el soporte de Java (extensión Pack for Java), sin Maven en esa carpeta.

## Qué hace el ejemplo (`ejemplos/src/App.java`)

El método `proceso()` muestra dos ideas centrales:

1. **Colecciones y Streams:** se arma un `ArrayList<Cliente>`, se filtra por nombre con **`stream().filter(...)`** y se imprime con **`forEach`**.
2. **Polimorfismo:** se usa un `ArrayList<IComprable>` con instancias de **`Proveedor`** y **`Representante`**. El mensaje **`comprar()`** se resuelve distinto según el tipo real de cada objeto.

## Estructura de carpetas (VS Code)

| Carpeta | Rol |
|---------|-----|
| `src` | Código fuente (por ejemplo `App.java`). |
| `lib` | Dependencias `.jar` (si las hubiera). |
| `bin` | Salida compilada (por defecto). |

La configuración del proyecto Java está en **`ejemplos/.vscode/settings.json`** (`java.project.sourcePaths`, `java.project.outputPath`, etc.).

## Requisitos previos

1. **JDK** instalado (versión acorde a lo que pida la materia; conviene LTS reciente).
2. **Visual Studio Code** con el **Extension Pack for Java** (o tu IDE equivalente si importás la carpeta como proyecto Java simple).

## Cómo ejecutarlo

1. Abrí VS Code en la carpeta **`Ejemplosjava/ejemplos`**.
2. Abrí **`src/App.java`**.
3. Usá **Run Java** (o el comando equivalente del IDE) sobre la clase `App` con método **`main`**.

Si preferís terminal, desde **`Ejemplosjava/ejemplos`** (ajustá la ruta del JDK si hace falta):

```bash
javac -d bin src/App.java
java -cp bin App
```

## Nota sobre las clases de modelo

`App.java` referencia tipos como **`Cliente`**, **`Proveedor`**, **`Representante`** e **`IComprable`**. En un entrega completa esas clases deben estar en **`src/`** junto con `App` (mismo paquete por defecto, o bien con `package` e **`import`** coherentes). Si al compilar te falta algún `.java`, revisá el enunciado de la clase o alineá esas clases con el modelo de la **Parte 2** (misma idea de dominio, distinto empaquetado).

## Solución de problemas (Parte 1)

- **No aparece “Run Java”:** instalá o habilitá el **Language Support for Java** / **Debugger for Java** desde el pack de Java en VS Code.
- **Error de compilación “cannot find symbol” en `Cliente`, etc.:** falta algún archivo fuente o un `import` / `package` incorrecto; unificá paquetes o mové las clases a `src` como indique la consigna.

---

# Parte 2: carpeta `demo`

El proyecto **`demo`** es una aplicación **Spring Boot** (Java **21**, parent **4.0.3**) que expone una API web mínima. Sirve para ver cómo los mismos conceptos de objetos (`Persona`, `Cliente`, herencia, interfaz `IComprable`, etc.) se integran en un servidor HTTP.

## Qué incluye el proyecto

| Elemento | Descripción breve |
|----------|-------------------|
| `pom.xml` | Maven: `spring-boot-starter-webmvc`, tests con `spring-boot-starter-webmvc-test`. |
| `DemoApplication.java` | Punto de entrada `@SpringBootApplication`. |
| `Ping` | `GET /ping` → responde texto **`pong`**. |
| `ClienteController` | `GET /clientes` → devuelve JSON con una lista en memoria (`ArrayList<Cliente>`). |
| Paquete `model` | Clases de dominio: `Persona`, `Cliente`, `Proveedor`, `Representante`, `IComprable`. |

El controlador de clientes usa una lista en memoria **solo a fines didácticos** (no es persistencia ni capa de servicio).

## Requisitos previos

1. **JDK 21** (coincide con `<java.version>21</java.version>` del `pom.xml`).
2. Opcional: **Maven** instalado globalmente; si no, usá el **wrapper** incluido (`mvnw` / `mvnw.cmd`).

## Paso 1: Levantar la aplicación

1. Abrí una terminal en la carpeta **`demo`**.
2. En **Windows**:

   ```bash
   .\mvnw.cmd spring-boot:run
   ```

   También podés ejecutar **`DemoApplication`** desde tu IDE.

3. Por defecto Spring Boot escucha en **`http://localhost:8080`** (salvo que cambies `server.port` en `application.properties`).

## Paso 2: Probar los endpoints

| Método | Ruta | Respuesta esperada |
|--------|------|----------------------|
| GET | `http://localhost:8080/ping` | Texto plano: `pong`. |
| GET | `http://localhost:8080/clientes` | JSON: array de clientes precargados en el controlador. |

Podés probarlo en el navegador o con:

```bash
curl http://localhost:8080/ping
curl http://localhost:8080/clientes
```

## Resumen de comandos

| Ubicación | Comando |
|-----------|---------|
| `demo` | `.\mvnw.cmd spring-boot:run` |
| `demo` (tests) | `.\mvnw.cmd test` |

## Solución de problemas (Parte 2)

- **El puerto 8080 está ocupado:** configurá otro puerto en `src/main/resources/application.properties`, por ejemplo `server.port=8081`, y probá las mismas rutas en ese puerto.
- **`mvnw` no ejecuta:** verificá que estés en la carpeta `demo` y que `mvnw.cmd` exista; en PowerShell puede hacer falta `.\` delante del script.
- **404 en `/clientes`:** revisá que la app esté levantada y que la URL sea exactamente `/clientes` (minúsculas, como en el controlador).


