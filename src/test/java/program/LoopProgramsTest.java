package program;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.console.output.impl.SystemOutput;
import com.kingmang.lazurite.utils.Handler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LoopProgramsTest {

    private String runProgram(String code) throws IOException {
        SystemOutput so = new SystemOutput();
        Console.useOutput(so);

        Handler.runProgram(code);
        return so.toString();
    }

    @Test
    public void forTest() throws IOException {
        String out = runProgram("""
                for (i = 1, i < 10, i++) {
                  print(i);
                }
                """);

        assertEquals("123456789", out);
    }

    @Test
    public void whileTest() throws IOException {
        String out = runProgram("""
                counter = 0
                while (true) {
                    print(counter);
                
                    if (counter == 10) {
                        break;
                    }
                
                    counter++;
                }
                """);

        assertEquals("012345678910", out);
    }

}
