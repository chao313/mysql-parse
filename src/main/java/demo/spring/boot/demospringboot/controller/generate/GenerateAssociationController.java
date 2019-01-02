package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.parse.mysql.parse.db.GenerateFile;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.BaseAndAssociationRequest;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BeanParam;

/**
 * 2018/4/6    Created by   juan
 */

@RestController
@RequestMapping(value = "/generateAssociation")
public class GenerateAssociationController {

    private static Logger LOGGER =
            LoggerFactory.getLogger(GenerateAssociationController.class);

    private static final String tmpPath = "tmp/";

    @PostMapping("/generateFile")
    public Response<JavaTable> GenerateFileAssociation(@RequestBody BaseAndAssociationRequest request) {
        Response<JavaTable> response = new Response<>();
        try {
            JavaTable javaTable = GenerateFile.GenerateFileAssociation(request.getDataBase(),
                    request.getTableBase(),
                    request.getBasePackage(),
                    request.getAssociationRequests());
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
