package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.parse.mysql.parse.db.GenerateFile;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.AssociationTable;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 2018/4/6    Created by   juan
 */

@RestController
@RequestMapping(value = "/generateAssociation")
public class GenerateAssociationController {

    private static Logger LOGGER =
            LoggerFactory.getLogger(GenerateAssociationController.class);

    private static final String tmpPath = "tmp/";

//    @PostMapping("/generateFile")
//    public Response<JavaTable> GenerateFileAssociation(@RequestBody BaseAndAssociationRequest request) {
//        Response<JavaTable> response = new Response<>();
//        try {
//            JavaTable javaTable = GenerateFile.GenerateFileAssociation(request.getDataBase(),
//                    request.getTableBase(),
//                    request.getBasePackage(),
//                    request.getAssociationRequests());
//            response.setCode(Code.System.OK);
//            response.setContent(javaTable);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            LOGGER.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }

    @PostMapping("/generateFile")
    public Response<JavaTable> GenerateFileAssociation(@RequestParam(value = "dataBase") String dataBase,
                                                       @RequestParam(value = "basePackage") String basePackage,
                                                       @RequestParam(value = "tableBase") String tableBase,
                                                       @RequestParam(value = "hasChooseAssociationLeftTable[]") List<String> leftTables,
                                                       @RequestParam(value = "hasChooseAssociationLeftField[]") List<String> leftFields,
                                                       @RequestParam(value = "hasChooseAssociationRightTable[]") List<String> rightTables,
                                                       @RequestParam(value = "hasChooseAssociationRightField[]") List<String> rightFields) {
        Response<JavaTable> response = new Response<>();
        try {
            //转换关系
            List<AssociationTable> associationTables = new ArrayList<>();
            for (int i = 0; i < leftTables.size(); i++) {
                AssociationTable associationTable = new AssociationTable();
                associationTable.setLeftTable(leftTables.get(i));
                associationTable.setLeftField(leftFields.get(i));
                associationTable.setRightTable(rightTables.get(i));
                associationTable.setRightField(rightFields.get(i));
                associationTables.add(associationTable);
            }

            JavaTable javaTable = GenerateFile.GenerateFileAssociation(dataBase,
                    basePackage,
                    basePackage,
                    associationTables);
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
