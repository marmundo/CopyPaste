import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener {
	static CopyPaste copypaste;

	public static void main(String[] args) {

		String fileName=args[0];
		InputStream in;
		try {
			in = new FileInputStream(new File(fileName));
//			InputStream in = Main.class.getResourceAsStream(fileName);
//			InputStream in = Main.class.getResourceAsStream("texto.txt");
			copypaste = new CopyPaste(in);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			// logger.error(e.getMessage(), e);
			System.exit(-1);
		}
		
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);

		GlobalScreen.addNativeKeyListener(new Main());
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode()==NativeKeyEvent.VC_RIGHT){
			copypaste.sendCaracterToClipBoard();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

}
