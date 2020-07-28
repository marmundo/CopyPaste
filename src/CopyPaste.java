import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static java.awt.event.KeyEvent.VK_V;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class CopyPaste {

	File filenameOrigin;
	// Scanner myReader;
	Reader myReader;

	public CopyPaste(InputStream in) {
		myReader = new BufferedReader(new InputStreamReader(in));
		// myReader = new Scanner(in);
	}

	public boolean sendCaracterToClipBoard() {
		char c = readFileCaracter();
		if (c == 0) {
			
			return false;
		}
		String caracter = Character.toString(c);
		copy(caracter);
		try {
			paste();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public char readFileCaracter() {
		int r;
		char caracter = 0;
		try {
			if ((r = myReader.read()) != -1) 
				caracter = (char) r;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return caracter;
	}

	

	public static void copy(String caracter) {
		Clipboard clipboard = getSystemClipboard();
		clipboard.setContents(new StringSelection(caracter), null);
	}

	public static void paste() throws AWTException {
		Robot robot = new Robot();

		int controlKey = IS_OS_MAC ? VK_META : VK_CONTROL;
		robot.keyPress(controlKey);
		robot.keyPress(VK_V);
		robot.keyRelease(controlKey);
		robot.keyRelease(VK_V);
	}

	public static String get() throws Exception {
		Clipboard systemClipboard = getSystemClipboard();
		DataFlavor dataFlavor = DataFlavor.stringFlavor;

		if (systemClipboard.isDataFlavorAvailable(dataFlavor)) {
			Object text = systemClipboard.getData(dataFlavor);
			return (String) text;
		}

		return null;
	}

	private static Clipboard getSystemClipboard() {
		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		return defaultToolkit.getSystemClipboard();
	}
}
