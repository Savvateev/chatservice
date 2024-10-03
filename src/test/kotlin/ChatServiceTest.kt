import org.junit.Test

import org.junit.Assert.*
import ru.netolohy.ChatNotFoundException
import ru.netolohy.ChatService
import ru.netolohy.Message
import java.time.LocalTime
import java.util.*

class ChatServiceTest {

    @Test
    fun createChat() {
        val messageId = 1
        val ownerId : Int = 10
        val text : String = "First message from 10"
        val message = Message (messageId, 0, ownerId, text)
        ChatService.clear()
        ChatService.createChat(message)
        //val result = ChatService.getChatList(10).toString()
    }

    @Test (expected = ChatNotFoundException::class)
    fun deleteChat() {
        val messageId = 1
        val ownerId : Int = 10
        val text : String = "First message from 10"
        val message = Message (messageId, 0, ownerId, text)
        ChatService.clear()
        ChatService.createChat(message)
        ChatService.deleteChat(10)
    }

    @Test (expected = ChatNotFoundException::class)
    fun getChatList() {
        val messageId1 = 1
        val ownerId1 : Int = 10
        val text1 : String = "First message from 10"
        val ms1 = Message (messageId1, 0, ownerId1, text1)
        val messageId2 = 2
        val ownerId2 : Int = 3
        val text2 : String = "First message from 3"
        val ms2 = Message (messageId2, 1, ownerId2, text2)
        val messageId3 = 3
        val ownerId3 : Int = 10
        val text3 : String = "message from 10"
        val ms3 = Message (messageId3, 2, ownerId3, text3)
        ChatService.clear()
        ChatService.createChat(ms1)
        ChatService.createChat(ms2)
        ChatService.createChat(ms3)
        val result = ChatService.getChatList(100)
        //assertEquals(result.isEmpty(), true)
    }

    @Test
    fun getUnreadChatsCount() {
        val messageId1 = 1
        val ownerId1 : Int = 10
        val text1 : String = "First message from 10"
        val ms1 = Message (messageId1, 0, ownerId1, text1)
        val messageId2 = 2
        val ownerId2 : Int = 3
        val text2 : String = "First message from 3"
        val ms2 = Message (messageId2, 1, ownerId2, text2)
        val messageId3 = 3
        val ownerId3 : Int = 10
        val text3 : String = "message from 10"
        val ms3 = Message (messageId3, 2, ownerId3, text3)
        ChatService.clear()
        ChatService.createChat(ms1)
        ChatService.createChat(ms2)
        ChatService.createChat(ms3)
        val result = ChatService.getUnreadChatsCount(10)
        assertEquals(result,2)
    }

    @Test (expected = ChatNotFoundException::class)
    fun getMessageList() {
        val messageId1 = 1
        val ownerId1 : Int = 10
        val text1 : String = "First message from 10"
        val ms1 = Message (messageId1, 0, ownerId1, text1)
        val messageId2 = 2
        val ownerId2 : Int = 3
        val text2 : String = "First message from 3"
        val ms2 = Message (messageId2, 1, ownerId2, text2)
        val messageId3 = 3
        val ownerId3 : Int = 10
        val text3 : String = "message from 10"
        val ms3 = Message (messageId3, 2, ownerId3, text3)
        ChatService.clear()
        ChatService.createChat(ms1)
        ChatService.createChat(ms2)
        ChatService.createChat(ms3)
        val result = ChatService.getMessageList(11, 2)
    }

    @Test (expected = ChatNotFoundException::class)
    fun addMessage() {
        val messageId1 = 1
        val ownerId1 : Int = 10
        val text1 : String = "First message from 10"
        val ms1 = Message (messageId1, 12, ownerId1, text1)
        ChatService.addMessage(ms1)
    }

    @Test (expected = ChatNotFoundException::class)
    fun updateMessage() {
        val messageId1 = 1
        val ownerId1 : Int = 10
        val text1 : String = "First message from 10"
        val ms1 = Message (messageId1, 12, ownerId1, text1)
        val messageId2 = 2
        val ownerId2 : Int = 3
        val text2 : String = "First message from 3"
        val ms2 = Message (messageId2, 1, ownerId2, text2)
        ChatService.clear()
        ChatService.createChat(ms1)
        ChatService.updateMessage(ms2)
    }

    @Test (expected = ChatNotFoundException::class)
    fun deleteMessage() {
        val messageId1 = 1
        val ownerId1 : Int = 10
        val text1 : String = "First message from 10"
        val ms1 = Message (messageId1, 12, ownerId1, text1)
        val messageId2 = 2
        val ownerId2 : Int = 3
        val text2 : String = "First message from 3"
        val ms2 = Message (messageId2, 1, ownerId2, text2)
        ChatService.clear()
        ChatService.createChat(ms1)
        ChatService.deleteMessage(123)
    }

    @Test
    fun getLastMessages() {
        val messageId1 = 1
        val ownerId1 : Int = 10
        val text1 : String = "First message from 10"
        val ms1 = Message (messageId1, 0, ownerId1, text1)
        val messageId2 = 2
        val ownerId2 : Int = 3
        val text2 : String = "First message from 3"
        val ms2 = Message (messageId2, 0, ownerId2, text2)
        val messageId3 = 3
        val ownerId3 : Int = 10
        val text3 : String = "message from 10"
        val ms3 = Message (messageId3, 0, ownerId3, text3)
        ChatService.clear()
        ChatService.createChat(ms1)
        ChatService.addMessage(ms2)
        ChatService.addMessage(ms3)
        val result = ChatService.getLastMessages(56)
        assertEquals(result,"Невозможно получить последние сообщения, задан неверный Id чата")
    }
}