package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.parse.mysql.parse.db.GenerateFile;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.AssociationJavaTable;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.AssociationTable;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import demo.spring.boot.demospringboot.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
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
    public Response<List<AssociationJavaTable>> GenerateFileAssociation(@RequestParam(value = "dataBase") String dataBase,
                                                                        @RequestParam(value = "basePackage") String basePackage,
                                                                        @RequestParam(value = "tableBase") String tableBase,
                                                                        @RequestParam(value = "hasChooseAssociationLeftTable[]") List<String> leftTables,
                                                                        @RequestParam(value = "hasChooseAssociationLeftField[]") List<String> leftFields,
                                                                        @RequestParam(value = "hasChooseAssociationRightTable[]") List<String> rightTables,
                                                                        @RequestParam(value = "hasChooseAssociationRightField[]") List<String> rightFields) {
        Response<List<AssociationJavaTable>> response = new Response<>();
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

            List<AssociationJavaTable> javaTable = GenerateFile.GenerateFileAssociation(dataBase,
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

    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownLoad(@RequestParam(value = "dataBase") String dataBase,
                                               @RequestParam(value = "basePackage") String basePackage,
                                               @RequestParam(value = "tableBase") String tableBase,
                                               @RequestParam(value = "hasChooseAssociationLeftTable") List<String> leftTables,
                                               @RequestParam(value = "hasChooseAssociationLeftField") List<String> leftFields,
                                               @RequestParam(value = "hasChooseAssociationRightTable") List<String> rightTables,
                                               @RequestParam(value = "hasChooseAssociationRightField") List<String> rightFields) throws Exception {
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

        List<AssociationJavaTable> associationJavaTables = GenerateFile.GenerateFileAssociation(dataBase,
                basePackage,
                basePackage,
                associationTables);
        AssociationJavaTable associationJavaTable = associationJavaTables.get(0);
        String dirPath = associationJavaTable.getBasePackagePath().indexOf("/") > 0 ? associationJavaTable.getBasePackagePath().substring(0,
                associationJavaTable.getBasePackagePath().indexOf("/")) : associationJavaTable.getBasePackagePath();
        String fileNameZip = dirPath + ".zip";
        InputStream inputStream = ZipUtils.createFilesAndZip(associationJavaTables, fileNameZip, dirPath);

        byte[] body = null;
        body = new byte[inputStream.available()];
        inputStream.read(body);//读入到输入流里面

        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileNameZip);//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(body, headers, statusCode);
        return response;
    }

}
