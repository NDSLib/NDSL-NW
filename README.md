# NDSL-NW
The part of NetWorking

## Json
com.ndsl.nw.bun133.Json is Json Class

Use Example:

```Java
  Json.build("{\"a\":10}")
```
The Source made Json with "a":10

You can also getValue or Key

```Java
Json.getContent("field_name")
```
It returns JsonContent.

### What is JsonContent?
Json is made of JsonContents.
JsonContent is "a":1 or "a":"a" ...and so on.

## TCPNetWorking
Cilent is NetworkBase
Server is NetworkServerBase

Most part of these is same.

### Send
```Java
send(Object)
```
It'l Send Object
We supported Integer,String,Json...
###getData
```Java
getData()
```
getData returns byte array,but you can use sockIn.readUTF() for String,sockIn.readInteger for Integer...

### getJson
```Java
if(isJson){
  getJson()
}
```
isJson returns available data is Json
and getJson return Json.

### Ping
```Java
ping()
```
It will return ping ms as long.

### PingWithJson
```Java
pingWithJson()
```
It will return ping and Json.build ms as long.
