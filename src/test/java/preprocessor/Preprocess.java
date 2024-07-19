package preprocessor;

import com.kingmang.lazurite.parser.preprocessor.Preprocessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Preprocess {

    @Test
    public void testJIncludePreprocess() {
        String code = "#jInclude \"java.util.Stack\"";
        String input = Preprocessor.preprocess(code);
        String output = "using \"lzr.reflection\"; Stack = JClass(\"java.util.Stack\")\n";
        assertEquals(input, output);
    }
}
