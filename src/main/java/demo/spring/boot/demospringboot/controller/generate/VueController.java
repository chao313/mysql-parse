package demo.spring.boot.demospringboot.controller.generate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/vue")
public class VueController {
    @GetMapping("/index")
    public String index() {

        return "vue/index";

    }
}
