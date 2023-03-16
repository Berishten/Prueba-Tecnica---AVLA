# Getting Started

### Iniciar proyecto spring boot desde consola
1. Entrar a la carpeta del proyecto
2. Ejecutar el comando `gradle bootRun`
3. Para acceder a la documentación de la API abra en el navegador en la url `http://localhost:8080/swagger-ui/index.html#/`

### Iniciar proyecto spring boot dockerizado
1. Entrar a la carpeta del proyecto
2. Compile el proyecto `gradle build`
3. Construir la imagen docker `docker build -t reto-tecnico .`
4. Ejecutar el contenedor `docker run -p 8080:8080 reto-tecnico`
5. Para acceder a la documentación de la API abra en el navegador en la url `http://localhost:8080/swagger-ui/index.html#/`