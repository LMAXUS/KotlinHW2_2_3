import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }
    @Test
    fun add() {
        val post = WallService.add(Post(0, 376, 56, 1692333801, "Начало", 12, "regular", true, true, true, Comments(), Likes()))
        assertEquals(1, post.id)
    }

    @Test
    fun updateIdExists() {
        val post = WallService.add(Post(0, 376, 56, 1692333801, "Начало", 12, "regular", true, true, true, Comments(), Likes()))
        val result = WallService.update(Post(1, 376, 56, 1692333801, "Самое начало", 12, "regular", true, true, true, Comments(), Likes()))
        assertEquals(true, result)
    }
    @Test
    fun updateIdDosntExists() {
        val post = WallService.add(Post(0, 376, 56, 1692333801, "Начало", 12, "regular", true, true, true, Comments(), Likes()))
        val result = WallService.update(Post(10, 376, 56, 1692333801, "Самое начало", 12, "regular", true, true, true, Comments(), Likes()))
        assertEquals(false, result)
    }
}