package demo.spring.boot.demospringboot.controller;

import demo.spring.boot.demospringboot.mybatis.service.TsEutrainCustomsDeclarationBindService;
import demo.spring.boot.demospringboot.mybatis.vo.TsEutrainCustomsDeclarationBindUpdateRequest;
import demo.spring.boot.demospringboot.mybatis.vo.TsEutrainCustomsDeclarationBindVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/TsEutrainCustomsDeclarationBindService")
public class TestController {

    @Autowired
    private TsEutrainCustomsDeclarationBindService service;

    @PostMapping(value = "/insert")
    public Boolean exec(@RequestBody() TsEutrainCustomsDeclarationBindVo vo) {
        return service.insert(vo);
    }

    @PostMapping(value = "/inserts")
    public Boolean exec(@RequestBody() List<TsEutrainCustomsDeclarationBindVo> vo) {
    return service.insert(vo);
    }

    @PostMapping(value = "/queryBase")
    public List<TsEutrainCustomsDeclarationBindVo> queryBase(@RequestBody() TsEutrainCustomsDeclarationBindVo query) {
        return service.queryBase(query);
        }

        @PostMapping(value = "/updateBase")
        public Boolean updateBase(@RequestBody TsEutrainCustomsDeclarationBindUpdateRequest request) {
        return service.updateBase(request.getSource(), request.getTarget());
        }

        @PostMapping(value = "/updateBaseIncludeNull")
        public Boolean updateBaseIncludeNull(@RequestBody TsEutrainCustomsDeclarationBindUpdateRequest request) {
        return service.updateBaseIncludeNull(request.getSource(), request.getTarget());
        }

        @PostMapping(value = "/deleteBase")
        public Boolean deleteBase(@RequestBody() TsEutrainCustomsDeclarationBindVo vo) {
        return service.deleteBase(vo);
        }

        @PostMapping(value = "/queryByPrimaryKey")
        public TsEutrainCustomsDeclarationBindVo queryByPrimaryKey(@RequestParam(value = "id") Integer id) {
        return service.queryByPrimaryKey(id);
        }

        @PostMapping(value = "/deleteByPrimaryKey")
        public boolean deleteByPrimaryKey(@RequestParam(value = "id") Integer id) {
        return service.deleteByPrimaryKey(id);
        }

        }
