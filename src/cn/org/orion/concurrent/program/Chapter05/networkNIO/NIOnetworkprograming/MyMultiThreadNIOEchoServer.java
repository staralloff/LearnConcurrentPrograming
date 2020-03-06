package cn.org.orion.concurrent.program.Chapter05.networkNIO.NIOnetworkprograming;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyMultiThreadNIOEchoServer {
	private Selector selector;
	private ExecutorService tp = Executors.newCachedThreadPool();
	
	//public static Map<Socket,Long> time_stat=new HashMap<Socket,Long>(10240);
	private void startServer() throws Exception {
		// open a selector.
		selector = Selector.open();
		// open a server socket channel.
		ServerSocketChannel ssc = ServerSocketChannel.open();
		// set none block.
		ssc.configureBlocking(false);
		// bind port for server.
		InetSocketAddress isa = new InetSocketAddress(8000);
		ssc.socket().bind(isa);
		// register interested things, there for accept.
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		for (;;) {
			// this will block until reach exciting things.
			selector.select();
			// cycle ask selector's reached things.
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator i = readyKeys.iterator();
			// ready things then to operate.
			//long e=0;
			while(i.hasNext()) {
				SelectionKey sk = (SelectionKey) i.next();
				// notice call iterator's end keyIterator.remove() everytime.
				// Selector will not remove SelectionKey instance from themself's SelectionKey set.
				// must remove it while operate end.
				i.remove();
				
				if (sk.isAcceptable()) {
					doAccept(sk);
				} else if (sk.isValid() && sk.isReadable()) {
					//if(!time_stat.containsKey(((SocketChannel)sk.channel()).socket()))
						//time_stat.put(((SocketChannel)sk.channel()).socket(),
								//System.currentTimeMillis());
					doRead(sk);
				} else if (sk.isValid() && sk.isWritable()) {
					doWrite(sk);
					//e=System.currentTimeMillis();
					//long b=time_stat.remove(((SocketChannel)sk.channel()).socket());
					//System.out.println("spend:"+(e-b)+"ms");
				}
			}
		}
	}
	
	private void doWrite(SelectionKey sk) {
		SocketChannel channel = (SocketChannel) sk.channel();
		ByteBuffer bb = (ByteBuffer) sk.attachment();
		try {
			int len = channel.write(bb);
			if (len == -1) {
				disconnect(sk);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			disconnect(sk);
		}
		sk.interestOps(SelectionKey.OP_READ);
	}
	
	private void doRead(SelectionKey sk) {
		SocketChannel channel = (SocketChannel) sk.channel();
		ByteBuffer bb = ByteBuffer.allocate(1024);
		StringBuffer buf = new StringBuffer();
		int len = 0;
		try {
			while(true) {
				bb.clear();
				len = channel.read(bb);
				if(len<=0) {
					break;
				}else {
					buf.append(new String(bb.array(),0,len,"UTF-8"));
				}
			}
			System.out.println("客户端说:"+new String(buf.toString().getBytes(),"UTF-8"));
			if (len < 0) {
				disconnect(sk);
				return;
			}
		} catch (Exception e) {
			disconnect(sk);
			return;
		}
	}
	
	private void disconnect(SelectionKey sk) {
		try {
			sk.channel().close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doAccept(SelectionKey sk) {
		System.out.println("开始接受accept");
		ServerSocketChannel server = (ServerSocketChannel) sk.channel();
		SocketChannel clientChannel;
		try {
			clientChannel = server.accept();
			clientChannel.configureBlocking(false);
			// while has new client connected, register read channel, then register interested channel.
			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
			ByteBuffer byteBuf = ByteBuffer.allocate(1024);
			clientKey.attach(byteBuf);
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			byteBuf.put(("Accepted connection from "+ clientAddress.getHostAddress()).getBytes());
			byteBuf.flip();
			clientChannel.write(byteBuf);
			clientKey.interestOps(SelectionKey.OP_WRITE);
			selector.wakeup();// notify  wakeup selector() method.
			tp.execute(new Runnable() {
				@Override
				public void run() {
					while(true) {
						ByteBuffer bb = (ByteBuffer) clientKey.attachment();
						if(bb!=null) {
							break;
						}
					}
					ByteBuffer bb = (ByteBuffer) clientKey.attachment();
					bb.put("".getBytes());
					while(true) {
						try {
							System.out.println(Thread.currentThread().getName());
							Scanner scanner = new Scanner(System.in);
							String nextLine = scanner.nextLine();
							bb.clear();
							bb.put(nextLine.getBytes());
							bb.flip();
							clientKey.interestOps(SelectionKey.OP_WRITE);
							selector.wakeup();
						} catch(Exception e) {
							e.printStackTrace();
							break;
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		MyMultiThreadNIOEchoServer echoServer = new MyMultiThreadNIOEchoServer();
		try {
			echoServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
