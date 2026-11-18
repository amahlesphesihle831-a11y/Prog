/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package regristrationloginapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
  import org.junit.jupiter.api.Test;


/**
 *
 * @author Amahle Hlongwane
 */
public class RegristrationLoginTest {
  

    @Test
    public void testMessageIDSuccess() {
        Message msg = new Message("123456789", "+27718630302", "Hey");
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testMessageIDFailure() {
        Message msg = new Message("123456789012345", "+27718630302", "Hello simphiwe");
        assertFalse(msg.checkMessageID());
    }

    @Test
    public void testMessageIDExactly10() {
        Message msg = new Message("1234567890", "+27718630302", "Hi");
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message("123456789", "+27718630302", "Hi simle");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message msg = new Message("123456789", "08575975389", "Hey");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code.",
                msg.checkRecipientCell());
    }

    @Test
    public void testRecipientTooFewDigits() {
        Message msg = new Message("123456789", "+27123", "Hey");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code.",
                msg.checkRecipientCell());
    }

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("123456789", "+27718630302", "Hi there");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "A".repeat(260);
        Message msg = new Message("123456789", "+27718630302", longMessage);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                msg.checkMessageLength());
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message("1234567890", "+27718630302", "Hi Mike tonight");
        assertEquals("12:0:HITONIGHT", msg.getMessageHash());
    }

    @Test
    public void testMessageHashSingleWord() {
        Message msg = new Message("1234567890", "+27718630302", "Hello");
        assertEquals("12:0:HELLOHELLO", msg.getMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message msg = new Message("1234567890", "+27718630302", "Test message");
        String result = msg.sendMessage("send");
        assertEquals("Message successfully sent", result);
    }

    @Test
    public void testSendDisregard() {
        Message msg = new Message("1234567890", "+27718630302", "Test message");
        String result = msg.sendMessage("disregard");
        assertEquals("Message disregarded. Nothing was sent or stored.", result);
    }

    @Test
    public void testSendInvalid() {
        Message msg = new Message("1234567890", "+27718630302", "Test message");
        String result = msg.sendMessage("delete");
        assertEquals("Invalid choice", result);
    }
}
    
   
    
    
}
