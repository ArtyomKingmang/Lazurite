package libraries

import com.kingmang.lazurite.libraries.lzr.utils.artify.artify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArtifyLibraryTest {

    @Test
    fun `test banner style`() {
        val result = artify.proceed("aBc009", "banner")
        assertEquals("""
               ###    ########   ######    #####     #####    #######  
              ## ##   ##     ## ##    ##  ##   ##   ##   ##  ##     ## 
             ##   ##  ##     ## ##       ##     ## ##     ## ##     ## 
            ##     ## ########  ##       ##     ## ##     ##  ######## 
            ######### ##     ## ##       ##     ## ##     ##        ## 
            ##     ## ##     ## ##    ##  ##   ##   ##   ##  ##     ## 
            ##     ## ########   ######    #####     #####    #######  
            
        """.trimIndent(), result)
    }

    @Test
    fun `test doom style`() {
        val result = artify.proceed("aBc009", "doom")
        assertEquals("""
                   ______        _____  _____  _____ 
                   | ___ \      |  _  ||  _  ||  _  |
              __ _ | |_/ /  ___ | |/' || |/' || |_| |
             / _` || ___ \ / __||  /| ||  /| |\____ |
            | (_| || |_/ /| (__ \ |_/ /\ |_/ /.___/ /
             \__,_|\____/  \___| \___/  \___/ \____/ 
                                                     
                                                     
            
        """.trimIndent(), result)
    }

}
