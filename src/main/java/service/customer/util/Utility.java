package service.customer.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    /**
     * Método para obtener la fecha y hora actual en formato "dd-MM-yyyy HH:mm:ss".
     *
     * @return Fecha y hora como String.
     */
    public static String getDateTimeNow() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return ahora.format(formatter);
    }
}
