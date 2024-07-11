package libraries;

import com.kingmang.lazurite.libraries.lzr.utils.clipboard.clipboard;
import com.kingmang.lazurite.utils.Handler;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class ClipboardLibraryTest {

    @Test
    public void testSetClipboard() throws Exception {
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard();

            Long timestamp = System.currentTimeMillis();
            String code = """
                    using "lzr.utils.clipboard"
                                    
                    name = "Antoganist%s"
                    toSet = "my name is $name, every morning i..."
                                    
                    clipboard.set(toSet)
                    """.formatted(timestamp);

            Handler.runProgram(code);
            assertEquals(
                    "my name is Antoganist%s, every morning i...".formatted(timestamp),
                    clipboard.get().asString()
            );
        } catch (HeadlessException e) {
            System.out.println("No X11 DISPLAY variable was set,");
        }
    }
}
