package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GrupoTest {

    Grupo grupo;

    @BeforeEach
    public void setup() throws ClubException {
        grupo = new Grupo("A1", "Deporte", 5, 3, 10.0);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE})
    @DisplayName("Constructor de grupo con datos numero de plazas invalido throws exception.")
    public void constructorThrowsClubExceptionNPlazas(int nplazas) {
        assertThrows(ClubException.class, () -> new Grupo("A1", "Deporte", nplazas, 6, 10.0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE, 20})
    @DisplayName("Constructor de grupo con datos numero de matriculados invalido o mayor que numero de plazas throws exception.")
    public void constructorThrowsClubExceptionNMatriculados(int matriculados) {
        assertThrows(ClubException.class, () -> new Grupo("A1", "Deporte", 5, matriculados, 10.0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE})
    @DisplayName("Constructor de grupo con tarifa invalida throws exception.")
    public void constructorThrowsClubExceptionTarifa(int tarifa) {
        assertThrows(ClubException.class, () -> new Grupo("A1", "Deporte", 5, 3, tarifa));
    }


    @Test
    @DisplayName("Codigo de grupo correcto.")
    public void getCodigoCorrect() {
        String expectedValue = "A1";
        String obtainedValue = grupo.getCodigo();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Actividad de grupo correcta.")
    public void getActividadCorrect() {
        String expectedValue = "Deporte";
        String obtainedValue = grupo.getActividad();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Numero de plazas de grupo correctas.")
    public void getPlazasCorrect() {
        int expectedValue = 5;
        int obtainedValue = grupo.getPlazas();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Numero de matriculados de grupo correctas.")
    public void getMatriculadosCorrect() {
        int expectedValue = 3;
        int obtainedValue = grupo.getMatriculados();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Tarifa de grupo correcta.")
    public void getTarifaCorrect() {
        double expectedValue = 10.0;
        double obtainedValue = grupo.getTarifa();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Plazas libres de grupo correctas.")
    public void plazasLibresCorrect() {
        int expectedValue = 2;
        int obtainedValue = grupo.plazasLibres();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("actualizarPlazas añade el numero de plazas correcto.")
    public void actualizarPlazasCorrect() throws ClubException {
        grupo.actualizarPlazas(10);
        int expectedValue = 10;
        int obtainedValue = grupo.getPlazas();
        assertEquals(expectedValue, obtainedValue);
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE, 1})
    @DisplayName("actualizarPlazas con numero de plazas negativo o invalido throws exception.")
    public void actualizarPlazasThrowsClubException(int nplazas) {
        assertThrows(ClubException.class, () -> grupo.actualizarPlazas(nplazas));
    }

    @Test
    @DisplayName("matricular con numero de plazas libres suficiente añade el numero de matriculados correcto.")
    public void matricularCorrect() throws ClubException {
        grupo.matricular(2);
        int expectedValue = 5;
        int obtainedValue = grupo.getMatriculados();
        assertEquals(expectedValue, obtainedValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE, Integer.MAX_VALUE})
    @DisplayName("matricular con numero de plazas libres insuficiente o invalido throws exception.")
    public void matricularThrowsClubException(int matriculas) {
        assertThrows(ClubException.class, () -> grupo.matricular(matriculas));
    }
    @Test
    @DisplayName("toString de grupo correcto.")
    public void toStringCorrect() {
        String expectedValue = "(A1 - Deporte - 10.0 euros - P:5 - M:3)";
        String obtainedValue = grupo.toString();
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Equals de grupos iguales da true")
    public void equalsTrue() throws ClubException {
        Grupo grupo2 = new Grupo("A1", "Deporte", 5, 3, 10.0);
        boolean expectedValue = true;
        boolean obtainedValue = grupo.equals(grupo2);
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Equals de grupos distintos da false")
    public void equalsFalse() throws ClubException {
        Grupo grupo1 = new Grupo("A2", "Karate", 5, 3, 10.0);
        boolean expectedValue = false;
        boolean obtainedValue = grupo.equals(grupo1);
        assertEquals(expectedValue, obtainedValue);

        Grupo grupo2 = new Grupo("A1", "Karate", 5, 3, 10.0);
        boolean expectedValue2 = false;
        boolean obtainedValue2 = grupo.equals(grupo2);
        assertEquals(expectedValue2, obtainedValue2);
    }

    @Test
    @DisplayName("Equals de dos objetos distintos da false")
    public void equalsDifferentObjectFalse() {
        boolean expectedValue = false;
        boolean obtainedValue = grupo.equals(new Object());
        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("HashCode de grupo correcto.")
    public void hashCodeCorrect() {
        int expectedValue = grupo.getCodigo().toUpperCase().hashCode()+grupo.getActividad().toUpperCase().hashCode();
        int obtainedValue = grupo.hashCode();
        assertEquals(expectedValue, obtainedValue);
    }


}
