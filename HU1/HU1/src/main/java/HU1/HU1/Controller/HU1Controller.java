package HU1.HU1.Controller;

import HU1.HU1.Service.HU1Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/HU1")
@CrossOrigin(origins = "*")

public class HU1Controller {
    @PostMapping("/simulateLoanAmount")
    public ResponseEntity<Integer> simulateLoanAmount(@RequestBody Map<String, String> body) {
        try {
            // Verificar que los parámetros están presentes
            if (!body.containsKey("amount") || !body.containsKey("termYears") || !body.containsKey("annualInterest")) {
                return ResponseEntity.badRequest().body(null); // Retorna un código 400 si falta algún parámetro
            }

            // Parsear los valores desde el JSON
            int amount = Integer.parseInt(body.get("amount"));
            int termYears = Integer.parseInt(body.get("termYears"));
            double annualInterest = Double.parseDouble(body.get("annualInterest"));

            // Llamar al servicio para calcular el préstamo
            int result = HU1Service.simulateLoanAmount(amount, termYears, annualInterest);
            return ResponseEntity.ok(result);

        } catch (NumberFormatException e) {
            // Manejar la excepción si los parámetros no son válidos
            return ResponseEntity.badRequest().body(null);
        }
    }
}
