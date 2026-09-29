# Matriz de Casos de Prueba - Ae6

| ID | Regla / Método | Escenario | Entrada | Esperado | Tipo | Riesgo Protegido |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **CP-01** | `puedeCancelar` | Cancelación normal con tiempo holgado | Anticipación = 5 horas | `true` | Normal | Permitir la cancelación cuando se cumple el tiempo holgado |
| **CP-02** | `puedeCancelar` | Límite exacto permitido | Anticipación = 2 horas | `true` | Límite | Evitar errores de rechazo en la frontera exacta de tiempo |
| **CP-03** | `puedeCancelar` | Límite inválido (fuera de regla) | Anticipación = 1 hora | `false` | Límite | Evitar cancelaciones indebidas fuera del plazo mínimo |
| **CP-04** | `calcularTotal` | Descuento cliente VIP | Tipo = `"VIP"`, Monto = 100.0 | `85.0` | Alternativo | Cobro incorrecto de tarifa preferencial VIP |
| **CP-05** | `calcularTotal` | Descuento cliente ESTUDIANTE | Tipo = `"ESTUDIANTE"`, Monto = 100.0 | `90.0` | Alternativo | Omisión del beneficio de tarifa estudiantil |
| **CP-06** | `calcularTotal` | Cliente NORMAL | Tipo = `"NORMAL"`, Monto = 100.0 | `100.0` | Normal | Alteración no deseada de la tarifa estándar |
| **CP-07** | `calcularTotal` | Valor de reserva negativo | Tipo = `"NORMAL"`, Monto = -1.0 | `IllegalArgumentException` | Inválido | Procesamiento de montos financieros inconsistentes/negativos |
| **CP-08** | `confirmar` | Reserva con disponibilidad | Reserva R-001, Disponible = `true` | `EstadoReserva.CONFIRMADA` | Normal | Falla al transicionar y guardar el estado confirmado |
| **CP-09** | `confirmar` | Reserva sin disponibilidad | Reserva R-002, Disponible = `false` | `IllegalStateException` | Alternativo | Confirmación indebida sin cupo disponible (sobreventa) |
| **CP-10** | `confirmar` | Objeto de reserva nulo | Reserva = `null` | `IllegalArgumentException` | Inválido | Caídas del sistema por `NullPointerException` sin controlar |