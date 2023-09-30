import java.lang.RuntimeException

class PostNotFoundException(message: String): RuntimeException(message)

sealed class Attachment(val type: String, val id: Int = 0)

data class Photo(
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
) : Attachment("video")

data class Graffiti(
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
) : Attachment("graffiti")

data class App(
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
) : Attachment("app")

data class Page(
    val groupId: Int = 0,
    val title: String = ""
) : Attachment("page")

data class Event(
    val time: Long = 0,
    val memberStatus: Int = 0,
    val text: String = ""
) : Attachment("event")

class Counter(){
    var qty = 0
    fun add(): Int {
        qty++
        return qty()
    }
    fun qty(): Int {
        return qty
    }
}

// Базовый класс для сообщений
open class Post(
    val id: Int = 0, // Идентификатор записи
    val fromId: Int, // Идентификатор автора записи
    val date: Long, // Дата публикации в формате unixtime
    var text: String, // Текст записи
    var attachments: Array<Attachment> = emptyArray<Attachment>(), // Вложения разного рода
    var deleted: Boolean = false, // Пост удален
)

// Класс для комментариев, наследуется от Post
class Comment(
    id: Int = 0, // Идентификатор записи
    fromId: Int, // Идентификатор автора записи
    date: Long, // Дата публикации в формате unixtime
    text: String, // Текст записи
    attachments: Array<Attachment> = emptyArray<Attachment>(), // Вложения разного рода
    val replyPostId: Int, // Идентификатор записи, в ответ на которую была добавлена текущая
): Post(id, fromId, date, text, attachments)

object WallService{
    private var posts = mutableListOf<Post>()
    private var comments = mutableListOf<Comment>()

    private fun postExists(id: Int){
        if (id >= posts.size) throw PostNotFoundException("Не существует поста с id: $id")
    }

    fun addPost(
        fromId: Int, // Идентификатор автора записи
        text: String, // Текст записи
        attachments: Array<Attachment> = emptyArray<Attachment>(), // Вложения разного рода
     ): Post{
            posts += Post(posts.size, fromId, System.currentTimeMillis(), text, attachments)
            return posts.last()
    }

    fun getPost(id: Int): Post{
        postExists(id)
        return posts[id]
    }

    fun deletePost(id: Int){
        postExists(id)
        posts[id].deleted = true
    }

    fun addComment(
        replyPostId: Int, // Идентификатор записи, в ответ на которую была добавлена текущая
        fromId: Int,  // Идентификатор автора записи
        text: String, // Текст записи
        attachments: Array<Attachment> = emptyArray<Attachment>() // Вложения разного рода
    ): Comment{
        postExists(replyPostId)
        comments += Comment(comments.size, fromId, System.currentTimeMillis(), text, attachments, replyPostId)
        return comments.last()
    }

    fun clear(){
        posts = mutableListOf<Post>()
        comments = mutableListOf<Comment>()
    }
}

fun main() {
    val post1 = WallService.addPost(1, "Первый пост")
    val post2 = WallService.addPost(15, "Второй пост")
    val post3 = WallService.addPost(15, "Третий пост")
    println(WallService.getPost(0).text)

    WallService.addComment(post1.id, 10, "Комментарий к первому посту")
    WallService.addComment(5, 10, "Комментарий к несуществующему посту")
}