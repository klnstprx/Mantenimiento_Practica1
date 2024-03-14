package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClubDeportivoTest {

    ClubDeportivo club1, club2;

    @BeforeEach
    public void setup() throws ClubException {
        club1 = new ClubDeportivo("Club 1");
    }

    @Test
    @DisplayName("Nombre de club1 correcta.")
    public void clubNameCorrect() {
        String expectedValue = "Club 1 --> [  ]";
        String obtainedValue = club1.toString();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Constructor con número de grupos negativo throws exception.")
    public void constructorThrowsClubException() {
        assertThrows(ClubException.class, () -> club2 = new ClubDeportivo("Club 2", -1));
    }

    @Test
    @DisplayName("Añadir actividad con grupo null throws exception.")
    public void anyadirActividadThrowsClubException() {
        Grupo g = null;
        assertThrows(ClubException.class, () -> club1.anyadirActividad(g));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A1,Deporte,abc,5,10.0", "B1,Deporte,10,-xcd,10.0", "B2,Deporte,10,5,10.0x"})
    @DisplayName("Añadir actividad con datos incorrectos throws exception.")
    public void anyadirActividadWithIncorrectDataThrowsClubException(String datos) {
        String[] datosSplit = datos.split(",");
        assertThrows(ClubException.class, () -> club1.anyadirActividad(datosSplit));
    }

    @Test
    @DisplayName("Anadir mas de una actividad.")
    public void anyadirActivdadWithMoreThanOneActividad() throws ClubException {
        String[] datos1 = {"A1", "Deporte", "5", "3", "10.0"};
        club1.anyadirActividad(datos1);
        String[] datos2 = {"B1", "Deporte", "10", "5", "10.0"};
        club1.anyadirActividad(datos2);
        String expectedValue = "Club 1 --> [ (A1 - Deporte - 10.0 euros - P:5 - M:3), (B1 - Deporte - 10.0 euros - P:10 - M:5) ]";
        String obtainedValue = club1.toString();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Añadir actividad con datos correctos.")
    public void anyadirActividadWithCorrectData() throws ClubException {
        String[] datos1 = {"A1", "Deporte", "5", "3", "10.0"};
        club1.anyadirActividad(datos1);
        String expectedValue = "Club 1 --> [ (A1 - Deporte - 10.0 euros - P:5 - M:3) ]";
        String obtainedValue = club1.toString();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Añadir actividad con grupo ya existente.")
    public void anyadirActividadWithExistingGroup() throws ClubException {
        String[] datos = {"A1", "Deporte", "5", "3", "10.0"};
        club1.anyadirActividad(datos);
        String[] datos2 = {"A1", "Deporte", "6", "3", "10.0"};
        club1.anyadirActividad(datos2);
        String expectedValue = "Club 1 --> [ (A1 - Deporte - 10.0 euros - P:6 - M:3) ]";
        String obtainedValue = club1.toString();
        assertEquals(expectedValue, obtainedValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,1})
    @DisplayName("Matricular en actividad con plazas libres.")
    public void matricularWithFreePlaces(int numPersonas) throws ClubException {
        String[] datos = {"A1", "Deporte", "5", "3", "10.0"};
        String[] datos1 = {"A123", "Karate", "10", "5", "10.0"};
        club1.anyadirActividad(datos);
        club1.anyadirActividad(datos1);
        club1.matricular("Deporte", numPersonas);
        club1.matricular("Karate", numPersonas);
        int expectedMatriculadosDeporte = numPersonas + 3;
        int expectedMatriculadosKarate = numPersonas + 5;
        String expectedValue = "Club 1 --> [ (A1 - Deporte - 10.0 euros - P:5 - M:"+ expectedMatriculadosDeporte +"), (A123 - Karate - 10.0 euros - P:10 - M:"+ expectedMatriculadosKarate +") ]";
        String obtainedValue = club1.toString();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Matricular en actividad sin plazas libres throws exception.")
    public void matricularWithoutFreePlacesThrowsClubException() throws ClubException {
        String[] datos = {"A1", "Deporte", "5", "3", "10.0"};
        club1.anyadirActividad(datos);
        assertThrows(ClubException.class, () -> club1.matricular("Deporte", 3));
    }

    @Test
    @DisplayName("Se comprueba que se calcule bien la cantidad aumentada de ingresos")
    public void ingresos() throws ClubException {
        String[] datos = {"A1", "Deporte", "5", "3", "10.0"};
        club1.anyadirActividad(datos);
        double expectedValue = 30.0;
        double obtainedValue = club1.ingresos();
        assertEquals(expectedValue, obtainedValue);
    }



}
