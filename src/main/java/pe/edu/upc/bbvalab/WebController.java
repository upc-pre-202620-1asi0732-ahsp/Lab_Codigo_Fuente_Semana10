package pe.edu.upc.bbvalab;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller public class WebController {
 private final TransferService service;
 public WebController(TransferService service){this.service=service;}
 @GetMapping("/") public String home(Model model){model.addAttribute("balance",service.balance());model.addAttribute("history",service.history());return "index";}
 @PostMapping("/transfer") public String transfer(@RequestParam String destination,@RequestParam BigDecimal amount,RedirectAttributes attrs){
  try{service.send(destination,amount);attrs.addFlashAttribute("success","Transferencia simulada realizada correctamente");}
  catch(IllegalArgumentException ex){attrs.addFlashAttribute("error",ex.getMessage());}
  return "redirect:/";
 }
}
