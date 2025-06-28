package com.healthtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    // Test que verifica el correcto funcionamiento del método actualizarPeso de la clase Usuario
    @Test
    public void testActualizarPeso() {
        // Crea un usuario con nombre "Helena" y peso inicial 65.6
        Usuario usuario = new Usuario("Helena", 65.6);

        // Actualiza el peso del usuario a 56.5
        usuario.actualizarPeso(56.5);

        // Verifica que el peso del usuario haya cambiado correctamente a 56.5
        // El tercer parámetro (0.001) es el delta para comparación de números decimales
        assertEquals(56.5, usuario.getPeso(), 0.001);
    }
}