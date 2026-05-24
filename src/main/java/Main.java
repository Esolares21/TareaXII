import java.util.List;

public class Main {

    public static void main(String[] args) {

        String archivo = "clientes.csv";
        int cantidadClientes = 2_000_000;

        System.out.println("======================================");
        System.out.println("🚀 LABORATORIO: PROCESAMIENTO MASIVO");
        System.out.println("👨‍💻 ESTUDIANTE: Erix Alejandro Solares Flores");
        System.out.println("🆔 CARNET: 9941-20-23978");
        System.out.println("======================================");

        System.out.println("\n⚙️ Generando archivo...");
        long inicioGeneracion = System.currentTimeMillis();

        GeneradorClientes.generarArchivo(archivo, cantidadClientes);

        long finGeneracion = System.currentTimeMillis();
        System.out.println("⏱️ Tiempo de generación: " + (finGeneracion - inicioGeneracion) + " ms");

        mostrarMemoria();

        try {
            System.out.println("\n⚠️ Cargando TODOS los clientes en memoria (Versión Ineficiente)...");
            long inicioCarga = System.currentTimeMillis();

            List<Cliente> clientes = ProcesadorMalo.cargarTodosLosClientes(archivo);

            long finCarga = System.currentTimeMillis();
            System.out.println("✅ Clientes cargados en memoria: " + clientes.size());
            System.out.println("⏱️ Tiempo de carga: " + (finCarga - inicioCarga) + " ms");

            System.out.println("\n🔄 Procesando con estructura INEFICIENTE...");
            ProcesadorIneficiente.procesar(clientes);

        } catch (OutOfMemoryError e) {
            System.out.println("\n❌ ¡ERROR CRÍTICO EVITADO!: La versión ineficiente se quedó sin memoria RAM (OutOfMemoryError).");
            System.out.println("💡 Explicación: Cargar 2,000,000 de registros pesados en un ArrayList superó el límite del Heap.");
        }


        System.out.println("\n♻️ Solicitando recolección de basura");
        System.gc();
        try { Thread.sleep(2000); } catch (InterruptedException e) { }
        mostrarMemoria();

        System.out.println("\n🔥 Cambiando a la VERSIÓN OPTIMIZADA...");
        ProcesadorOptimizado.procesarDesdeArchivo(archivo);

        mostrarMemoria();

        System.out.println("\n🏁 Fin del programa.");
    }

    private static void mostrarMemoria() {
        Runtime runtime = Runtime.getRuntime();

        long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
        long memoriaTotal = runtime.totalMemory();
        long memoriaMaxima = runtime.maxMemory();

        System.out.println("\n📊 Memoria JVM:");
        System.out.println("   Usada : " + (memoriaUsada / 1024 / 1024) + " MB");
        System.out.println("   Total : " + (memoriaTotal / 1024 / 1024) + " MB");
        System.out.println("   Máxima: " + (memoriaMaxima / 1024 / 1024) + " MB");
    }
}