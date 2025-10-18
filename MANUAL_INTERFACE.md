# Manual de Usuario - Sistema de Cálculo de Matrículas Universitarias
## Interfaz Gráfica de Usuario (GUI)

---

## 📋 Índice
1. [Introducción](#introducción)
2. [Requisitos del Sistema](#requisitos-del-sistema)
3. [Instalación](#instalación)
4. [Interfaz Principal](#interfaz-principal)
5. [Funcionalidades](#funcionalidades)
6. [Guía de Uso Paso a Paso](#guía-de-uso-paso-a-paso)
7. [Reportes](#reportes)
8. [Solución de Problemas](#solución-de-problemas)

---

## 1. Introducción

El Sistema de Cálculo de Matrículas Universitarias es una aplicación Java con interfaz gráfica que permite gestionar y calcular las matrículas de estudiantes universitarios de manera eficiente y visual.

### Características Principales:
- ✅ Carga de archivos de estudiantes
- ✅ Cálculo automático de matrículas
- ✅ Ordenamiento por ID
- ✅ Generación de reportes
- ✅ Interfaz intuitiva y amigable
- ✅ Estadísticas en tiempo real

---

## 2. Requisitos del Sistema

### Requisitos Mínimos:
- **Sistema Operativo**: Windows 10/11, macOS, Linux
- **Java**: JDK 8 o superior
- **Memoria RAM**: 2 GB mínimo
- **Espacio en Disco**: 50 MB
- **Resolución**: 1200x700 píxeles mínimo

---

## 3. Instalación

### Opción 1: Ejecutar desde IntelliJ IDEA
1. Abrir el proyecto en IntelliJ IDEA
2. Navegar a `src/TuitionSystemGUI.java`
3. Click derecho → Run 'TuitionSystemGUI.main()'

### Opción 2: Línea de Comandos
```bash
cd src
javac *.java
java TuitionSystemGUI
```

---

## 4. Interfaz Principal

### Vista General
![Interfaz Principal Vacía]
La interfaz se divide en 4 secciones principales:

1. **Barra de Herramientas Superior**: Botones de acción principales
2. **Tabla Central**: Visualización de datos de estudiantes
3. **Panel de Estadísticas** (derecha): Resumen de información
4. **Panel de Registro** (inferior): Log de actividades

### Componentes de la Interfaz:

#### Botones de Acción:
- 📁 **Cargar Archivo**: Carga un archivo de estudiantes (.txt)
- 🧮 **Calcular Matrículas**: Calcula las matrículas de todos los estudiantes
- 🔤 **Ordenar por ID**: Ordena la lista por ID de estudiante
- 💾 **Guardar Resultados**: Guarda los resultados en un archivo
- ➕ **Agregar Estudiante**: Abre diálogo para agregar estudiante manual
- 🗑️ **Limpiar Todo**: Elimina todos los datos cargados

#### Tabla de Datos:
Columnas disponibles:
- **ID**: Identificador único del estudiante
- **Apellido**: Apellido del estudiante
- **Nombre**: Nombre del estudiante
- **Tipo**: OnCampus u Online
- **Créditos**: Número de créditos matriculados
- **Residencia/Tech Fee**: Estado de residencia o fee tecnológico
- **Program Fee**: Tarifa del programa (solo OnCampus)
- **Matrícula**: Monto calculado

---

## 5. Funcionalidades

### 5.1 Carga de Archivos

#### Proceso de Carga:
![Diálogo de Carga de Archivo]

1. Click en **"Cargar Archivo"**
2. Navegar hasta la carpeta del proyecto
3. Seleccionar `p02-students.txt`
4. Click en **"Open"**

#### Formato del Archivo de Entrada:
```
C 8230123345450 Flintstone Fred R 750 12
O 2873472978693 Szyslak Moe F 24
```
- **C**: Estudiante OnCampus
- **O**: Estudiante Online
- **R/N**: Residente/No Residente (OnCampus)
- **T/F**: Con/Sin Tech Fee (Online)

### 5.2 Visualización de Datos

![Datos Cargados en la Tabla]

Una vez cargados los datos:
- La tabla muestra todos los estudiantes
- El contador de estadísticas se actualiza (9 estudiantes en el ejemplo)
- El registro muestra "✓ Archivo cargado exitosamente"
- Los botones de cálculo y ordenamiento se habilitan

### 5.3 Ordenamiento

![Estudiantes Ordenados por ID]

Al hacer click en **"Ordenar por ID"**:
- Los estudiantes se reorganizan en orden ascendente por ID
- El registro confirma: "✓ Lista ordenada por ID de estudiante"
- Nota cómo los IDs ahora van de menor a mayor

### 5.4 Cálculo de Matrículas

Para calcular las matrículas:
1. Click en **"Calcular Matrículas"**
2. Una barra de progreso mostrará el avance
3. La columna "Matrícula" se actualizará con los montos
4. Las estadísticas mostrarán el total acumulado

#### Reglas de Cálculo:

**OnCampus:**
- Base Residente: $7,575
- Base No Residente: $14,875
- Créditos adicionales (>18): $475 por crédito
- Más: Program Fee específico

**Online:**
- $950 por crédito
- Tech Fee opcional: $75

### 5.5 Limpieza de Datos

![Confirmación de Limpieza]

Para limpiar todos los datos:
1. Click en **"Limpiar Todo"**
2. Aparecerá un diálogo de confirmación
3. Click en **"Yes"** para confirmar
4. La tabla quedará vacía

![Datos Eliminados]

Resultado después de limpiar:
- Tabla vacía
- Estadísticas reiniciadas a 0
- Registro muestra: "✓ Todos los datos han sido limpiados"

---

## 6. Guía de Uso Paso a Paso

### Flujo de Trabajo Típico:

#### Paso 1: Iniciar la Aplicación
```bash
java TuitionSystemGUI
```

#### Paso 2: Cargar Datos
- Click en **"Cargar Archivo"**
- Seleccionar `p02-students.txt`
- Verificar que los datos aparezcan en la tabla

#### Paso 3: Calcular Matrículas
- Click en **"Calcular Matrículas"**
- Esperar a que termine el cálculo
- Verificar los montos en la última columna

#### Paso 4: Ordenar (Opcional)
- Click en **"Ordenar por ID"**
- Los datos se reorganizarán automáticamente

#### Paso 5: Guardar Resultados
- Click en **"Guardar Resultados"**
- Elegir ubicación y nombre del archivo
- El formato será compatible con el sistema principal

---

## 7. Reportes

### 7.1 Generar Reporte Detallado

![Ventana de Reporte Detallado]

El sistema genera reportes con:
- **Resumen General**: Total de estudiantes y matrícula total
- **Estadísticas OnCampus**:
    - Total de estudiantes presenciales
    - Desglose Residentes/No Residentes
    - Matrícula promedio
- **Estadísticas Online**:
    - Total de estudiantes en línea
    - Desglose Con/Sin Tech Fee
    - Matrícula promedio

### 7.2 Guardar Reporte

Desde la ventana de reporte:
1. Click en **"Guardar Reporte"**
2. Elegir ubicación
3. El reporte se guardará en formato .txt

### Ejemplo de Reporte:
```
=====================================
     REPORTE DE MATRÍCULAS
=====================================

RESUMEN GENERAL
---------------
Total de estudiantes: 9
Matrícula total: $156,925.00
Matrícula promedio: $17,436.11

ESTUDIANTES ONCAMPUS
--------------------
Total: 5 estudiantes
  - Residentes: 3
  - No residentes: 2
Matrícula total: $54,450.00
Matrícula promedio: $10,890.00

ESTUDIANTES ONLINE
------------------
Total: 4 estudiantes
  - Con tech fee: 2
  - Sin tech fee: 2
Matrícula total: $102,475.00
Matrícula promedio: $25,618.75
```

---

## 8. Solución de Problemas

### Problemas Comunes y Soluciones:

#### Problema: "No se puede cargar el archivo"
**Solución:**
- Verificar que el archivo existe
- Comprobar el formato del archivo
- Asegurarse de que no hay caracteres especiales

#### Problema: "Las matrículas muestran 0"
**Solución:**
- Click en "Calcular Matrículas"
- Verificar que los datos de entrada son correctos

#### Problema: "La tabla no se actualiza"
**Solución:**
- Cerrar y volver a abrir la aplicación
- Verificar que Java está actualizado

#### Problema: "Error al guardar resultados"
**Solución:**
- Verificar permisos de escritura en la carpeta
- Asegurarse de que el archivo no esté abierto en otro programa

---

## 📊 Características Adicionales

### Edición en Tabla
- Los campos de **Créditos** y **Program Fee** son editables
- Hacer doble click en la celda para editar
- La matrícula se recalcula automáticamente

### Agregar Estudiante Manual
El botón **"Agregar Estudiante"** abre un diálogo donde puedes:
- Seleccionar tipo (OnCampus/Online)
- Ingresar datos personales
- Configurar opciones específicas del tipo
- El estudiante se agregará a la lista actual

### Registro de Actividad
El panel inferior mantiene un historial de todas las acciones:
- Archivos cargados
- Cálculos realizados
- Errores encontrados
- Guardados exitosos

---

## 🎯 Tips y Mejores Prácticas

1. **Siempre calcular antes de guardar**: Asegúrate de calcular las matrículas antes de guardar
2. **Verificar el ordenamiento**: Si necesitas un orden específico, ordena antes de guardar
3. **Hacer respaldos**: Guarda copias de tus archivos originales
4. **Revisar el registro**: El panel de registro te ayudará a identificar problemas
5. **Usar el reporte**: Genera reportes para análisis detallados

---

## 📞 Soporte

Para problemas técnicos o consultas, contactar a los desarrolladores:
- Andrés Perot (a.perotquevedo@uandresbello.edu)
- Rodrigo Yañez (r.yaezsepulveda@uandresbello.edu)
- Lorenzo Chacano (l.chacanomuoz@uandresbello.edu)
- Natalia San Miguel (n.sanmiguelcornejo@uandresbello.edu)
- Sabina Romero (s.romerorodriguez1@uandresbello.cl)

---

## 📝 Notas de Versión

### Versión 1.0 (2024)
- ✅ Interfaz gráfica completa
- ✅ Carga y procesamiento de archivos
- ✅ Cálculo automático de matrículas
- ✅ Generación de reportes
- ✅ Edición en línea
- ✅ Estadísticas en tiempo real

---

*Sistema desarrollado para el curso CSE205 - Programación Orientada a Objetos*