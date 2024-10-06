package ru.netolohy

import java.time.LocalTime
import java.util.Date

class ChatNotFoundException(message: String) : RuntimeException(message)

data class Message(
    val messageId: Int,
    // id сообщения
    val ownerId: Int,
    val text: String,
    var date : Date = Date(),
    var time : LocalTime = LocalTime.now(),
    var isRead: Boolean = false,
)

data class Chat(val messages: MutableList<Message> = mutableListOf())

object ChatService {
    private var chatId: Int = 0
    private var chats = mutableMapOf<Int, Chat>()

    fun clear() {
        chats.clear()
        chatId = 0
    }

    fun createChat(message: Message): Int {
        val chat = Chat()
        chat.messages.add(message)
        chats[chatId] = chat
        chatId += 1
        return chatId - 1
    }

    fun deleteChat(chatId: Int) {
        if (!chats.containsKey(chatId)) throw ChatNotFoundException("Ошибка удаления чата, задан неверный Id чата")
        else chats.remove(chatId)
    }

    fun getChatList(ownerId: Int) = chats
        .filterValues { it.messages.any { it.ownerId == ownerId } }

    fun getUnreadChatsCount(ownerId: Int) =
        chats.values
            .count { chat -> chat.messages.any { !it.isRead && it.ownerId == ownerId } }

    fun getMessageList(chatId: Int, count: Int): List<Message> {
        val chat = chats[chatId] ?: throw ChatNotFoundException("неверный ownerId")
        return chat.messages
            .takeLast(count)
            .onEach { it.isRead = true }
    }

    fun addMessage(chatId: Int, message: Message) {
        if (!chats.containsKey(chatId)) createChat(message)
        else {
            val chat = chats[chatId]
            if (chat != null) {
                chat.messages += message
                chats[chatId] = chat
            }
        }
    }

    fun updateMessage(message: Message) {
        chats.forEach { entry ->
            if (entry.value.messages.any() { it.messageId == message.messageId }) {
                val chat = chats[entry.key]
                if (chat != null) {
                    chat.messages.forEach { entry1 ->
                        if (entry1.messageId == message.messageId) {
                            chat.messages[entry1.messageId] = message
                        }
                    }
                    chats[chatId] = chat
                }
            } else throw ChatNotFoundException("невозможно сохранить сообщение неверный messageId")
        }
    }

    fun deleteMessage(messageId: Int) {
        var flag = false
        chats.forEach { entry ->
            entry.value.messages.forEach { ms ->
                if (ms.messageId == messageId) {
                    val chat = chats.get(entry.key)!!
                    chat.messages.removeAt(ms.messageId)
                    chats.replace(entry.key, chat)
                    flag = true
                }
            }
        }
        if (!flag) throw ChatNotFoundException("Ошибка удаления сообщения, задан неверный Id чата")
    }

    fun getLastMessages() = chats.values.map { it.messages.lastOrNull()?.text ?: "нет сообщений" }

    fun printChats() {
        chats.forEach { entry -> println("$entry.value ") }
    }
}

fun main() {
    val ms1 = Message(0, 10, "First Message")
    val ms2 = Message(1, 10, "Second Message")
    val ms3 = Message(2, 4, "Hi")
    val ms4 = Message(3, 10, "Hello !")
    val ms5 = Message(4, 4, "How are you ?")
    val ms6 = Message(5, 4, "How do you do ?")
    val ms7 = Message(6, 3, "What about learning ?")
    val ms8 = Message(7, 3, "is bad")
    val ms9 = Message(8, 2, "Olga at home ?")
    val ms10 = Message(9, 2, "She is gone")
    val ms11 = Message(9, 2, "Yes")
    var chatId = ChatService.createChat(ms1)
    ChatService.addMessage(chatId, ms2)
    ChatService.addMessage(chatId, ms4)
    chatId = ChatService.createChat(ms3)
    ChatService.addMessage(chatId, ms5)
    ChatService.addMessage(chatId, ms6)
    chatId = ChatService.createChat(ms7)
    ChatService.addMessage(chatId, ms8)
    chatId = ChatService.createChat(ms9)
    ChatService.addMessage(chatId, ms10)
    // println(ChatService.getChatList(3))
    // ChatService.deleteChat(0)
    // println(ChatService.getUnreadChatsCount(10))
    // println(ChatService.getMessageList(0, 3))
    // ChatService.printChats()
    ChatService.deleteMessage(1)
    // ChatService.printChats()
    println()
    println(ChatService.getLastMessages())
}
