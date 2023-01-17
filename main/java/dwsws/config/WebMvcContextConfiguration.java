package dwsws.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import dwsws.interceptor.LoginInterceptor;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = { "dwsws.controller" })
@Import({ ContextDatasource.class, ContextSqlMapper.class })
public class WebMvcContextConfiguration implements WebMvcConfigurer {
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	}

//	    default servlet handler를 사용하게 합니다.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		System.out.println("add view controller가 실행 되었습니다.");
		registry.addViewController("/").setViewName("index");
	}

	/**
	 * This is for adding intercepters.
	 * 
	 * @author 박승수 (21.01.12, DeviceResolverHandlerInterceptor added)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//for detecting which machine (pc or mobile) user using
		registry.addInterceptor(deviceResolverHandlerInterceptor());
		//refuse access when login info not exist
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/diagnose**")
														.addPathPatterns("/myinfo**")
														.addPathPatterns("/analysis**")
														.addPathPatterns("/contents**")
														.addPathPatterns("/survey-modify**")
														.addPathPatterns("/authManagement**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
						"Access-Control-Request-Headers", " Access-Control-Allow-Origin")
				.allowCredentials(true).maxAge(3600);
	}

	/**
	 * For detecting whether user is using mobile or pc. This is for not making
	 * other controllers for mobile.
	 * 
	 * @author 박승수 (21.01.12)
	 */
	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}
	
	/**
	 * To use Device detector in controller
	 * @author 박승수 (21.01.12)
	 */
	@Bean
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
		return new DeviceHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(deviceHandlerMethodArgumentResolver());
	}
	
	//파일업로드 관련
	@Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }

}