# Semana 7 - Suite de Pruebas Unitarias y Cobertura (Ae6)

**Materia:** Diseño de Software  
**Proyecto:** `semana7-proyecto-base-ae6`  
**Autor:** Isac Sanchez

---

## 1. Objetivo del Proyecto
El propósito de este proyecto es implementar y validar una suite completa de pruebas unitarias para la clase `ReservaService`, asegurando la verificación de reglas de negocio para la cancelación, cálculo de totales con descuento y confirmación de reservas. Además, se integran dobles de prueba (Mocks con Mockito) para aislar dependencias externas y se mide la cobertura del código mediante JaCoCo.

---

## 2. Requisitos de Ejecución
* **Java JDK:** Versión 21
* **Apache Maven:** Versión 3.8 o superior
* **Git:** Para el control de versiones

---

## 3. Resumen de Casos de Prueba Incluidos (CP-01 a CP-10)

| Caso de Prueba | Descripción / Regla de Negocio Evaluada |
| :--- | :--- |
| **CP-01** | Permitir cancelación de reserva con anticipación de 5 horas. |
| **CP-02** | Permitir cancelación de reserva en el límite de anticipación (2 horas). |
| **CP-03** | Denegar cancelación de reserva si la anticipación es menor a 2 horas (1 hora). |
| **CP-04** | Aplicar un 15% de descuento al calcular el total para un cliente tipo **VIP**. |
| **CP-05** | Aplicar un 10% de descuento al calcular el total para un cliente tipo **ESTUDIANTE**. |
| **CP-06** | Calcular total sin descuento (0%) para un cliente tipo **NORMAL**. |
| **CP-07** | Lanzar `IllegalArgumentException` cuando el monto base ingresado es negativo. |
| **CP-08** | Confirmar reserva exitosamente cuando hay disponibilidad (verifica estado, persistencia y notificación vía Mocks). |
| **CP-09** | Lanzar `IllegalStateException` al intentar confirmar sin disponibilidad (verifica que no se guarde ni notifique). |
| **CP-10** | Lanzar `IllegalArgumentException` si el objeto de reserva proporcionado es nulo. |

---

## 4. Instrucciones de Ejecución de Pruebas

Para limpiar la compilación y ejecutar la suite completa de JUnit 5 en la terminal:

```bash
mvn clean test