# Compilador Hecho a Mano en Java

Este proyecto es una implementación de un compilador hecho a mano en Java, desarrollado para la materia "Diseño de Compiladores" en la Facultad de Ciencias Exactas, UNICEN. El objetivo del proyecto es diseñar y construir un compilador desde cero que pueda tomar un código fuente en un lenguaje específico y traducirlo a código de máquina o a otro lenguaje intermedio.

## Contenido del Proyecto

- **`src/`**: Código fuente del compilador en Java.
- **`docs/`**: Documentación del proyecto.
- **`test/`**: Casos de prueba y scripts para verificar el funcionamiento del compilador.
- **`README.md`**: Este archivo, que proporciona una descripción del proyecto.

## Requisitos

- Java Development Kit (JDK) 11 o superior.
- Herramientas de construcción como Apache Maven o Gradle (opcional, según cómo se gestione el proyecto).

## Instalación

1. Clona el repositorio en tu máquina local:

   `git clone https://github.com/eliseovillaok/handmadecompiler.git`

2. Navega al directorio del proyecto:

   `cd handmadecompiler`

3. Compila el proyecto:

   `javac -d bin src/**/*.java`

4. Ejecuta el compilador (asegúrate de ajustar el nombre del archivo principal según el código fuente):

   `java -cp bin com.ejemplo.Compilador`

## Uso

Para utilizar el compilador, proporciona un archivo de código fuente en el lenguaje específico soportado por el compilador. El compilador generará un archivo de salida con el código traducido.

### Ejemplo de Comando

   `java -cp bin com.ejemplo.Compilador archivo_fuente.txt -o archivo_salida.txt`

## Documentación

La documentación del proyecto se encuentra en el directorio `docs/` y contiene detalles sobre el diseño del compilador, la gramática del lenguaje, y la implementación de los diferentes componentes del compilador.

## Contribuciones

Las contribuciones al proyecto son bienvenidas. Si deseas contribuir, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una rama para tus cambios (`git checkout -b feature/nueva-caracteristica`).
3. Realiza tus cambios y haz un commit (`git commit -am 'Añadir nueva característica'`).
4. Haz un push a la rama (`git push origin feature/nueva-caracteristica`).
5. Crea un pull request desde la rama de tu fork al repositorio original.

## Licencia

Este proyecto está licenciado bajo los términos de la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Contacto

Para más información, puedes contactarnos en cualquiera de los emails siguientes:
Eliseo: [eliseovilla10@gmail.com](mailto:eliseovilla10@gmail.com)
Valentin: [dronesdetandil@gmail.com](mailto:dronesdetandil@gmail.com)
Agustin: [agustinpereh03@gmail.com](mailto:agustinpereh03@gmail.com)


---

Desarrollado por Eliseo Villa, Valentin Pardo y Agustin Perez para la materia "Diseño de Compiladores 1", Facultad de Ciencias Exactas, UNICEN.

