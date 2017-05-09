package com.easy.reader.rest;

import com.easy.reader.api.ApplicationConfiguration;
import com.easy.reader.parser.BookParser;
import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.entity.Book;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Upload file test
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
public class BookUploadServiceTest {
    private static final Logger LOGGER = Logger.getLogger(BookUploadServiceTest.class);
    
    private static WebTarget target;
    @ArquillianResource
    private URL base;
    
    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(BookParser.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookDao.class.getPackage())
                .addPackage(BookUploadService.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebResource("xmlParserTest.xml")
                .addClass(ApplicationConfiguration.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    
        System.out.println(webArchive.toString(true));
        return webArchive;
    }
    
    @Before
    public void init() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "upload").toExternalForm()));
    }
    
    @Test
    public void testFb2FileUpload() {
        Response response = target.queryParam("fileName", "xmlParserTest.fb2").request()
                .post(Entity.entity(getClass().getResourceAsStream("/xmlParserTest.xml"), MediaType.APPLICATION_OCTET_STREAM));
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void testFileUploadWithWrongFileType() {
        Response response = target.queryParam("fileName", "wrongFile.pdf").request()
                .post(Entity.entity(getClass().getResourceAsStream("/xmlParserTest.xml"), MediaType.APPLICATION_OCTET_STREAM));
        Assert.assertEquals(Response.Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(), response.getStatus());
    }
}
