package com.ndsl.nw.bun133.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IServerListener {
    void onPacket(DataInputStream in, NetWorkServerBase server) throws IOException;
}
