package runtime

import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VariablesTest {
    companion object {
        private val VAR_TRUE = Var("true", LzrNumber.ONE)
        private val VAR_FALSE = Var("false", LzrNumber.ZERO)

        private val VAR1 = Var("var1", LzrNumber.of(1000))
        private val VAR2 = Var("var2", LzrNumber.of(-1000))
    }

    @BeforeEach
    fun resetVariablesState() {
        Variables.clear()
    }

    @Test
    fun `initial state Expect valid boolean`() {
        assertValidBoolean()
    }

    @Test
    fun `get not existed variable on initial state Expect zero`() {
        assertVariableNotExists(VAR1)
    }

    @Test
    fun `clear Expect valid boolean`() {
        Variables.clear()
        assertValidBoolean()
    }

    @Test
    fun `push Expect valid boolean`() {
        Variables.push()
        assertValidBoolean()
    }

    @Test
    fun `pop Expect valid boolean`() {
        Variables.pop()
        assertValidBoolean()
    }

    @Test
    fun `push and pop Expect valid boolean`() {
        Variables.push()
        Variables.pop()
        assertValidBoolean()
    }

    @Test
    fun `define Expect exists`() {
        define(VAR1)
        assertVariableExists(VAR1)
    }

    @Test
    fun `define and remove Expect not exists`() {
        define(VAR1)
        remove(VAR1)
        assertVariableNotExists(VAR1)
    }

    @Test
    fun `push and define Expect exists`() {
        Variables.push()
        define(VAR1)
        assertVariableExists(VAR1)
    }

    @Test
    fun `push and define and remove Expect not exists`() {
        Variables.push()
        define(VAR1)
        remove(VAR1)
        assertVariableNotExists(VAR1)
    }

    @Test
    fun `push and define and pop Expect not exists`() {
        Variables.push()
        define(VAR1)
        Variables.pop()
        assertVariableNotExists(VAR1)
    }

    @Test
    fun `define list of vars Expect exists all`() {
        Variables.push()
        define(VAR1)
        Variables.push()
        define(VAR2)
        assertValidBoolean()
        assertVariableExists(VAR1)
        assertVariableExists(VAR2)
    }

    @Test
    fun `define list of vars and remove var1 Expect exists all Except var1`() {
        Variables.push()
        define(VAR1)
        Variables.push()
        define(VAR2)
        remove(VAR1)
        assertValidBoolean()
        assertVariableNotExists(VAR1)
        assertVariableExists(VAR2)
    }

    @Test
    fun `define list of vars and set wrong values Expect valid wrong values`() {
        val wrongTrue = VAR_TRUE.copy(value = LzrNumber.MINUS_ONE)
        val wrongFalse = VAR_FALSE.copy(value = LzrNumber.ONE)
        val wrongVar1 = VAR1.copy(value = LzrNumber.of(1234))
        val wrongVar2 = VAR2.copy(value = LzrNumber.of(-1234))
        Variables.push()
        define(VAR1)
        Variables.push()
        define(VAR2)
        // let's set the wrong values
        set(wrongTrue)
        set(wrongFalse)
        set(wrongVar1)
        set(wrongVar2)
        assertVariableExists(wrongTrue)
        assertVariableExists(wrongFalse)
        assertVariableExists(wrongVar1)
        assertVariableExists(wrongVar2)
    }

    private fun set(variable: Var) {
        Variables.set(variable.key, variable.value)
    }

    private fun define(variable: Var) {
        Variables.define(variable.key, variable.value)
    }

    private fun remove(variable: Var) {
        Variables.remove(variable.key)
    }

    private fun assertValidBoolean() {
        assertVariableExists(VAR_TRUE)
        assertVariableExists(VAR_FALSE)
    }

    private fun assertVariableExists(variable: Var) {
        assertEquals(true, Variables.isExists(variable.key), "assertVariableExists:isExists:${variable.key}")
        assertEquals(variable.value, Variables.get(variable.key), "assertVariableExists:get:${variable.key}")
    }

    private fun assertVariableNotExists(variable: Var) {
        assertEquals(false, Variables.isExists(variable.key), "assertVariableNotExists:isExists:${variable.key}")
        assertEquals(LzrNumber.ZERO, Variables.get(variable.key), "assertVariableNotExists:get:${variable.key}")
    }

    private data class Var(
        val key: String,
        val value: LzrValue
    )
}