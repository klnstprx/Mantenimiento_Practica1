package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClubExceptionTest {
    ClubException clubException1, clubException2;


    @BeforeEach
    void setup(){
        clubException1 = new ClubException();
        clubException2 = new ClubException("ERROR");
    }

    @Test
    @DisplayName("El mensaje de error es el mismo que el mensaje pasado")
    void messageNotChanged() {
        String expectedMessage1 = null, expectedMessage2 = "ERROR";
        String obtainedMessage1 = clubException1.getMessage();
        String obtainedMessage2 = clubException2.getMessage();

        assertEquals(expectedMessage1, obtainedMessage1);
        assertEquals(expectedMessage2, obtainedMessage2);
    }
}
