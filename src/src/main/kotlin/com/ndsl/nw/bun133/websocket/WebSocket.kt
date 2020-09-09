package com.ndsl.nw.bun133.websocket

import org.java_websocket.WebSocket
import org.java_websocket.WebSocketImpl
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import java.net.URI

class WebSocketServer(address: InetSocketAddress) : WebSocketServer(address){
    constructor() : this(InetSocketAddress(WebSocketImpl.DEFAULT_PORT))
    constructor(port:Int) : this(InetSocketAddress(port))

    var isConnecting:Boolean = !this.connections.isEmpty()
    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        isConnecting = true
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        isConnecting = false
    }
    var messages:MutableList<String> = mutableListOf()
    override fun onMessage(conn: WebSocket?, message: String?) {
        if(message!=null){
            messages.add(message)
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        throw ex!!
    }

    override fun onStart() {
        isConnecting=false
    }

    fun send(data: String, socket: WebSocket){
        socket.send(data)
    }

    fun sendAll(data: String){
        broadcast(data)
    }
}

open class WebSocketClient(uri: URI) : WebSocketClient(uri){
    var isConnecting:Boolean = false
    override fun onOpen(handshakedata: ServerHandshake?) {
        println("onOpen")
        isConnecting=true
    }

    var messages:MutableList<String> = mutableListOf()
    override fun onMessage(message: String?) {
        println("onMessage")
        if(message!=null) messages.add(message)
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("onClose")
        isConnecting=false
    }

    override fun onError(ex: Exception?) {
        println("onError")
        throw ex!!
    }
}

