package com.desafio.tools.management.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(metaData())
                .globalResponseMessage(
                    RequestMethod.GET,
                        Collections.singletonList(
                            new ResponseMessageBuilder()
                                .code(500)
                                .message("Internal Server Error")
                                .build()
                        )
                ).globalResponseMessage(
                        RequestMethod.POST,
                        Collections.singletonList(
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal Server Error")
                                        .build()
                        )
                );
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Tool Management")
                .description("Document for Tool Management API")
                .version("1.0.0")
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
         .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
