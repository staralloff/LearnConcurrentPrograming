package cn.org.orion.concurrent.program.Chapter05.networkAIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 
 * Asynchronous IO
 *
 */
public class AIOClient {
	public static void main(String[] args) throws Exception {
		//Open AsynchronousSocketChannel
		final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
		//let client connect the server which have given,and register a series of events.
		client.connect(new InetSocketAddress("localhost", 8000), null, new CompletionHandler<Void, Object>() {

			@Override
			public void completed(Void result, Object attachment) {
				client.write(ByteBuffer.wrap("Hello!".getBytes()), null, new CompletionHandler<Integer, Object>() {

					@Override
					public void completed(Integer result, Object attachment) {
						try {
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							client.read(buffer,buffer,new CompletionHandler<Integer, ByteBuffer>() {
								@Override
								public void completed(Integer result, ByteBuffer attachment) {
									buffer.flip();
									System.out.println(new String(buffer.array()));
									try {
										client.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void failed(Throwable exc, ByteBuffer attachment) {
									System.err.println("exc error: " + exc);
								}
							});
						}catch(Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						System.err.println("exc error: " + exc);
					}
					
				});
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.err.println("exc error: " + exc);
			}
		});
		//Once main thread ends,wait execution all finished.
		Thread.sleep(1000);
	}
}
