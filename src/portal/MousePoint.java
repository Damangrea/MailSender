/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author damangrea
 */
public class MousePoint {
    public static void main(String[] args) throws AWTException, InterruptedException {
        /*
        X:74
        Y:267
        */
        Point point;
        Robot robot=new Robot();
        String text = "Dian";
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        int x,y;
        robot.mouseMove(78, 267);
        Thread.sleep(1000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Thread.sleep(1000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_META);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(2000);
        
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(100);
        
        text = "Test";
        stringSelection = new StringSelection(text);
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        Thread.sleep(1000);
        
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_META);
        robot.keyRelease(KeyEvent.VK_V);

//        for (int i = 0; i < 100; i++) {
//            point= MouseInfo.getPointerInfo().getLocation();
//            x=point.x;
//            y=point.y;
//            robot.mouseMove(x, y+5);
//            robot.mousePress(InputEvent.BUTTON1_MASK);
//            robot.mouseRelease(InputEvent.BUTTON1_MASK);
//            robot.keyPress(KeyEvent.VK_META);
//            robot.keyPress(KeyEvent.VK_V);
//            robot.keyRelease(KeyEvent.VK_META);
//            robot.keyRelease(KeyEvent.VK_V);
//            
//            Thread.sleep(100);
//        }
        
    }
}
