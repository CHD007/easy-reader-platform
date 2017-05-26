package com.easy.reader.api;

import com.easy.reader.exportFiles.services.ExportService;
import com.easy.reader.filters.CorsFilter;
import com.easy.reader.rest.BookService;
import com.easy.reader.rest.BookUploadService;
import com.easy.reader.rest.BookWordService;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class ApplicationConfiguration extends Application {
    public ApplicationConfiguration() {
    }

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
                BookService.class,
                CorsFilter.class,
                BookWordService.class,
                BookUploadService.class,
                MultiPartFeature.class,
                ExportService.class
        ));
    }
}
