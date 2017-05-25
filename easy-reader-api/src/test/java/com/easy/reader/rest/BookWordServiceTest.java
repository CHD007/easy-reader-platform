package com.easy.reader.rest;

import com.easy.reader.api.ApplicationConfiguration;
import com.easy.reader.persistance.dao.BookWordDao;
import com.easy.reader.persistance.dto.BookWordDto;
import com.easy.reader.persistance.entity.BookWord;
import com.easy.reader.persistance.entity.Status;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
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
 * Test for BookWordService
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
@UsingDataSet({"datasets/books.yml", "datasets/words.yml", "datasets/users.yml", "datasets/bookWords.yml"})
public class BookWordServiceTest {
    private static final Logger LOGGER = Logger.getLogger(BookUploadServiceTest.class);
    
    private static WebTarget target;
    @ArquillianResource
    private URL base;
    
    @Inject
    private BookWordDao bookWordDao;
    
    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(BookWord.class.getPackage())
                .addPackage(BookWordDao.class.getPackage())
                .addPackage(BookWordService.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addClass(ApplicationConfiguration.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        
        System.out.println(webArchive.toString(true));
        return webArchive;
    }
    
    @Before
    public void init() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "book_words").toExternalForm()));
    }
    
    @Test
    public void testGetBookWord() {
        Response response = target.path("1").request().get();
        BookWordDto bookWordDto = response.readEntity(BookWordDto.class);
        Assert.assertEquals("Head First", bookWordDto.getBookName());
    }
    
    @Test
    public void testUpdateBookWordStatus() {
        Response response = target.path("1/update_status").request()
                .post(Entity.entity(Status.LEARNED.toString(), MediaType.TEXT_PLAIN));
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(Status.LEARNED, bookWordDao.findById(1L).getStatus());
    }
    
    @Test
    public void testGetWrongBook() {
        Response response = target.path("500").request().get();
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
