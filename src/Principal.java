public class Principal {
    public static void main(String[] args) {
        try {
            ConsultaMoneda conversor = new ConsultaMoneda("USD");
            double cantidad = 100;
            String monedaOrigen = "USD";
            String monedaDestino = "EUR";
            double cantidadConvertida = conversor.convertir(monedaOrigen, monedaDestino, cantidad);
            System.out.println(cantidad + " " + monedaOrigen + " = " + cantidadConvertida + " " + monedaDestino);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
