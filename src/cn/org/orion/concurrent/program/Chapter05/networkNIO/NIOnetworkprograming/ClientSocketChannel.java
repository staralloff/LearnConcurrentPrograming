package cn.org.orion.concurrent.program.Chapter05.networkNIO.NIOnetworkprograming;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ClientSocketChannel {
	public static void main(String[] args) {
		startChannelConnection();
	}

	private static void startChannelConnection() {
		try {
			//create a selector
			Selector selector = Selector.open();
			//open channel
			SocketChannel open = SocketChannel.open();
			//set none block
			open.configureBlocking(false);
			//config link host and port, at call open.finishConnect();then finish link
			open.connect(new InetSocketAddress("127.0.0.1",8000));
			//while open channel, register interested transaction.
			open.register(selector, SelectionKey.OP_CONNECT);
			
			//start loop test if happen interested thing.
			for(;;) {
				//this method will block until interested things happen(use wakeup method to notify this method)
				selector.select();
				//get interested event's handle method.
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				//iterate this iterator to find match event's handle method.
				while(iterator.hasNext()) {
					SelectionKey next = iterator.next();
					//remove current object manually, so as not to repeat handle.
					iterator.remove();
					//start match mapper event.
					if(next.isValid()) {
						if(next.isConnectable()) {
							System.out.println("开始连接********");
							SocketChannel channel = (SocketChannel) next.channel();
							channel.finishConnect();
							System.out.println("连接完成********");
							//at this send msg to server.
							channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));
							new Thread(new Runnable() {
								@Override
								public void run() {
									while(true) {
										try {
											Scanner scanner = new Scanner(System.in);
											String nextLine = scanner.nextLine();
											channel.write(ByteBuffer.wrap(nextLine.getBytes()));
											next.interestOps(SelectionKey.OP_READ);
											selector.wakeup();
										} catch (Exception e) {
											break;
										}
									}
								}
							}).start();
							next.interestOps(SelectionKey.OP_READ);
						}else if(next.isValid()&&next.isReadable()) {
							try {
								SocketChannel channel2 = (SocketChannel) next.channel();
								ByteBuffer dsts = ByteBuffer.allocate(1024);
								//if allocate value is smaller,as to not codemiss can cached all byte first
								//then use String constructor to encode UTF-8 once.
								//but this have more cached operation.
								List<Byte> bytearr = new ArrayList<Byte>();
								StringBuffer buffer = new StringBuffer();
								while(true) {
									dsts.clear();
									int len = 0;
									len = channel2.read(dsts);
									if(len<=0) {
										break;
									}else {
										buffer.append(new String(dsts.array(), 0, len, "UTF-8"));
									}
								}
								System.out.println(new String(buffer.toString().getBytes(), "UTF-8"));
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
							next.interestOps(SelectionKey.OP_READ);
						}else if(next.isWritable()) {
							//because we not have register WRITE event, so will not output below sentence.
							System.out.println("dowrite********");
							next.interestOps(SelectionKey.OP_READ);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
