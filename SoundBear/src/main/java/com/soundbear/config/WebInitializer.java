package com.soundbear.config;

import java.util.Properties;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final long MAX_FILE_SIZE = 15728640; // 15MB : Max file size.
														// Beyond that size
														// spring will throw
														// exception.
	private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total
															// request size
															// containing Multi
															// part.

	private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after
														// which files will be
														// written to disk

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SpringWebConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/", "*.html", "*.pdf" };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setMultipartConfig(getMultipartConfigElement());
	}

	private MultipartConfigElement getMultipartConfigElement() {
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(null, MAX_FILE_SIZE,
				MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
		return multipartConfigElement;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {

		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

		// page and statusCode pare
		// you also can use method setStatusCodes(properties)
		resolver.addStatusCode("error", 300);

		// set views for exception
		Properties mapping = new Properties();
		mapping.setProperty("MultipartException", "error");
		resolver.setExceptionMappings(mapping);
		return resolver;
	}

	// @Bean
	// public SimpleMappingExceptionResolver
	// createSimpleMappingExceptionResolver() {
	// SimpleMappingExceptionResolver resolver = new
	// SimpleMappingExceptionResolver();
	// Properties errorMaps = new Properties();
	// errorMaps.setProperty("MultipartException", "error");
	// errorMaps.setProperty("NullPointerException", "error");
	// resolver.setExceptionMappings(errorMaps);
	// resolver.setDefaultErrorView("error");
	// resolver.setExceptionAttribute("exception");
	// return resolver;
	// }
}