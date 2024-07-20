package testutils

import com.kingmang.lazurite.exceptions.LzrException
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

fun assertLzrFails(expected: LzrException, block: () -> Unit) {
    val actual = assertFails(block)
    assertIs<LzrException>(actual)
    assertEquals(expected.type, actual.type)
    //assertEquals(expected.text, actual.text)
}

fun assertLzrTypeFails(text: String, block: () -> Unit) {
    assertLzrFails(LzrException("TypeException", text), block)
}

fun assertLzrTypeCastFails(origin: String, target: String, block: () -> Unit) {
    assertLzrFails(LzrException("TypeException", "Cannot cast $origin to $target"), block)
}