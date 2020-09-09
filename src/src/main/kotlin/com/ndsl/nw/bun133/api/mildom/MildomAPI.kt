@file:Suppress("SpellCheckingInspection")

package com.ndsl.nw.bun133.api.mildom

import com.google.gson.Gson
import com.ndsl.nw.bun133.api.mildom.jsons.*
import com.ndsl.nw.bun133.websocket.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.random.Random
import kotlin.reflect.KClass

const val MILDOM_WS: String = "wss://jp-room1.mildom.com/?roomId="
const val GIFT_WS: String = "https://cloudac.mildom.com/nonolive/gappserv/gift/find"

class MildomAPI(var id: Int) : WebSocketClient(URI("$MILDOM_WS$id")) {
    companion object {
        const val default_user_agent = "Mozilla/5.0 (Windows; U; Win98; en-US; rv:0.9.2) Gecko/20010725 Netscape6/6.1"
    }

    override fun onOpen(handshakedata: ServerHandshake?) {
        println("[MildomAPI]Opened")
        val req = genInitReq()
        println("Req:")
        println(req)
        send(req)
        println("[MildomAPI]Init Req sended")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("[MildomAPI]Closed")
    }

    override fun onError(ex: Exception?) {
        println("[MildomAPI]Error Occoured")
        throw ex!!
    }

    override fun onMessage(message: String?) {
        println("[MildomAPI]Respond:${message!!}")
        when (MildomJsonWorker.getType(message!!)) {
            MildomResponceType.chat -> {
                println("[MildomAPI]onChat")
                println("[MildomAPI]Chat:${MildomJsonWorker.getAsChat(message).msg}")
            }
            MildomResponceType.add -> {
                println("[MildomAPI]onAdd");println("[MildomAPI]UserID:${MildomJsonWorker.getAsAdd(message).userId}")
            }
            MildomResponceType.userCount -> {
                println("[MildomAPI]onUserCount");println("[MildomAPI]NowViewers:${MildomJsonWorker.getAsCount(message).userCount}")
            }
            MildomResponceType.enterRoom -> {
                println("[MildomAPI]enterRoom");println("[MildomAPI]UserCount:${MildomJsonWorker.getAsEnterRoom(message).userCount}")
            }
            MildomResponceType.runNotify -> {
                println("[MildomAPI]runNotify");println("[MildomAPI]Command:${MildomJsonWorker.getAsRunCmd(message).runCmd}")
            }
            MildomResponceType.gift -> {
                val details:onGift = MildomJsonWorker.getAsGift(message)
                println("[MildomAPI]onGift")
                println("[MildomAPI]Sender:${details.userName}(ID:${details.userId})")
                println("[MildomAPI]Gift:ID:${details.giftId}")
            }
            MildomResponceType.recallMsg -> {
                var details:onRecallMsg = MildomJsonWorker.getAsRecallMsg(message)
                println("[MildomAPI]Message was recalled.")
                println("[MildomAPI]MesId:${details.msgId}")
                println("[MildomAPI]UserID:${details.userId}")
            }
        }
    }

    private fun genInitReq(): String {
        val guestId = genGuestID()
        val nonopara = "fr=web`sfr=pc`devi=OS X 10.15.6 64-bit`la=ja`gid=$guestId`na=Japan`loc=Japan|Tokyo`clu=aws_japan`wh=1920*1080`rtm=${MildomDate().get()}`ua=$default_user_agent`aid=$id`live_type=2`live_subtype=2`isHomePage=false"
        return "{\n" +
                "\"userId\": 0,\n" +
                "\"level\": 1,\n" +
                "\"userName\": \"${genUserName()}\",\n" +
                "\"guestId\": \"${guestId}\",\n" +
                "\"nonopara\": \"$nonopara\",\n" +
                "\"roomId\": $id,\n" +
                "\"cmd\":\"enterRoom\",\n" +
                "\"reConnect\":1,\n" +
                "\"nobleLevel\":0,\n" +
                "\"avatarDecortaion\":0,\n" +
                "\"enterroomEffect\":0,\n" +
                "\"nobleClose\":0,\n" +
                "\"nobleSeatClose\":0,\n" +
                "\"reqId\":1\n" +
                "}"
    }

    /**
     * Memos
     */
    val UserNamePatten: Pattern = Pattern.compile("^guest[0123456789]{6}")
    val GuestIDPatten: Pattern = Pattern.compile("^pc-gp-[abcdef0123456789]{4}-[abcdef0123456789]{4}-[abcdef0123456789]{4}-[abcdef0123456789]{12}")
    val NumberSet: Array<String> = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    val AlphaSet: Array<String> = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    private fun genUserName(): String {
        return "guest" + randomOf(NumberSet, 6)
    }

