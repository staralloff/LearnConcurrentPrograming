package cn.org.orion.concurrent.program.Chapter05.networkAIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AIOEchoServer {
	public final static int PORT = 8000;
	private AsynchronousServerSocketChannel server;
	public AIOEchoServer() throws IOException {
		server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
	}
	
	public void start() throws InterruptedException, ExecutionException, TimeoutException {
		System.out.println("Server listen on " + PORT);
		//register event and event finished process unit.
		//1.call accept request.tell system start listening.
		//2.register CompletionHandler instance,tell system, once have client come connect, if success,
		//execute CompletionHandler.completed() method: if failed,execute CompletionHandler.failed() method.
		// so server.accept() method will not block,it will return at once.
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
			final ByteBuffer buffer = ByteBuffer.allocate(1024);
			@Override
			public void completed(AsynchronousSocketChannel result, Object attachment) {
				System.out.println(Thread.currentThread().getName());
				Future<Integer> writeResult = null;
				try {
					buffer.clear();
					//use read() method read client data.attation please,AsynchronousSocketChannel.read() method
					//also asynchronous,it will not wait read finish then callback,it will return at once,return a
					//future,thus it's a Future mode typically.for easy code,it directly call Future.get() method,
					//execute wait,change this asynchronous method to synchronous method.so,while execution finished,
					//read data have done.
					result.read(buffer).get(100, TimeUnit.SECONDS);
					buffer.flip();
					//return data back to client.this call AsynchronousSocketChannel.write() method.this method will not
					//wait all data writed,also callback at once.As same as read() method,it will return Future object.
					writeResult = result.write(buffer);
				}catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}catch(TimeoutException e) {
					e.printStackTrace();
				}finally {
					try {
						server.accept(null, this);
						//the server ready for next client connecting.at the same time,shut down current executing
						//client connect.but before closed,must make sure write() ops have done first,so that,use
						//Future.get() method execute waiting.
						writeResult.get();
						result.close();
					}catch(Exception e) {
						System.out.println(e.toString());
					}
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("failed: " + exc);
			}
			
		});
	}
	
	public static void main(String[] args) throws Exception {
		new AIOEchoServer().start();
		// main thread can continue its work.
		while(true) {
			Thread.sleep(1000);
		}
	}

}
