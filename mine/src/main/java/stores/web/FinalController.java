package stores.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinalController {

    @GetMapping("/final")
    public String finalPage() {
        return "final";
    }
}
