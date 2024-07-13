package program;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.console.output.impl.SystemOutput;
import com.kingmang.lazurite.utils.Handler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ExampleProgramsTest {

    private String runProgram(String code) throws IOException {
        SystemOutput so = new SystemOutput();
        Console.useOutput(so);

        Handler.runProgram(code);
        return so.toString();
    }

    @Test
    public void printTest() throws IOException {
        String out = runProgram("""
                a = 5
                hello = "Hello"
                world = "World"
                print("$hello $world!")
                """);

        assertEquals("Hello World!", out);
    }

    @Test
    public void printTest2() throws IOException {
        String out = runProgram("""
                a = 5
                b = 4
                c = 5 - 4
                print("$a - $b = (simple math) $a - $b == ${a - b}")
                """);
        assertEquals("5 - 4 = (simple math) 5 - 4 == 1", out);
    }

    @Test
    public void mathPlusTest() throws IOException {
        String out = runProgram("""
                a = 14
                b = 88
                c = a + b
                print(c)
                """);

        assertEquals("102", out);
    }

    @Test
    public void mathMinusTest() throws IOException {
        String out = runProgram("""
                a = 14
                b = 88
                c = a - b
                print(c)
                """);

        assertEquals("-74", out);
    }

    @Test
    public void mathDivideTest() throws IOException {
        String out = runProgram("""
                a = 1000
                b = 100
                c = a / b
                print(c)
                """);

        assertEquals("10", out);
    }

    @Test
    public void mathMultiplyTest() throws IOException {
        String out = runProgram("""
                a = 10
                b = 100
                c = a * b
                print(c)
                """);

        assertEquals("1000", out);
    }

    @Test
    public void functionTest() throws IOException {
        String out = runProgram("""
                func x(arg) {
                    return arg + 505
                }
                
                print(x(495) + 1)
                """);

        assertEquals("1001", out);
    }
}
