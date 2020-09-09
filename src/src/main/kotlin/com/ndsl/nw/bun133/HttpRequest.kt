package com.ndsl.nw.bun133

import java.net.Socket

class HttpRequest(socket: Socket) {
    companion object {
        val HTTP_VER:Double=1.1;
        val UserAgentName:String="NDSL-${com.ndsl.nw.bun133.NetWorking.Ver}";

        fun genGetReq(link: String,uri:String, port: Int): String {
            return genReqHeader("GET",link,uri,port)
        }

        /**
         * https://qiita.com/Sekky0905/items/dff3d0da059d6f5bfabf#http%E9%80%9A%E4%BF%A1%E3%81%AE%E4%B8%AD%E8%BA%AB
         */
        fun genReqHeader(method:String, link: String,uri:String, port: Int): String {
            return "$method $uri HTTP/$HTTP_VER" + "\n"+
                    "Host: $link:$port" + "\n"+
                    "Connection: keep-alive" + "\n"+
                    "User-Agent: $UserAgentName" + "\n"+
                    "Accept: */*" + "\n"+
                    "Accept-Encoding: identity" + "\n"+
                    "Accept-Language: ja,en-US;q=0.8,en;q=0.6";
        }

        fun sendReq(socket: Socket,request:String){
            socket.getOutputStream().write(request.toByteArray());
            socket.keepAlive=true;
        }

        fun sendReq(socket: Socket,method: String,link: String,uri: String,port: Int){
            sendReq(socket, genReqHeader(method, link, uri, port));
        }
    }

    init {
        socket.getOutputStream().write(genGetReq(socket.inetAddress.hostAddress,"/index.html",socket.port).toByteArray())
    }
}