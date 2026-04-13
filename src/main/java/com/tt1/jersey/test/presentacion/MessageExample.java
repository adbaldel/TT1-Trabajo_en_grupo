package com.tt1.jersey.test.presentacion;

import com.tt1.jersey.test.modelo.Message;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

//Sets the path to base URL + /hello
@Path("/message")
public class MessageExample {

    // This method is called if TEXT_PLAIN is requested
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }

    // This method is called if XML is requested
    @GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    // This method is called if HTML is requested
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }

    // This method is called if JSON is requested
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message sayJsonHello() {
        return new Message("Hello", "Jersey");
    }

}