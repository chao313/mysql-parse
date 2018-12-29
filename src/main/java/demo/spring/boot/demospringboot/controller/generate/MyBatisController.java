package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.mybatis.service.ColumnsService;
import demo.spring.boot.demospringboot.mybatis.service.SchemataService;
import demo.spring.boot.demospringboot.mybatis.service.TablesService;
import demo.spring.boot.demospringboot.mybatis.vo.ColumnsVo;
import demo.spring.boot.demospringboot.mybatis.vo.SchemataVo;
import demo.spring.boot.demospringboot.mybatis.vo.TablesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mybatis")
public class MyBatisController {

    @Autowired
    private SchemataService schemataService;

    @Autowired
    private TablesService tablesService;

    @Autowired
    private ColumnsService columnsService;


    @GetMapping(value = "/getAllDB")
    public Response<List<SchemataVo>> getAllDataBases() {
        Response<List<SchemataVo>> response = new Response<>();
        try {
            SchemataVo query = new SchemataVo();
            List<SchemataVo> list = schemataService.queryBase(query);
            response.setCode(Code.System.OK);
            response.setContent(list);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;
    }

    @GetMapping(value = "/getAllTablesByDB")
    public Response<List<TablesVo>> getAllTablesByDB(@RequestParam(value = "dataBase") String dataBase) {
        Response<List<TablesVo>> response = new Response<>();
        try {
            TablesVo query = new TablesVo();
            query.setTableSchema(dataBase);
            List<TablesVo> list = tablesService.queryBase(query);
            response.setCode(Code.System.OK);
            response.setContent(list);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;
    }

    @GetMapping(value = "/getAllColumns")
    public Response<List<ColumnsVo>> getAllColumnColumnsVoByTab(@RequestParam(value = "dataBase") String dataBase,
                                                                @RequestParam(value = "tableName") String tableName) {
        Response<List<ColumnsVo>> response = new Response<>();
        try {
            ColumnsVo query = new ColumnsVo();
            query.setTableSchema(dataBase);
            query.setTableName(tableName);
            List<ColumnsVo> list = columnsService.queryBase(query);
            response.setCode(Code.System.OK);
            response.setContent(list);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;
    }


}
