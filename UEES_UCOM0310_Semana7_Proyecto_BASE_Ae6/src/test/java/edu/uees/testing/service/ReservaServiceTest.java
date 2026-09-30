package edu.uees.testing.service;

import edu.uees.testing.availability.DisponibilidadClient;
import edu.uees.testing.domain.EstadoReserva;
import edu.uees.testing.domain.Reserva;
import edu.uees.testing.notification.Notificador;
import edu.uees.testing.repository.ReservaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private DisponibilidadClient disponibilidad;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private Notificador notificador;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reservaValida;

    @BeforeEach
    void setUp() {
        reservaValida = new Reserva("R-001", "CLI-001");
    }

    // ==========================================
    // CANCELACIÓN DE RESERVAS (CP-01 a CP-03)
    // ==========================================

    @Test
    @DisplayName("CP-01: Permitir cancelación con anticipación normal (5 horas)")
    void cp01_cincoHorasPermitenCancelar() {
        boolean resultado = reservaService.puedeCancelar(5);
        assertTrue(resultado, "Debe permitir cancelar si faltan 5 horas");
    }

    @Test
    @DisplayName("CP-02: Permitir cancelación en el límite permitido (2 horas)")
    void cp02_dosHorasPermitenCancelar() {
        boolean resultado = reservaService.puedeCancelar(2);
        assertTrue(resultado, "Debe permitir cancelar exactamente con 2 horas de anticipación");
    }

    @Test
    @DisplayName("CP-03: Denegar cancelación por debajo del límite (1 hora)")
    void cp03_unaHoraNoPermiteCancelar() {
        boolean resultado = reservaService.puedeCancelar(1);
        assertFalse(resultado, "No debe permitir cancelar si falta menos de 2 horas");
    }

    // ==========================================
    // CÁLCULO DE TOTALES Y DESCUENTOS (CP-04 a CP-07)
    // ==========================================

    @Test
    @DisplayName("CP-04: Aplicar descuento VIP del 15% al calcular el total")
    void cp04_calcularTotalDescuentoVip() {
        double total = reservaService.calcularTotal("VIP", 100.0);
        assertEquals(85.0, total, 0.001, "El cliente VIP debe recibir un 15% de descuento");
    }

    @Test
    @DisplayName("CP-05: Aplicar descuento ESTUDIANTE al calcular el total")
    void cp05_calcularTotalDescuentoEstudiante() {
        double total = reservaService.calcularTotal("ESTUDIANTE", 100.0);
        assertEquals(90.0, total, 0.001, "El cliente ESTUDIANTE debe recibir un 10% de descuento");
    }

    @Test
    @DisplayName("CP-06: Calcular total para cliente NORMAL sin descuento")
    void cp06_calcularTotalClienteNormal() {
        double total = reservaService.calcularTotal("NORMAL", 100.0);
        assertEquals(100.0, total, 0.001, "El cliente NORMAL no debe tener ningún descuento");
    }

    @Test
    @DisplayName("CP-07: Lanzar IllegalArgumentException cuando el monto base es negativo")
    void cp07_calcularTotalMontoNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            reservaService.calcularTotal("NORMAL", -1.0);
        }, "Un monto negativo debe lanzar IllegalArgumentException");
    }

    // ==========================================
    // CONFIRMACIÓN DE RESERVA CON MOCKS (CP-08 a CP-10)
    // ==========================================

    @Test
    @DisplayName("CP-08: Confirmar reserva exitosamente cuando hay disponibilidad")
    void cp08_confirmarReservaDisponibleSeConfirma() {
        when(disponibilidad.estaDisponible(any())).thenReturn(true);

        reservaService.confirmar(reservaValida);

        assertEquals(EstadoReserva.CONFIRMADA, reservaValida.getEstado());
        verify(reservaRepository, times(1)).guardar(reservaValida);
        verify(notificador, times(1)).enviarConfirmacion(reservaValida);
    }

    @Test
    @DisplayName("CP-09: Lanzar IllegalStateException cuando no hay disponibilidad")
    void cp09_confirmarSinDisponibilidadLanzaExcepcion() {
        when(disponibilidad.estaDisponible(any())).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> {
            reservaService.confirmar(reservaValida);
        });

        verify(reservaRepository, never()).guardar(any());
        verify(notificador, never()).enviarConfirmacion(any());
    }

    @Test
    @DisplayName("CP-10: Lanzar IllegalArgumentException cuando la reserva es nula")
    void cp10_confirmarReservaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            reservaService.confirmar(null);
        }, "Debe lanzar excepción si el objeto reserva es nulo");
    }
}