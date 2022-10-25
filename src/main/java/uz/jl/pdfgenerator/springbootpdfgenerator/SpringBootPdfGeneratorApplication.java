package uz.jl.pdfgenerator.springbootpdfgenerator;

import com.itextpdf.html2pdf.HtmlConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class SpringBootPdfGeneratorApplication {

    final Configuration configuration;

    public SpringBootPdfGeneratorApplication(Configuration configuration) {
        this.configuration = configuration;
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringBootPdfGeneratorApplication.class, args);
    }


    @GetMapping
    public String generateTemplate() throws IOException, TemplateException {
        Template template = configuration.getTemplate("template-file.ftl");


        String data = FreeMarkerTemplateUtils.processTemplateIntoString(template, Map
                .of("name", "Komilova Robiya opa",
                        "interests", List.of("Uxlash", "Dars Qilmaslik", "Cartoon Ko'rish")));

        HtmlConverter.convertToPdf(data, new FileOutputStream("resume.pdf"));

        return data;
    }


    @Bean
    public FreeMarkerConfigurationFactoryBean configuration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }

}


