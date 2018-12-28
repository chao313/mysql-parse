package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.parse.mysql.parse.db.GenerateFile;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2018/4/6    Created by   juan
 */

@RestController
@RequestMapping(value = "/generate")
public class GenerateController {

    private static Logger LOGGER =
            LoggerFactory.getLogger(GenerateController.class);


    @GetMapping("/generateFile")
    public Response<JavaTable> GenerateFile(@RequestParam(value = "dataBase") String dataBase,
                                            @RequestParam(value = "ptableName") String ptableName,
                                            @RequestParam(value = "basePackage") String basePackage) {
        Response<JavaTable> response = new Response<>();
        try {
            JavaTable javaTable = GenerateFile.GenerateFile(dataBase, ptableName, basePackage);
            response.setCode(Code.System.OK);
            response.setContent(javaTable);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            LOGGER.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }
}
