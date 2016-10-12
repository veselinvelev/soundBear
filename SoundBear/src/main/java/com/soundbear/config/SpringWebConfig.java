package com.soundbear.config;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.soundbear.repository.PlaylistDAO;
import com.soundbear.repository.PlaylistRepository;
import com.soundbear.repository.SongDAO;
import com.soundbear.repository.SongRepository;
import com.soundbear.repository.UserRepository;

@Configuration
@EnableWebMvc
// @ComponentScan("com.soundbear.controller")
@EnableTransactionManagement // Transactions
@ComponentScan(basePackages = { "com.soundbear.controller", "com.soundbear.repository", "com.soundbear.utils" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
		registry.addResourceHandler("/pdfs/**").addResourceLocations("/static/pdf/");
		registry.addResourceHandler("/script/**").addResourceLocations("/static/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("/static/img/");
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/jsp/");
		resolver.setSuffix(".jsp");

		return resolver;
	}

	// localization configuration
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new GsonHttpMessageConverter());
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
		super.configureContentNegotiation(configurer);
	}

	// upload
	@Bean(name = "multipartResolver")
	public StandardServletMultipartResolver resolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
		changeInterceptor.setParamName("language");
		registry.addInterceptor(changeInterceptor);
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/soundbear?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		return dataSource;
	}

	// @Bean
	// public DataSource getDataSource() {
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	// dataSource.setUrl("jdbc:mysql://192.168.8.22:3306/hr?useSSL=false");
	// dataSource.setUsername("ittstudent");
	// dataSource.setPassword("ittstudent-123");
	//
	// return dataSource;
	// }

	@Bean
	public UserRepository getUserRepository() {
		return new UserRepository(getDataSource());
	}

	@Bean
	public SongDAO getSongRepository() {
		return new SongRepository(getDataSource(), transactionTemplate());
	}

	@Bean
	public PlaylistDAO getPlaylistRepository() {
		return new PlaylistRepository(getDataSource());
	}

	// transaction
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(getDataSource());
	}

	@Bean
	public TransactionTemplate transactionTemplate() {
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager());
		return transactionTemplate;
	}

	// Transactions

}
