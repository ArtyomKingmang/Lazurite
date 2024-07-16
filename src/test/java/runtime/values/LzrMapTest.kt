package runtime.values

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.runtime.values.*
import org.junit.Test
import testutils.assertLzrTypeCastFails
import java.util.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LzrMapTest {

    private val emptyMap = emptyMap<LzrValue, LzrValue>()
    private val emptyLzrMap = LzrMap(emptyMap)
    private val defaultMap = mapOf<LzrValue, LzrValue>(
        LzrString("k1") to LzrString("v1"),
        LzrString("k2") to LzrString("v2"),
        LzrString("k3") to LzrString("v3"),
    )
    private val defaultLzrMap = LzrMap(defaultMap)

    @Test
    fun ifPresentString() {
        defaultMap.entries.forEach { (key, value) ->
            assertEquals(true, defaultLzrMap.ifPresent(key.asString()) {
                assertEquals(value, it)
            })
        }
        assertEquals(false, defaultLzrMap.ifPresent("k211") {
            error("ifPresent should not call consumer when key not exists")
        })
    }

    @Test
    fun ifPresentLzrValue() {
        defaultMap.entries.forEach { (key, value) ->
            assertEquals(true, defaultLzrMap.ifPresent(key) {
                assertEquals(value, it)
            })
        }
        assertEquals(false, defaultLzrMap.ifPresent(LzrString("k211")) {
            error("ifPresent should not call consumer when key not exists")
        })
    }

    @Test
    fun toPairs() {
        val emptyExpected = emptyMap.map { LzrArray.of(it.key, it.value) }.toTypedArray<LzrValue>()
        val defaultExpected = defaultMap.map { LzrArray.of(it.key, it.value) }.toTypedArray<LzrValue>()
        assertContentEquals(emptyExpected, emptyLzrMap.toPairs().raw())
        assertContentEquals(defaultExpected, defaultLzrMap.toPairs().raw())
    }

    @Test
    fun getMap() {
        assertEquals(emptyMap, emptyLzrMap.getMap())
        assertEquals(defaultMap, defaultLzrMap.getMap())
    }

    @Test
    fun type() {
        assertEquals(emptyLzrMap.type(), Types.MAP)
    }

    @Test
    fun size() {
        assertEquals(0, emptyLzrMap.size())
        assertEquals(3, defaultLzrMap.size())
    }

    @Test
    fun containsKey() {
        defaultMap.keys.forEach {
            assertEquals(true, defaultLzrMap.containsKey(it))
        }
        assertEquals(false, defaultLzrMap.containsKey(LzrString("k191")))
    }

    @Test
    fun get() {
        defaultMap.entries.forEach { (key, value) ->
            assertEquals(value, defaultLzrMap[key])
        }
        assertEquals(null, defaultLzrMap[LzrString("k191")])
    }

    @Test
    fun setStrToValue() {
        defaultLzrMap["k12"] = LzrString("v12")
        assertEquals(LzrString("v12"), defaultLzrMap.get(LzrString("k12")))
    }

    @Test
    fun setStrToFunc() {
        val func = Function { LzrString("v14") }
        defaultLzrMap["k14"] = func
        val actual = defaultLzrMap[LzrString("k14")]
        actual as LzrFunction
        assertEquals(func, actual.value)
        assertEquals(LzrString("v14"), actual.value.execute())
    }

    @Test
    fun setValueToValue() {
        defaultLzrMap["k15"] = LzrNumber.ONE
        assertEquals(LzrNumber.ONE, defaultLzrMap.get(LzrString("k15")))
    }

    @Test
    fun raw() {
        assertEquals(emptyMap, emptyLzrMap.getMap())
        assertEquals(defaultMap, defaultLzrMap.getMap())
    }

    @Test
    fun asInt() {
        assertLzrTypeCastFails("map", "integer") {
            defaultLzrMap.asInt()
        }
    }

    @Test
    fun asNumber() {
        assertLzrTypeCastFails("map", "number") {
            defaultLzrMap.asNumber()
        }
    }

    @Test
    fun asString() {
        assertEquals(emptyMap.toString(), emptyLzrMap.asString())
        assertEquals(defaultMap.toString(), defaultLzrMap.asString())
    }

    @Test
    fun asArray() {
        assertContentEquals(intArrayOf(), emptyLzrMap.asArray())
        assertContentEquals(intArrayOf(), defaultLzrMap.asArray())
    }

    @Test
    operator fun iterator() {
        assertEquals(emptyMap.entries.toList(), emptyLzrMap.toList())
        assertEquals(defaultMap.entries.toList(), defaultLzrMap.toList())
    }

    @Test
    fun testHashCode() {
        assertEquals(37 * 5 + Objects.hashCode(emptyMap), emptyLzrMap.hashCode())
        assertEquals(37 * 5 + Objects.hashCode(defaultMap), defaultLzrMap.hashCode())
    }

    @Test
    fun testEquals() {
        assertEquals(LzrMap(emptyMap), emptyLzrMap)
        assertEquals(LzrMap(defaultMap), defaultLzrMap)
    }

    @Test
    fun compareTo() {
        val number = LzrNumber.ZERO
        val defaultStr = LzrString(defaultLzrMap.asString())
        assertEquals(1, defaultLzrMap.compareTo(emptyLzrMap))
        assertEquals(-1, emptyLzrMap.compareTo(defaultLzrMap))
        assertEquals(75, defaultLzrMap.compareTo(number))
        assertEquals(-75, number.compareTo(defaultLzrMap))
        assertEquals(0, defaultLzrMap.compareTo(defaultStr))
        assertEquals(0, defaultStr.compareTo(defaultLzrMap))
    }

    @Test
    fun testToString() {
        assertEquals(emptyLzrMap.asString(), emptyLzrMap.toString())
        assertEquals(defaultLzrMap.asString(), defaultLzrMap.toString())
    }

    @Test
    fun merge() {
        val map1 = (0..5).associate<Int, LzrValue, LzrValue> { LzrString("k$it") to LzrString("v$it") }
        val map2 = (10..50).associate<Int, LzrValue, LzrValue> { LzrString("k$it") to LzrString("v$it") }
        val actual = LzrMap.merge(LzrMap(map1), LzrMap(map2))
        val expected = LinkedHashMap<LzrValue, LzrValue>().run {
            putAll(map1)
            putAll(map2)
            LzrMap(this)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun emptyConst() {
        assertEquals(0, LzrMap.EMPTY.size())
        assertEquals(emptyMap(), LzrMap.EMPTY.getMap())
    }
}