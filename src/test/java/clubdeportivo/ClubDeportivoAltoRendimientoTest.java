package clubdeportivo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoAltoRendimientoTest {
    ClubDeportivoAltoRendimiento clubDeportivoAltoRendimiento1;
    ClubDeportivoAltoRendimiento clubDeportivoAltoRendimiento2;

    @BeforeEach
    void setup() throws ClubException {
        clubDeportivoAltoRendimiento1 = new ClubDeportivoAltoRendimiento("Grupo1", 10, 1);
        clubDeportivoAltoRendimiento2 = new ClubDeportivoAltoRendimiento("Grupo2", 10, 20, 2);
    }

    @ParameterizedTest
    @CsvSource({"grupoMaximoNegativoSinTamaño, -1, -88, 1", "grupoIncrementoNegativoSinTamaño, -1, 10, -1",
                "grupoMaximoNegativoConTamaño, 10, -88, 1", "grupoIncrementoNegativoConTamaño, 10, 10, -1"})
    @DisplayName("Un club tanto con tamaño como sin tamaño, si el máximo o el incremento es menor que 0 salta error")
    void clubDeportivoAltoRendimientoMaximoOIncrementoMenorQueCero(String nombre, int tam, int maximo, double incremento) {
        assertThrows(ClubException.class, () -> {
            if (tam == -1) {
                new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
            } else {
                new ClubDeportivoAltoRendimiento(nombre, tam, maximo, incremento);
            }
        });
    }

    @ParameterizedTest
    @CsvSource({"1A-Kizomba-20.0-15, 25, 15", "1A-Kizomba-20.0-15.2-3.2, 25, 15", "1A-Kizomba-25-15-25.0, 10, 10",
            "1A-Kizomba-25-10-25.0, 10, 10", "1A-Kizomba-9-5-20.0, 9, 5"})
    @DisplayName("Limita el numero de plazas si recibe un número mayor al permitido")
    void limiteDeParticipantesControlado(String datos, int expectedMaximo, int expectedMatriculados) throws ClubException {
        String[] datosSplit = datos.split("-");
        int obtainedMaximo = -1, obtainedMatriculados = -1;

        if (datosSplit.length < 5 || !esNumeroEntero(datosSplit[2]) || !esNumeroEntero(datosSplit[3])) {
            assertThrows(ClubException.class, () -> {
                clubDeportivoAltoRendimiento1.anyadirActividad(datosSplit);
            });
        } else {
            String[] actividad1 = {"4D", "Pilates", "10", "10", "5.0"};

            clubDeportivoAltoRendimiento1.anyadirActividad(actividad1);
            clubDeportivoAltoRendimiento1.anyadirActividad(datosSplit);

            // Obtener la cadena con todos los grupos
            String grupoString = clubDeportivoAltoRendimiento1.toString();

            String grupo = grupoString.substring(13).substring(0, grupoString.length() - 15);

            // Expresión regular para dividir la cadena en grupos individuales
            String[] actividades = grupo.split(", ");

            for (String actividad : actividades) {
                String datosActividad = actividad.substring(1).substring(0, actividad.length() - 2);
                String[] datosSplitActividad = datosActividad.split(" - ");

                if (datosSplitActividad[0].equals(datosSplit[0])) {
                    obtainedMaximo = Integer.parseInt(datosSplitActividad[3].substring(2));
                    obtainedMatriculados = Integer.parseInt(datosSplitActividad[4].substring(2));
                }
            }

            assertEquals(expectedMaximo, obtainedMaximo);
            assertEquals(expectedMatriculados, obtainedMatriculados);
        }
    }



    public static boolean esNumeroEntero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    @ParameterizedTest
    @CsvSource({"1A, Kizomba, 20, 15, 25.0", "1A, Kizomba, 2015"})
    @DisplayName("Control de errores en los datos al añadir una nueva actividad")
    void erroresEnDatos(String datos) throws ClubException {
        String[] datosSplit = datos.split("-");

        assertThrows(ClubException.class, () -> {
            clubDeportivoAltoRendimiento1.anyadirActividad(datosSplit);
        });
    }

    @Test
    @DisplayName("Se comprueba que se calcule bien la cantidad aumentada de ingresos")
    void nuevosIngresosCalculados() throws ClubException {
        double cantidad = 100;
        clubDeportivoAltoRendimiento1.anyadirActividad(new String[]{"1A", "Kizomba", "20", "15", "10.0"});
        double expectedCantidadFinal = cantidad+cantidad*(1.0/100.0);
        double obtainedCantidadFinal = clubDeportivoAltoRendimiento1.ingresos();

        assertEquals(expectedCantidadFinal, obtainedCantidadFinal);
    }
}
