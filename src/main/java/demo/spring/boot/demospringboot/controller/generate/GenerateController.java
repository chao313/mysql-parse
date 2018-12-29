package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.parse.mysql.parse.db.GenerateFile;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import demo.spring.boot.demospringboot.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

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

//    @GetMapping("/generateFileAndCreate")
//    public Response<JavaTable> generateFileAndCreate(@RequestParam(value = "dataBase") String dataBase,
//                                                     @RequestParam(value = "ptableName") String ptableName,
//                                                     @RequestParam(value = "basePackage") String basePackage) {
//        Response<JavaTable> response = new Response<>();
//        try {
//            BufferedOutputStream voOutputStream = null;
//            BufferedOutputStream daoOutputStream = null;
//            BufferedOutputStream serviceOutputStream = null;
//            BufferedOutputStream serviceImplOutputStream = null;
//            BufferedOutputStream mapperFileOutputStream = null;
//            JavaTable javaTable = GenerateFile.GenerateFile(dataBase, ptableName, basePackage);
//
//
//            //创建文件夹
//            new File(javaTable.getBasePackagePath()).mkdirs();
//
//            File voFile = new File(javaTable.getClassVoPath());
//            voFile.createNewFile();
//            voOutputStream = new BufferedOutputStream(new FileOutputStream(voFile));
//            voOutputStream.write(javaTable.getClassVoStr().getBytes());
//            voOutputStream.flush();
//
//            File daoFile = new File(javaTable.getClassDaoPath());
//            daoFile.createNewFile();
//            daoOutputStream = new BufferedOutputStream(new FileOutputStream(daoFile));
//            daoOutputStream.write(javaTable.getClassDaoStr().getBytes());
//            daoOutputStream.flush();
//
//            File serviceFile = new File(javaTable.getClassServiceImplPath());
//            serviceFile.createNewFile();
//            serviceOutputStream = new BufferedOutputStream(new FileOutputStream(serviceFile));
//            serviceOutputStream.write(javaTable.getClassDaoStr().getBytes());
//            serviceOutputStream.flush();
//
//            File serviceImplFile = new File(javaTable.getClassServiceImplPath());
//            serviceFile.createNewFile();
//            serviceImplOutputStream = new BufferedOutputStream(new FileOutputStream(serviceImplFile));
//            serviceImplOutputStream.write(javaTable.getClassDaoStr().getBytes());
//            serviceImplOutputStream.flush();
//
//            File mapperFile = new File(javaTable.getMapperPath());
//            mapperFile.createNewFile();
//            mapperFileOutputStream = new BufferedOutputStream(new FileOutputStream(mapperFile));
//            mapperFileOutputStream.write(javaTable.getMapperStr().getBytes());
//            mapperFileOutputStream.flush();
//
//
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            LOGGER.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }


    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownLoad(@RequestParam(value = "dataBase") String dataBase,
                                               @RequestParam(value = "ptableName") String ptableName,
                                               @RequestParam(value = "basePackage") String basePackage) throws Exception {

        JavaTable javaTable = GenerateFile.GenerateFile(dataBase, ptableName, basePackage);
        String dirPath = javaTable.getBasePackagePath().substring(0,
                javaTable.getBasePackagePath().indexOf("/"));
        String fileNameZip = dirPath + ".zip";
        InputStream inputStream = this.createFilesAndZip(javaTable, fileNameZip, dirPath);

        byte[] body = null;
        body = new byte[inputStream.available()];
        inputStream.read(body);//读入到输入流里面

        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileNameZip);//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(body, headers, statusCode);
        return response;
    }

    /**
     * 返回的是压缩后的文件流,
     *
     * @param javaTable
     * @param zipFileName 文件地址
     * @return
     * @throws IOException
     */
    private InputStream createFilesAndZip(JavaTable javaTable, String zipFileName, String dirPath) throws IOException {
        BufferedOutputStream voOutputStream = null;
        BufferedOutputStream daoOutputStream = null;
        BufferedOutputStream serviceOutputStream = null;
        BufferedOutputStream serviceImplOutputStream = null;
        BufferedOutputStream mapperFileOutputStream = null;

        //创建文件夹
        new File(javaTable.getBasePackagePath()).mkdirs();

        File voFile = new File(javaTable.getClassVoPath());
        voFile.createNewFile();
        voOutputStream = new BufferedOutputStream(new FileOutputStream(voFile));
        voOutputStream.write(javaTable.getClassVoStr().getBytes());
        voOutputStream.flush();

        File daoFile = new File(javaTable.getClassDaoPath());
        daoFile.createNewFile();
        daoOutputStream = new BufferedOutputStream(new FileOutputStream(daoFile));
        daoOutputStream.write(javaTable.getClassDaoStr().getBytes());
        daoOutputStream.flush();

        File serviceFile = new File(javaTable.getClassServiceImplPath());
        serviceFile.createNewFile();
        serviceOutputStream = new BufferedOutputStream(new FileOutputStream(serviceFile));
        serviceOutputStream.write(javaTable.getClassDaoStr().getBytes());
        serviceOutputStream.flush();

        File serviceImplFile = new File(javaTable.getClassServiceImplPath());
        serviceFile.createNewFile();
        serviceImplOutputStream = new BufferedOutputStream(new FileOutputStream(serviceImplFile));
        serviceImplOutputStream.write(javaTable.getClassDaoStr().getBytes());
        serviceImplOutputStream.flush();

        File mapperFile = new File(javaTable.getMapperPath());
        mapperFile.createNewFile();
        mapperFileOutputStream = new BufferedOutputStream(new FileOutputStream(mapperFile));
        mapperFileOutputStream.write(javaTable.getMapperStr().getBytes());
        mapperFileOutputStream.flush();

        voOutputStream.close();
        daoOutputStream.close();
        serviceOutputStream.close();
        serviceImplOutputStream.close();
        mapperFileOutputStream.close();


        File file = new File(zipFileName);
        OutputStream outputStream = new FileOutputStream(file);
        ZipUtils.toZip(dirPath, outputStream, true);
        outputStream.flush();
        outputStream.close();

        InputStream inputStream = new FileInputStream(zipFileName);



        voFile.delete();
        daoFile.delete();
        serviceFile.delete();
        serviceImplFile.delete();
        mapperFile.delete();
        new File(dirPath).delete();
        file.delete();

        return inputStream;
    }


}
