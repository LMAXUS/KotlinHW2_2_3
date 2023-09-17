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
        val post = WallService.add(Post(0, 376, 56, 1692333801, "Начало"))
        assertEquals(1, post.id)
    }

    @Test
    fun updateIdExists() {
        val post = WallService.add(Post(0, 376, 56, 1692333801, "Начало"))
        val result = WallService.update(Post(1, 376, 56, 1692333801, "Самое начало"))
        assertTrue(result)
    }
    @Test
    fun updateIdDosntExists() {
        val post = WallService.add(Post(0, 376, 56, 1692333801, "Начало"))
        val result = WallService.update(Post(0, 376, 56, 1692333801, "Начало"))
        assertFalse(result)
    }
}