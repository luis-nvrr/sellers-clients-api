package com.meli.sellerapi.domain.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("should return user not found exception with message")
    public void throwException() {
        String message = "user not found";
        Exception userNotFoundException = new UserNotFoundException(message);

        assertEquals(UserNotFoundException.class, userNotFoundException.getClass());
        assertEquals(message, userNotFoundException.getMessage());

    }
}