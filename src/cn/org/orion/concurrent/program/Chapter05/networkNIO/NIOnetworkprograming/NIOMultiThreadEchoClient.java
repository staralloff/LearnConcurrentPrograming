package cn.org.orion.concurrent.program.Chapter05.networkNIO.NIOnetworkprograming;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * 
 * 高并发书中原版
 */
public class NIOMultiThreadEchoClient {
	private Selector selector;
	public void init(String ip, int port) throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		this.selector = SelectorProvider.provider().openSelector();
		channel.connect(new InetSocketAddress(ip, port));
		channel.register(selector, SelectionKey.OP_CONNECT);
	}
	
	public void working() throws IOException {
		while (true) {
			if (!selector.isOpen())
				break;
			selector.select();
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = ite.next();
				ite.remove();
				// connect transaction happen.
				if (key.isConnectable()) {
					connect(key);
				} else if (key.isReadable()) {
					read(key);
				}
			}
		}
	}
	
	public void connect(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		// if connecting, finish connect.
		if (channel.isConnectionPending()) {
			channel.finishConnect();
		}
		channel.configureBlocking(false);
		channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));
		channel.register(this.selector, SelectionKey.OP_READ);
	}
	
	public void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		// create read buffer.
		ByteBuffer buffer = ByteBuffer.allocate(100);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("客户端收到信息: " + msg);
		channel.close();
		key.selector().close();
	}

}
