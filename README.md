# Sistema de Cálculo de Matrículas Universitarias
**Proyecto de Programación - Módulo 1**

## 📋 Descripción del Proyecto

Este proyecto implementa un sistema de gestión y cálculo de matrículas para una universidad que maneja dos tipos de estudiantes:
- **Estudiantes OnCampus (Presenciales)**: Con tarifas diferenciadas según residencia
- **Estudiantes Online**: Con tarifa única por crédito y fee tecnológico opcional

El sistema lee información de estudiantes desde un archivo de texto, calcula sus matrículas según las reglas establecidas, ordena los resultados por ID de estudiante, y genera un reporte con los totales.

## 🎯 Objetivos de Aprendizaje

- Implementación de **herencia** y **polimorfismo** en Java
- Manejo de **clases abstractas** e **interfaces**
- Procesamiento de archivos (lectura y escritura)
- Implementación de algoritmos de ordenamiento
- Aplicación del patrón de diseño orientado a objetos

## 📁 Estructura del Proyecto

```
Modulo1_Octubre/
├── src/                           # Código fuente
│   ├── Main.java                  # Clase principal - punto de entrada
│   ├── Student.java               # Clase abstracta base
│   ├── OnCampusStudent.java       # Estudiante presencial (hereda de Student)
│   ├── OnlineStudent.java         # Estudiante online (hereda de Student)
│   ├── Sorter.java                # Clase de utilidad para ordenamiento
│   └── TuitionConstants.java      # Constantes para cálculo de matrículas
├── p02-students.txt               # Archivo de entrada con datos de estudiantes
├── p02-tuition.txt                # Archivo de salida generado (después de ejecutar)
├── .gitignore                     # Archivos ignorados por Git
└── README.md                      # Este archivo
```

## 🏗️ Arquitectura del Sistema

### Diagrama de Clases

```
                    ┌──────────────────────────┐
                    │   <<interface>>          │
                    │   Comparable<Student>    │
                    └──────────────────────────┘
                                △
                                │ implements
                    ┌──────────────────────────┐
                    │   <<abstract>>           │
                    │      Student             │
                    ├──────────────────────────┤
                    │ - mId: String            │
                    │ - mFirstName: String     │
                    │ - mLastName: String      │
                    │ - mCredits: int          │
                    │ - mTuition: double       │
                    ├──────────────────────────┤
                    │ + calcTuition(): void    │
                    │ + compareTo(): int       │
                    │ + getters/setters        │
                    └──────────────────────────┘
                                △
                    ┌───────────┴───────────┐
                    │                       │
        ┌──────────────────┐   ┌──────────────────┐
        │  OnCampusStudent  │   │  OnlineStudent   │
        ├──────────────────┤   ├──────────────────┤
        │ - mResidency: int │   │ - mTechFee: bool │
        │ - mProgramFee:    │   ├──────────────────┤
        │   double          │   │ + calcTuition()  │
        ├──────────────────┤   └──────────────────┘
        │ + calcTuition()   │
        └──────────────────┘
```

### Descripción de Clases

#### `Student` (Abstracta)
- **Propósito**: Clase base que define la estructura común para todos los estudiantes
- **Características**:
    - Implementa `Comparable<Student>` para permitir ordenamiento
    - Define método abstracto `calcTuition()` para cálculo polimórfico
    - Almacena información básica: ID, nombre, apellido, créditos, matrícula

#### `OnCampusStudent`
- **Propósito**: Representa estudiantes presenciales
- **Cálculo de matrícula**:
    - Base: $7,575 (residente) o $14,875 (no residente)
    - Si excede 18 créditos: $475 por crédito adicional
    - Más: Program Fee específico del programa

#### `OnlineStudent`
- **Propósito**: Representa estudiantes en línea
- **Cálculo de matrícula**:
    - $950 por crédito
    - $75 de fee tecnológico (opcional)

#### `Sorter`
- **Propósito**: Implementa algoritmo de insertion sort
- **Características**:
    - Método estático para ordenamiento
    - Soporta orden ascendente y descendente

#### `Main`
- **Propósito**: Controlador principal del programa
- **Flujo**:
    1. Lee archivo de estudiantes
    2. Calcula matrículas (polimórficamente)
    3. Ordena por ID de estudiante
    4. Escribe resultados a archivo

## 📊 Formato de Archivos

### Archivo de Entrada (`p02-students.txt`)

#### Formato para Estudiantes OnCampus:
```
C <ID> <Apellido> <Nombre> <R|N> <ProgramFee> <Créditos>
```
- `C`: Indicador de estudiante OnCampus
- `R|N`: R=Residente, N=No residente
- `ProgramFee`: Tarifa del programa (sin decimales)
- `Créditos`: Número de créditos inscritos

