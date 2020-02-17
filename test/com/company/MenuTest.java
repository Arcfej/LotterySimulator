package com.company;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    private static InputStream backup;

    private static String input = "10\n100\nArcfej\n1\n2\n3\n4\n5\n6\ny\nSecond\n2\n3\n4\n5\n6\n7\ny\nThird\n10\n9\n8\n7\n6\n5\nn";

    @BeforeAll
    static void beforeAll() {
        backup = System.in;
    }

    @AfterAll
   static void afterAll() {
        System.setIn(backup);
    }

    @Test
    void runLotteryFor1_10Times_withLotteryMax10() {
        String input = "10\n10\nArcfej\n1\n2\n3\n4\n5\n6\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }

    @Test
    void runLotteryFor1_100Times_withLotteryMax10() {
        String input = "10\n100\nArcfej\n1\n2\n3\n4\n5\n6\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }

    @Test
    void runLotteryFor3_10Times_withLotteryMax10() {
        String input = "10\n10\nArcfej\n1\n2\n3\n4\n5\n6\ny\nSecond\n2\n3\n4\n5\n6\n7\ny\nThird\n10\n9\n8\n7\n6\n5\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }

    @Test
    void runLotteryFor3_100Times_withLotteryMax10() {
        String input = "10\n100\nArcfej\n1\n2\n3\n4\n5\n6\ny\nSecond\n2\n3\n4\n5\n6\n7\ny\nThird\n10\n9\n8\n7\n6\n5\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }

    @Test
    void runLotteryFor1_10Times_withLotteryMax100() {
        String input = "100\n10\nArcfej\n1\n2\n3\n4\n5\n6\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }

    @Test
    void runLotteryFor1_100Times_withLotteryMax100() {
        String input = "100\n100\nArcfej\n1\n2\n3\n4\n5\n6\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }

    @Test
    void runLotteryFor3_10Times_withLotteryMax100() {
        String input = "100\n10\nArcfej\n1\n2\n3\n4\n5\n6\ny\nSecond\n2\n3\n4\n5\n6\n7\ny\nThird\n10\n9\n8\n7\n6\n5\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }

    @Test
    void runLotteryFor3_100Times_withLotteryMax100() {
        String input = "100\n100\nArcfej\n1\n2\n3\n4\n5\n6\ny\nSecond\n2\n3\n4\n5\n6\n7\ny\nThird\n10\n9\n8\n7\n6\n5\nn";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        (new Menu()).showMenu();
    }
}