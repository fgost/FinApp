package dsw.finApp.jsp.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/finApp/jsp")
@Validated
public class JSPController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/teste")
    public String index2() {
        return "index";
    }
}
