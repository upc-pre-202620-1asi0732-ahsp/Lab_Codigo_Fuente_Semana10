package pe.edu.upc.bbvalab;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;
import java.util.List;
@WebMvcTest(WebController.class) class WebControllerTest {
 @Autowired MockMvc mvc;
 @MockBean TransferService service;
 @Test void homeShowsDashboard() throws Exception {when(service.balance()).thenReturn(new BigDecimal("1250.00"));when(service.history()).thenReturn(List.of());mvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));}
 @Test void transferRedirects() throws Exception {mvc.perform(post("/transfer").param("destination","001123456789").param("amount","10.00")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));}
}
