import com.company.LotterySystem;
import com.company.NotValidNumberException;
import com.company.WrongCountOfNumbersException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.company.LotterySystem.*;
import static org.junit.jupiter.api.Assertions.*;

class LotterySystemTest {

    private static LotterySystem lotterySystem;

    @BeforeAll
    static void before() {
        lotterySystem = new LotterySystem(10);
    }

    @Test
    void createValidTicket() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        try {
            new Ticket("Test", numbers);
        } catch (WrongCountOfNumbersException | NotValidNumberException e) {
            fail("An exception shouldn't been thrown");
        }
    }

    @Test
    void createTicketWithFiveNumbers() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        assertThrows(WrongCountOfNumbersException.class, () -> new Ticket("Test", numbers));
    }

    @Test
    void createTicketWithSevenNumbers() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        assertThrows(WrongCountOfNumbersException.class, () -> new Ticket("Test", numbers));
    }

    @Test
    void createTicketWithNegativeNumber() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(-1, 1, 2, 3, 4, 5));
        assertThrows(NotValidNumberException.class, () -> new Ticket("Test", numbers));
    }

    @Test
    void createTicketWithGreaterNumberThanMax() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 11));
        assertThrows(NotValidNumberException.class, () -> new Ticket("Test", numbers));
    }

    @Test
    void generateTicketsHundredTimes() {
        for (int i = 0; i < 100; i++) {
            try {
                Ticket.generateRandomTicket("Test");
            } catch (WrongCountOfNumbersException | NotValidNumberException e) {
                fail("An exception shouldn't been thrown");
            }
        }
    }
}