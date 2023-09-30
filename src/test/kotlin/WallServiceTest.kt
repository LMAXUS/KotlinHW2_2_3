import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }
    @Test
    fun addPost() {
        val post = WallService.addPost(10, "Тестовый пост", )
        assertEquals(0, post.id)
    }
}