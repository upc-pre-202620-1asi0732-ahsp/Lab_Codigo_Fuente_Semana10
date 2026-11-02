package pe.edu.upc.bbvalab;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TransferServiceTest {
 @Test void shouldSubtractAmount(){var s=new TransferService();var t=s.send("001123456789",new BigDecimal("50.00"));assertEquals(new BigDecimal("1200.00"),s.balance());assertEquals(new BigDecimal("1200.00"),t.resultingBalance());}
 @Test void shouldRejectInsufficientFunds(){var s=new TransferService();assertThrows(IllegalArgumentException.class,()->s.send("001123456789",new BigDecimal("9999.00")));assertEquals(new BigDecimal("1250.00"),s.balance());}
 @Test void shouldRejectNegativeAmount(){var s=new TransferService();assertThrows(IllegalArgumentException.class,()->s.send("001123456789",new BigDecimal("-1.00")));}
 @Test void shouldRejectInvalidDestination(){var s=new TransferService();assertThrows(IllegalArgumentException.class,()->s.send("abc",new BigDecimal("1.00")));}
}
