package pe.edu.upc.bbvalab;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class TransferService {
 private BigDecimal balance=new BigDecimal("1250.00");
 private final List<Transfer> history=new ArrayList<>();
 public synchronized BigDecimal balance(){return balance;}
 public synchronized List<Transfer> history(){return List.copyOf(history);}
 public synchronized Transfer send(String destination,BigDecimal amount){
  if(destination==null || !destination.matches("[0-9]{10,20}"))throw new IllegalArgumentException("La cuenta debe tener entre 10 y 20 digitos");
  if(amount==null || amount.signum()<=0 || amount.scale()>2)throw new IllegalArgumentException("El importe debe ser positivo y tener hasta dos decimales");
  if(amount.compareTo(balance)>0)throw new IllegalArgumentException("Saldo insuficiente");
  balance=balance.subtract(amount);
  Transfer transfer=new Transfer(destination,amount,balance);
  history.add(0,transfer);return transfer;
 }
 public record Transfer(String destination,BigDecimal amount,BigDecimal resultingBalance){}
}
