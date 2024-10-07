import org.junit.Test

import org.junit.Assert.*
import ru.netolohy.Chat
import ru.netolohy.ChatNotFoundException
import ru.netolohy.ChatService
import ru.netolohy.Message

class ChatServiceTest {

    @Test //(expected = ChatNotFoundException::class)
    fun createChat() {
        ChatService.clear()
        val ms1 = Message(0, 10, "First Message")
        val result = ChatService.createChat(ms1)
        assertEquals(result, 0)
    }

    @Test (expected = ChatNotFoundException::class)
    fun deleteChat() {
        ChatService.clear()
        val ms1 = Message(0, 10, "First Message")
        val result = ChatService.createChat(ms1)
        ChatService.deleteChat(10)
    }

    @Test (expected = ChatNotFoundException::class)
    fun getMessageList() {
        ChatService.clear()
        val ms1 = Message(0, 10, "First Message")
        val chatId = ChatService.createChat(ms1)
        ChatService.getMessageList(100, 2)
        //
    }

    @Test
    fun updateMessage() {
        ChatService.clear()
        val ms1 = Message(0, 20, "First Message")
        ChatService.createChat(ms1)
        ChatService.updateMessage(ms1)
    }

    @Test (expected = ChatNotFoundException::class)
    fun deleteMessage() {
        ChatService.clear()
        val ms1 = Message(0, 20, "First Message")
        ChatService.createChat(ms1)
        ChatService.deleteChat(200)
    }
}