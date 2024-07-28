package preprocessor

import com.kingmang.lazurite.parser.preprocessor.Preprocessor.preprocess
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Preprocess {
    @Test
    fun testJIncludePreprocess() {
        val code = "#jInclude \"java.util.Stack\""
        val input = preprocess(code)
        val output = "using \"lzr.lang.reflection\"; Stack = JClass(\"java.util.Stack\")\n"
        Assertions.assertEquals(input, output)
    }
}
