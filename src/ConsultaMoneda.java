import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ConsultaMoneda {
    private static final String API_KEY = "97a3391d2170278e8956945c";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private Map<String, Moneda> tasas;

    public ConsultaMoneda(String baseCurrency) throws Exception {
        String urlString = BASE_URL + API_KEY + "/latest/" + baseCurrency;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parsear JSON
        JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
        JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");

        tasas = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : conversionRates.entrySet()) {
            tasas.put(entry.getKey(), new Moneda(entry.getKey(), entry.getValue().getAsDouble()));
        }
    }

    public double convertir(String fromCurrency, String toCurrency, double amount) {
        if (!tasas.containsKey(fromCurrency) || !tasas.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Código de moneda inválido");
        }
        double tasaDesde = tasas.get(fromCurrency).tasa();
        double tasaHasta = tasas.get(toCurrency).tasa();
        return amount * (tasaHasta / tasaDesde);
    }
}
