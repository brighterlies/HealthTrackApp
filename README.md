# 🩺 HealthTrackApp - Evaluación DevOps Módulo 4

Este repositorio contiene la implementación de pruebas automatizadas y pipeline CI/CD para la plataforma **HealthTrack**, la cual permite a usuarios registrar y actualizar su peso. El objetivo es garantizar la calidad y estabilidad del sistema mediante pruebas unitarias, funcionales y de rendimiento.

---

## 📦 Contenido del proyecto

- `src/main/java/` → Código fuente principal (clase Usuario)
- `src/test/java/` → Pruebas automatizadas con JUnit y Selenium
- `healthtrackapp-test.jmx` → Escenario de pruebas de carga con JMeter
- `.github/workflows/usuario-test-workflow.yml` → Pipeline CI/CD con GitHub Actions
- `reporte_html/` → Reporte HTML de JMeter
- `target/` → Salida de compilación y pruebas (ignorada por `.gitignore`)

---

## 🧪 Pruebas implementadas

| Tipo de prueba     | Herramienta       | Descripción                              |
|--------------------|-------------------|------------------------------------------|
| Pruebas unitarias  | JUnit 5           | Verifica el comportamiento de `actualizarPeso()` |
| Pruebas funcionales| Selenium + Chrome | Simula flujos de usuario en navegador    |
| Pruebas de carga   | Apache JMeter     | Evalúa el rendimiento del endpoint       |
| Cobertura          | JaCoCo            | Calcula cobertura de pruebas             |
| Calidad de código  | SonarQube         | Análisis estático y detección de code smells |

---

## 🚀 Cómo ejecutar las pruebas

### Requisitos:
- Java 17
- Maven
- Google Chrome instalado
- Docker (opcional para SonarQube)

### 1. Ejecutar pruebas unitarias y funcionales
`mvn clean test`

### 2. Ejecutar pruebas de carga
`jmeter -n -t healthtrackapp-test.jmx -l resultados.jtl`
`jmeter -g resultados.jtl -o reporte_html`

### 3. Analizar con SonarQube
`mvn clean verify -DskipTests=true sonar:sonar -Dsonar.projectKey=HealthTrackApp -Dsonar.host.url=http://localhost:9000 -Dsonar.login=TU_TOKEN`