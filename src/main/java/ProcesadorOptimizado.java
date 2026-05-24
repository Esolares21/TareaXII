import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcesadorOptimizado {

    public static void procesarDesdeArchivo(String rutaArchivo) {
        Map<String, List<Cliente>> campantas = new HashMap<>();
        long inicio = System.currentTimeMillis();

        System.out.println("\n[OPTIMIZADO] Procesando archivo línea por línea con HashMap...");

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 8);

                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                double ingreso = Double.parseDouble(partes[2]);
                String segmento = partes[3];
                String region = partes[4];
                int score = Integer.parseInt(partes[5]);
                double deuda = Double.parseDouble(partes[6]);

                String jsonData = "";

                Cliente cliente = new Cliente(id, nombre, ingreso, segmento, region, score, deuda, jsonData);
                String tipo = determinarCampania(cliente);

                campantas.putIfAbsent(tipo, new ArrayList<>());
                campantas.get(tipo).add(cliente);
            }

        } catch (IOException e) {
            System.out.println("Error en el procesamiento optimizado: " + e.getMessage());
        }

        long fin = System.currentTimeMillis();

        System.out.println("\n[OPTIMIZADO] Resumen de campañas generadas:");
        System.out.println("Total de campañas diferentes: " + campantas.size());

        for (Map.Entry<String, List<Cliente>> entry : campantas.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size());
        }

        System.out.println("\nTiempo de procesamiento optimizado completo: " + (fin - inicio) + " ms");
    }

    private static String determinarCampania(Cliente cliente) {
        String nivelIngreso;
        if (cliente.getIngreso() >= 25000) {
            nivelIngreso = "INGRESO_ALTO";
        } else if (cliente.getIngreso() >= 15000) {
            nivelIngreso = "INGRESO_MEDIO";
        } else if (cliente.getIngreso() >= 10000) {
            nivelIngreso = "INGRESO_BAJO";
        } else {
            nivelIngreso = "NO_APLICA";
        }

        String nivelScore;
        if (cliente.getScore() >= 800) {
            nivelScore = "SCORE_EXCELENTE";
        } else if (cliente.getScore() >= 600) {
            nivelScore = "SCORE_BUENO";
        } else if (cliente.getScore() >= 400) {
            nivelScore = "SCORE_REGULAR";
        } else {
            nivelScore = "SCORE_RIESGO";
        }

        String nivelDeuda;
        if (cliente.getDeuda() >= 7000) {
            nivelDeuda = "DEUDA_ALTA";
        } else if (cliente.getDeuda() >= 3000) {
            nivelDeuda = "DEUDA_MEDIA";
        } else {
            nivelDeuda = "DEUDA_BAJA";
        }

        return cliente.getSegmento()
                + "_"
                + cliente.getRegion()
                + "_"
                + nivelIngreso
                + "_"
                + nivelScore
                + "_"
                + nivelDeuda;
    }
}