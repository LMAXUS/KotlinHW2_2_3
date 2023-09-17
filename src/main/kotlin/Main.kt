interface Attachment{
    val type: String
}

data class Photo(
    val id: Int = 0,
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
)

data class AttachmentPhoto(val photo: Photo) : Attachment{
    override val type: String = "photo"
}

data class Graffiti(
    val id: Int = 0,
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
)

data class AttachmentGraffiti(val graffiti: Graffiti) : Attachment{
    override val type: String = "graffiti"
}

data class App(
    val id: Int = 0,
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
)

data class AttachmentApp(val app: App) : Attachment{
    override val type: String = "app"
}

data class Page(
    val id: Int = 0,
    val groupId: Int = 0,
    val title: String = ""
)

data class AttachmentPage(val page: Page) : Attachment{
    override val type: String = "page"
}

data class Event(
    val id: Int = 0,
    val time: Long = 0,
    val memberStatus: Int = 0,
    val text: String = ""
)

data class AttachmentEvent(val event: Event) : Attachment{
    override val type: String = "event"
}

data class Counter(var id: Int = 0){
    fun add(): Int {
        id++
        return size()
    }
    fun size(): Int {
        return id
    }
}

class Geo(
    type: String = "",
    coordinates: String = ""
)

data class Post(
    var id: Int = 0, // Идентификатор записи
    val ownerId: Int, // Идентификатор владельца стены, на которой размещена запись
    val fromId: Int, // Идентификатор автора записи
    val date: Long, // Дата публикации в формате unixtime
    val text: String, // Текст записи
    val attachments: Array<Attachment> = emptyArray<Attachment>(),
    val createdBy: Int = 0,
    val replyOwnerId: Int = 0, // Идентификатор владельца записи, в ответ на которую была добавлена текущая
    val reply_post_id: Int = 0, // Идентификатор записи, в ответ на которую была добавлена текущая
    val friendsOnly: Boolean = false, // True, если запись была оставлена "Только для друзей"
    val comments: Counter = Counter(), // Информация о комментариях к записи
    val likes: Counter? = null,
    val reposts: Counter = Counter(),
    val views:  Counter = Counter(),
    val copyright: String = "",
    val post_type: String = "post",
    val postSource: String = "",
    val geo: Geo? = null,
    val singerId: Int = 0,
    val can_pin: Boolean = false,
    val can_delete: Boolean = false,
    val can_edit: Boolean = false,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int=0,
)

object WallService{
    private var posts = emptyArray<Post>()
    private var id = 0

    fun add(post: Post): Post{
        posts += post.copy(
            id = ++id,
            comments = post.comments.copy(),
            likes = post.likes?.copy(),
            reposts = post.reposts.copy(),
            views = post.views.copy(),
        )
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for((index, postToUpdate) in posts.withIndex()){
            if(post.id == postToUpdate.id){
                posts[index] = post.copy(
                    comments = post.comments.copy(),
                    likes = post.likes?.copy(),
                    reposts = post.reposts.copy(),
                    views = post.views.copy(),
                    )
                return true
            }
        }
        return false
    }

    fun getById(id: Int): Any{
        for(post in posts){
            if(post.id == id){
                return post
            }
        }
        return false
    }

    fun clear(){
        posts = emptyArray()
        id = 0
    }
}

fun main() {
    val post_test = Post(1, 376, 56, 1692333801, "Начало", arrayOf(AttachmentEvent(Event()), AttachmentPhoto(Photo())), likes = Counter(100))
    val post1 = WallService.add(post_test).also { println(it) }
    println(post1.likes?.add())
    println(post1.likes?.add())
    println(post1.likes?.add())
    println(post1.likes?.size())

    val post2 = WallService.add(Post(2, 34, 67, 1692333801, "Начало2")).also { println(it) }
    println(post2.likes?.add())
}