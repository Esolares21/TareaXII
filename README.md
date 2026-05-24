# 📊 Tarea XII - Mejora de Código (Procesamiento Masivo)

**👨‍💻 Estudiante:** Erix Alejandro Solares Flores  
**🆔 Carnet:** 9941-20-23978  
**🏫 Curso:** Programación III  

---

## 📝 Descripción del Problema
[cite_start]El programa original intenta procesar un volumen masivo de datos (**2,000,000 de registros de clientes**) desde un archivo CSV[cite: 13]. [cite_start]Sin embargo, el diseño base presenta fallas críticas de rendimiento y arquitectura[cite: 16]:
1. [cite_start]Almacenar absolutamente todos los objetos con sus cadenas JSON simuladas en un `ArrayList` al mismo tiempo[cite: 17].
2. [cite_start]Realizar búsquedas lineales iterativas (`for` anidados) sobre listas para agrupar las campañas.

---

## 🔍 Análisis Técnico del Código Base (Versión Ineficiente)

### 1. ¿Por qué cargar todos los clientes en memoria es un problema? 🛑
[cite_start]Al utilizar una lista tradicional para guardar los 2 millones de objetos, forzamos a la Máquina Virtual de Java (JVM) a retener todo en el Heap Memory simultáneamente[cite: 17]. [cite_start]Como cada objeto contiene datos JSON simulados extremadamente extensos, el espacio se agota por completo lanzando un error fatal de **`java.lang.OutOfMemoryError: Java heap space`**, deteniendo de golpe la ejecución del sistema[cite: 40].

### 2. ¿Por qué usar listas con búsqueda lineal no es eficiente? ⏳
[cite_start]La versión ineficiente evalúa los clientes uno por uno, y por cada registro realiza una búsqueda secuencial recorriendo toda la lista de campañas creadas hasta encontrar una coincidencia[cite: 18, 41]. [cite_start]Esto genera una complejidad temporal insostenible en volúmenes masivos, lo que incrementa el tiempo de procesamiento exponencialmente a medida que la lista crece[cite: 41].

### 3. ¿Cómo afecta el tamaño del JSON al consumo de memoria? 📦
[cite_start]El atributo `jsonData` actúa como un simulador de campo pesado de base de datos (como un CLOB). [cite_start]Al concatenar múltiples caracteres aleatorios por cada fila, el peso en bytes de una sola instancia de `Cliente` se eleva significativamente. [cite_start]Multiplicado por los 2 millones de registros, satura la memoria disponible antes de que los algoritmos de agrupación puedan siquiera ejecutarse.

---

## ⚡ Explicación de la Versión Optimizada

### 🛠️ Estructura de Datos Seleccionada
[cite_start]Para resolver de raíz el problema de almacenamiento y las búsquedas ineficientes, implementamos la estructura de un **`HashMap<String, List<Cliente>>`** junto con un procesamiento por flujo de datos secuencial[cite: 48, 52].

### 💡 Justificación del Uso de HashMap y Criterio de Memoria
* [cite_start]**Procesamiento por flujo de lectura:** En lugar de intentar subir todo el archivo CSV a la memoria RAM de un solo golpe, abrimos un flujo con `BufferedReader` para procesar los datos línea por línea.
* [cite_start]**Omitir Atributos Irrelevantes (Criterio de Memoria):** Aplicando un diseño inteligente, identificamos que el campo `jsonData` no aporta valor a la lógica de negocio para segmentar las campañas. [cite_start]Por lo tanto, el procesador optimizado instancia los clientes pasando este parámetro vacío `""`. [cite_start]Esto disminuye drásticamente el peso de la lista final en el mapa y evita colapsos por falta de memoria.
* [cite_start]**Búsqueda Eficiente:** El uso del `HashMap` nos permite validar y agrupar las campañas mediante una clave única con una complejidad promedio constante de **$O(1)$**[cite: 47]. [cite_start]Con los métodos de acceso directo eliminamos por completo los bucles anidados de búsqueda lineal, procesando los millones de datos de forma inmediata[cite: 47].