    private fun genGuestID(): String {
        return "pc-gp-" +
                "${randomOf(AlphaSet, 8)}-" +
                "${randomOf(AlphaSet, 4)}-" +
                "${randomOf(AlphaSet, 4)}-" +
                "${randomOf(AlphaSet, 4)}-" +
                randomOf(AlphaSet, 12)
    }
}

fun randomOf(sets: Array<String>): String {
    return sets[Random.nextInt(sets.size)]
}

fun randomOf(sets: Array<String>, length: Int): String {
    var s = ""
    for (i in 0 until length)
        s += randomOf(sets)
    return s
}

class MildomDate {
    var cal: Calendar = Calendar.getInstance()
    fun get(): String {
//        return "${cal.get(Calendar.YEAR)}-" +
//                "${cal.get(Calendar.MONTH)}-" +
//                "${cal.get(Calendar.DATE)}T" +
//                "${cal.get(Calendar.HOUR)}:" +
//                "${cal.get(Calendar.MINUTE)}:" +
//                "${cal.get(Calendar.SECOND)}." +
//                "${cal.get(Calendar.MILLISECOND)}Z"
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(Date())
    }
}

enum class MildomResponceType(var jsonClass: KClass<*>) {
    add(onAdd::class),
    chat(onChat::class),
    userCount(onUserCount::class),
    enterRoom(EnterRoom::class),
    runNotify(onRunCmdNotify::class),
    gift(onGift::class),
    recallMsg(onRecallMsg::class)
}

class MildomJsonWorker<T>(var clazz: Class<T>) {
    fun getAs(data: String): T {
        return Gson().fromJson<T>(data, clazz)
    }

    companion object {
//        fun get(data: String, type: MildomResponceType){
//            when (type) {
//                MildomResponceType.add -> {
//                }
//                MildomResponceType.userCount -> {
//                }
//                MildomResponceType.chat -> {
//                }
//            }
//        }

        fun getAsAdd(data: String): onAdd {
            val checked = checkEmptyString(data)
            return Gson().fromJson(checked, onAdd::class.java)
//            return get(data, MildomResponceType.add) as onAdd
        }

        fun getAsCount(data: String): onUserCount {
            val checked = checkEmptyString(data)
            return Gson().fromJson(checked, onUserCount::class.java)
//            return get(data, MildomResponceType.userCount) as onUserCount
        }

        fun getAsChat(data: String): onChat {
            val checked = checkEmptyString(data)
            return Gson().fromJson(checked, onChat::class.java)
//            return get(data, MildomResponceType.chat) as onChat
        }

        private fun checkEmptyString(data: String): String {
            return data.replace("\"\"","\" \"")
        }

        fun getAsEnterRoom(data: String): EnterRoom {
            val checked = checkEmptyString(data)
            return Gson().fromJson(checked, EnterRoom::class.java)
//            return get(data,MildomResponceType.enterRoom) as EnterRoom
        }

        fun getAsRunCmd(data: String): onRunCmdNotify {
            val checked = checkEmptyString(data)
            return Gson().fromJson(checked, onRunCmdNotify::class.java)
//            return get(data,MildomResponceType.runNotify) as onRunCmdNotify
        }

        fun getAsGift(data: String): onGift {
            val checked = checkEmptyString(data)
            return Gson().fromJson(checked, onGift::class.java)
        }

        fun getAsRecallMsg(data:String):onRecallMsg{
            val checked = checkEmptyString(data)
            return Gson().fromJson(checked,onRecallMsg::class.java)
        }

        fun getType(data: String): MildomResponceType {
            val gson = Gson()
            if (data.indexOf("\"cmd\": \"onAdd\"") != -1) return MildomResponceType.add
            if (data.indexOf("\"cmd\": \"onChat\"") != -1) return MildomResponceType.chat
            if (data.indexOf("\"cmd\": \"onUserCount\"") != -1) return MildomResponceType.userCount
            if (data.indexOf("\"cmd\": \"enterRoom\"") != -1) return MildomResponceType.enterRoom
            if (data.indexOf("\"cmd\": \"runCmdNotify\"") != -1) return MildomResponceType.runNotify
            if (data.indexOf("\"cmd\": \"ongift\"") != -1) return MildomResponceType.gift
            if (data.indexOf("\"cmd\": \"onRecallMsg\"") != -1) return MildomResponceType.recallMsg
            throw IllegalArgumentException()
        }
    }
}

class MildomSession() {

}