#### Formato para Estudiantes Online:
```
O <ID> <Apellido> <Nombre> <T|F> <Créditos>
```
- `O`: Indicador de estudiante Online
- `T|F`: T=Con fee tecnológico, F=Sin fee tecnológico

#### Ejemplo de Archivo:
```
C 8230123345450 Flintstone Fred R 750 12
C 3873472785863 Simpson Lisa R 500 18
O 2873472978693 Szyslak Moe F 24
C 4834324308675 Flintstone Wilma N 450 6
O 1384349045225 Szyslak Barney T 30
```

### Archivo de Salida (`p02-tuition.txt`)

Formato de salida con columnas alineadas:
```
<ID>             <Apellido>           <Nombre>         <Matrícula>
```

## 🚀 Instrucciones de Ejecución

### Prerrequisitos
- Java JDK 8 o superior
- IntelliJ IDEA (recomendado) o cualquier IDE Java

### Compilación y Ejecución

#### Opción 1: IntelliJ IDEA
1. Abrir el proyecto en IntelliJ
2. Marcar carpeta `src` como Source Root
3. Click derecho en `Main.java` → Run 'Main.main()'

#### Opción 2: Línea de Comandos
```bash
# Compilar
cd src
javac *.java

# Ejecutar
java Main
```

## 🧮 Ejemplos de Cálculo

### Estudiante OnCampus Residente
- Fred Flintstone: 12 créditos, Residente, $750 program fee
- Cálculo: $7,575 (base) + $750 (fee) = **$8,325**

### Estudiante OnCampus No Residente
- Wilma Flintstone: 6 créditos, No residente, $450 program fee
- Cálculo: $14,875 (base) + $450 (fee) = **$15,325**

### Estudiante OnCampus con Créditos Adicionales
- Betty Rubble: 21 créditos, Residente, $450 program fee
- Cálculo: $7,575 (base) + (3 × $475) (créditos extra) + $450 (fee) = **$9,450**

### Estudiante Online
- Barney Szyslak: 30 créditos, Con tech fee
- Cálculo: (30 × $950) + $75 = **$28,575**

## 📝 Conceptos Clave Implementados

### Polimorfismo
El método `calcTuition()` es polimórfico: cada subclase implementa su propia lógica de cálculo, pero se invoca de manera uniforme desde `Main`:

```java
for (Student student : pStudentList) {
    student.calcTuition(); // Llamada polimórfica
}
```

### Herencia
`OnCampusStudent` y `OnlineStudent` heredan de `Student`, reutilizando código común y especializando comportamiento.

### Abstracción
`Student` es abstracta, no se pueden crear instancias directas, solo a través de sus subclases concretas.

### Encapsulación
Todos los atributos son privados con acceso controlado mediante getters y setters.

## 🧪 Testing

Para verificar que el programa funciona correctamente:

1. Ejecutar con el archivo de prueba proporcionado
2. Verificar que `p02-tuition.txt` se genera
3. Confirmar que los estudiantes están ordenados por ID
4. Validar cálculos manualmente para algunos casos

## 📈 Rúbrica de Evaluación

| Criterio | Puntos | Descripción |
|----------|--------|-------------|
| **Calidad de la solución** | 36 pts | Programa funcional que cumple requisitos |
| **Pruebas y verificación** | 30 pts | Verificación con archivos de prueba |
| **Documentación** | 21 pts | Código comentado y documentación clara |
| **Sintaxis y orden** | 9 pts | Código limpio siguiendo convenciones |
| **Entrega a tiempo** | 9 pts | Cumplimiento de fecha límite |
| **TOTAL** | 105 pts | |

## 🐛 Solución de Problemas Comunes

### Error: "Could not open 'p02-students.txt'"
- Verificar que el archivo existe en la raíz del proyecto
- Confirmar que el contenido tiene el formato correcto

### Error: InputMismatchException
- Asegurarse de no usar decimales en los números del archivo
- Verificar que no hay espacios extra o caracteres invisibles

### Archivo de salida vacío
- Confirmar que se está cerrando el PrintWriter con `out.close()`
- Verificar que el ArrayList tiene datos antes de escribir

## 👥 Información del Proyecto

- **Curso**: Programación CSE205
- **Módulo**: 1
- **Peso**: 20% de la nota final
- **Fecha de entrega**: Martes 21 de octubre, 23:59

## 📞 Contacto y Soporte

- Para dudas sobre el proyecto consultar a :
- Andrés Perot a.perotquevedo@uandresbello.edu
- Rodrigo Yañez r.yaezsepulveda@uandresbello.edu
- Lorenzo Chacano l.chacanomuoz@uandresbello.edu
- Natalia San Miguel n.sanmiguelcornejo@uandresbello.edu
- Sabina Romero s.romerorodriguez1@uandresbello.cl

---

*Proyecto desarrollado como parte del curso de Programación Orientada a Objetos*