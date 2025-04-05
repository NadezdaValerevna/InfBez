import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class BrutForce {

    public static void clickAndType(int x, int y, String text) throws AWTException {
        Robot robot = new Robot();

        // Перемещаем курсор и кликаем
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);  // Задержка для надежности

        // Вводим текст
        typeString(robot, text);
    }

    private static void typeString(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            // Если символ в верхнем регистре или требует Shift (например, @, $ и т. д.)
            if (Character.isUpperCase(c) || isShiftRequired(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else {
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }

            robot.delay(20);  // Небольшая задержка между символами
        }
    }

    /**
     * Проверяет, требуется ли для символа нажатие Shift (например, для @, $ и т. д.).
     */
    private static boolean isShiftRequired(char c) {
        // Примеры символов, требующих Shift (можно расширить)
        String shiftChars = "~!@#$%^&*()_+{}|:\"<>?";
        return shiftChars.contains(String.valueOf(c));
    }


//    private static boolean Brut(Srting words){
//
//    }

}
