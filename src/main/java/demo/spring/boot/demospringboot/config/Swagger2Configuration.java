package demo.spring.boot.demospringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by tang.cheng on 2017/1/16.
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)//Docket, Springfox’s, primary api configuration mechanism is initialized for swagger specification 2.0
                .select()//select() returns an instance of ApiSelectorBuilder to give fine grained control over the endpoints exposed via swagger.
                .apis(RequestHandlerSelectors.basePackage("demo.spring.boot.demospringboot.controller"))
                .paths(PathSelectors.any())// .paths(Predicates.or(PathSelectors.regex("/api/.*")))//过滤的接口,此片过滤掉/api/打头的接口
                .build()//The selector requires to be built after configuring the api and path selectors. Out of the box we provide predicates for regex, ant, any, none
                .genericModelSubstitutes(DeferredResult.class)//异步http请求
                .forCodeGeneration(true)//By default, types with generics will be labeled with '\u00ab'(<<), '\u00bb'(>>), and commas. This can be problematic with things like swagger-codegen. You can override this behavior by implementing your own GenericTypeNamingStrategy.
                .pathMapping("/")// 在这里可以设置请求的统一前缀；默认请求都是以 / 根路径开始，如果我们的应用不是部署在根路径，比如以/platform（应用名）部署，则可以通过一下方式设置请求的统一前缀。
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)//使用默认的响应信息true：默认响应信息将会回到全局的响应信息中；false:不加到全局的响应信息中
                ;
    }


    /**
     * api描述信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerDemo")
                .version("0.1")//本次发布的版本
                .build();
    }
}
