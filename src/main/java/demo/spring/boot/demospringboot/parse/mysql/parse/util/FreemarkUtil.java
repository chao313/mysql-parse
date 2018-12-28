package demo.spring.boot.demospringboot.parse.mysql.parse.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkUtil {

    private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

    /**
     * @param map
     * @param templateDirPath  模板所在文件夹path
     * @param templateFilePath 模板的文件path
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static StringBuffer generateXmlByTemplate(Map<String, Object> map, File templateDirFile, String templateFilePath)
            throws IOException, TemplateException {
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassicCompatible(true);
        configuration.setDirectoryForTemplateLoading(templateDirFile);
        Template template =
                configuration.getTemplate(templateFilePath); //文件名
        StringWriter
                stringWriter = new StringWriter();
        template.process(map, stringWriter);
        stringWriter.close();
        return stringWriter.getBuffer();
    }
}