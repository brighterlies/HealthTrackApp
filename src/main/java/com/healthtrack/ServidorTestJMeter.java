package com.healthtrack;

import static spark.Spark.*;

public class ServidorTestJMeter {

    public static void main(String[] args) {
        // Configura el puerto
        port(8080);

        // Endpoint simple para verificar si el servidor está activo
        get("/", (req, res) -> "Servidor activo en /api/peso");

        // Endpoint POST para recibir nombre y peso, y responder con un mensaje
        post("/api/peso", (req, res) -> {
            String nombre = req.queryParams("nombre");
            String peso = req.queryParams("peso");

            if (nombre == null || peso == null) {
                res.status(400);
                return "Faltan parámetros: nombre y peso son requeridos";
            }

            return "El peso de " + nombre + " es " + peso + " kg";
        });

        // Espera a que Spark arranque
        awaitInitialization();

        System.out.println("🚀 Servidor Spark iniciado en http://localhost:8080");
        System.out.println("✅ Puedes probar POST a http://localhost:8080/api/peso con parámetros 'nombre' y 'peso'");
    }
}
