package com.example.user.config.util;

import org.junit.jupiter.api.Test;

class Ase256UtilTest {


    @Test
    void encrypt() {
        String encrypt = Ase256Util.encrypt("Hello World");
        assertEquals(Ase256Util.decrypted(encrypt), "Hello World");
    }


}