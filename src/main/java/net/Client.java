package net;

import exceptions.ResponseTimeoutException;
import json.Json;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class Client {
    private final Selector selector;

    private static final int MAX_PACKET_LENGTH = 10_000;

    public Client(String host, int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(host, port);
        DatagramChannel channel = DatagramChannel.open().connect(address);
        selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_WRITE, address);
    }

    public Response sendAndReceiveResponse(String message, long timeout) throws IOException {
        selector.select();
        for (SelectionKey key: selector.selectedKeys()) {
            if(key.isWritable()) {
                write(key, message);
                String response = read(key, timeout);
                return Json.fromJson(Json.parse(response), Response.class);
            }
        }
        // never thrown, read throws Exception
        throw new RuntimeException();
    }

    private void write(SelectionKey key, String msg) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.write(StandardCharsets.UTF_16.encode(msg));
    }

    private String read(SelectionKey key, long timeout) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_LENGTH * 2); // UTF 16 encoding
        buffer.clear();
        long startTime = System.currentTimeMillis();
        List<String> received = new ArrayList<>(10);

        while (System.currentTimeMillis() - startTime < timeout * 1000) {

            if (channel.read(buffer) <= 0) continue;

            buffer.flip();
            String msg = StandardCharsets.UTF_16.decode(buffer).toString();

            if (msg.equals("END"))
                return attachInput(received);

            received.add(msg);
            buffer.clear();
        }

        throw new ResponseTimeoutException();
    }

    private String attachInput(List<String> input) {
        StringBuilder b = new StringBuilder();
        input.forEach(b::append);
        return b.toString();
    }

}