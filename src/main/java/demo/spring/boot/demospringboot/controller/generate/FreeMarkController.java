package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.parse.mysql.parse.db.GenerateFile;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping(value = "/freemark")
public class FreeMarkController {

    private static Logger LOGGER =
            LoggerFactory.getLogger(FreeMarkController.class);


    @GetMapping("/index")
    public String GenerateFile(Map<String,Object> map, @RequestParam(value = "dataBase") String dataBase,
                               @RequestParam(value = "ptableName") String ptableName,
                               @RequestParam(value = "basePackage") String basePackage) {
        Response<JavaTable> response = new Response<>();
        try {
            JavaTable javaTable = GenerateFile.GenerateFile(dataBase, ptableName, basePackage);
            response.setContent(javaTable);
            map.put("javaTable", javaTable);
        } catch (Exception e) {
            map.put("e", e.toString());
            LOGGER.error("异常 ：{} ", e.getMessage(), e);
        }
        return "index";

    }
}
