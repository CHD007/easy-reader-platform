package com.easy.reader.rest;

import com.easy.reader.api.ApplicationConfiguration;
import com.easy.reader.parser.BookParser;
import com.easy.reader.persistance.dao.BookDao;
import com.easy.reader.persistance.entity.Book;
import com.easy.reader.persistance.entity.BookWord;
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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Upload file test
 * @author dchernyshov
 */
@RunWith(Arquillian.class)
public class BookServiceTest {
    private static WebTarget target;
    @ArquillianResource
    private URL base;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(BookParser.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookDao.class.getPackage())
                .addPackage(BookService.class.getPackage())
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
        target = client.target(URI.create(new URL(base, "books").toExternalForm()));
    }
    
    @Test
    @UsingDataSet("books.yml")
    public void testGetBook() {
        Book book = target.path("1").request().get(Book.class);
        Assert.assertEquals("Head First", book.getBookName());
    }
    
    @Test
    @UsingDataSet("books.yml")
    public void testGetBooks() {
        Response response = target.request().get();
        List<Book> books = response.readEntity(new GenericType<List<Book>>() {});
        Assert.assertEquals(1, books.size());
    }

    @Test
    @Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
    @UsingDataSet({"datasets/books.yml", "datasets/words.yml", "datasets/users.yml", "datasets/bookWords.yml"})
    public void testGetBookWordsForBook() {
        Response response = target.path("1/bookWords").request().get();
        List<BookWord> bookWords = response.readEntity(new GenericType<List<BookWord>>() {});
        Assert.assertEquals(4, bookWords.size());
    }
}
