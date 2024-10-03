package ru.netolohy

import java.time.LocalTime
import java.util.Date

class ChatNotFoundException(message: String) : RuntimeException(message)

data class Message (
    val messageId : Int,
    // id сообщения
    var chatId : Int?,
    // id чата
    val ownerId : Int,
    val text : String,
    var date : Date? = Date(),
    var time : LocalTime? = LocalTime.now(),
    var isRead : Boolean = false,
)

object ChatService {
    var id : Int = 0
    var chats = mutableMapOf<Int, Message>()

    fun clear() {
        chats.clear()
    }

    fun createChat(message: Message)  {
        val tmp = message
        tmp.setDataTime(tmp)
        chats.put(id, tmp)
        id += 1
    }

    fun deleteChat(chatId: Int) {
        val tmp = (chats.filterValues { it.chatId != chatId })
        if (tmp.size == chats.size) throw ChatNotFoundException("Ошибка удаления чата, задан неверный Id чата")
        chats.clear()
        chats.putAll(tmp)
    }

    fun getChatList(ownerId: Int) : MutableMap<Int, Message> {
        // одбираем по владельцу
        val tmp = chats.filterValues { it.ownerId == ownerId }
        if (tmp.isEmpty()) throw ChatNotFoundException("Задан неверный Id чата при попытке создать список чатов")
        // записываем только с уникальными chatId
        var tmp1 = mutableMapOf<Int, Message>()
        tmp1.put(0, tmp.getValue(0))
        var currentChatId = tmp.getValue(0).chatId
        for ((key, value) in tmp) if (currentChatId !== tmp.getValue(key).chatId) {
            currentChatId = tmp.getValue(key).chatId
            tmp1.put(id, tmp.getValue(key))
            id++
        }
        return tmp1
    }

    //fun getUnreadChatsCount(ownerId: Int) : Int {
    //    val tmp = chats.filterValues { !it.isRead && it.ownerId == ownerId }
    //    return tmp.size
    // }

    fun getUnreadChatsCount(ownerId: Int) = chats.values.count {!it.isRead && it.ownerId == ownerId}

    fun getMessageList(ownerId: Int, count : Int) : List<Message> {
        val tmp = chats.filterValues { it.ownerId == ownerId }
        if (tmp.isEmpty()) throw ChatNotFoundException("Задан неверный Id чата при попытке получения списка сообщений")
        return chats.values.take(count).onEach { it.isRead = true }
    }

    fun addMessage(message: Message) {
        if (chats.filterValues { it.chatId == message.chatId }.isEmpty()) {
            throw ChatNotFoundException("Невозможно добавить сообщение, задан неверный Id чата")
        }
        else {
            val tmp = message
            tmp.setDataTime(tmp)
            chats.put(id, tmp)
            id += 1
        }
    }

    fun updateMessage(message: Message) {
        if (chats.filterValues { it.chatId == message.chatId }.isEmpty()) {
            throw ChatNotFoundException("Невозможно редактировать сообщение, задан неверный Id чата")
        }
        else {
            val tmp = message
            tmp.setDataTime(tmp)
            chats.put(id, tmp)
            id += 1
        }
    }

    fun deleteMessage(messageId : Int) {
        val tmp = (chats.filterValues { it.messageId != messageId })
        if (tmp.size == chats.size) throw ChatNotFoundException("Ошибка удаления сообщения, задан неверный Id чата")
        chats.clear()
        chats.putAll(tmp)
    }

    fun getLastMessages(chatId : Int) : String {
        if (chats.filterValues { it.chatId == chatId }.isEmpty()) {
            return "Невозможно получить последние сообщения, задан неверный Id чата"
        }
        else {
            val tmp = chats.filterValues { it.chatId == chatId }
            return tmp.firstNotNullOf {  }.toString()
        }
    }

    // установка даты и времени сообщения Extention function
    fun Message.setDataTime(message: Message) {
        message.date = Date()
        message.time = LocalTime.now()
    }

}

fun main() {

}
