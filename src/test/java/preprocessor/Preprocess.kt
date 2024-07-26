package preprocessor

import com.kingmang.lazurite.parser.preprocessor.Preprocessor.preprocess
import org.junit.Assert
import org.junit.Test

class Preprocess {
    @Test
    fun testJIncludePreprocess() {
        val code = "#jInclude \"java.util.Stack\""
        val input = preprocess(code)
        val output = "using \"lzr.lang.reflection\"; Stack = JClass(\"java.util.Stack\")\n"
        Assert.assertEquals(input, output)
    }
}
