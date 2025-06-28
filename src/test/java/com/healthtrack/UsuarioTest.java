package com.healthtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    @Test
    public void testActualizarPeso() {
        Usuario usuario = new Usuario("Helena", 65.6);
        usuario.actualizarPeso(56.5);
        assertEquals(56.5, usuario.getPeso(), 0.001);
    }
}