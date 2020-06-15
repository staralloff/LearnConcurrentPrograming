package cn.org.orion.concurrent.program.Chapter07.Inbox;

import java.util.concurrent.TimeUnit;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

public class MyWorker extends UntypedActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	public static enum Msg {
		WORKING, DONE, CLOSE;
	}

	@Override
	public void onReceive(Object msg) {
		if (msg == Msg.WORKING) {
			log.info("I am working");
		}
		if (msg == Msg.DONE) {
			log.info("Stop working");
		}
		if (msg == Msg.CLOSE) {
			log.info("I will shutdown");
			getSender().tell(Msg.CLOSE, getSelf());
			getContext().stop(getSelf());
		} else
			unhandled(msg);
	}
	
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));
		ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");
		
		final Inbox inbox = Inbox.create(system);
		inbox.watch(worker);
		inbox.send(worker, MyWorker.Msg.WORKING);
		inbox.send(worker, MyWorker.Msg.DONE);
		inbox.send(worker, MyWorker.Msg.CLOSE);
		
		while(true) {
			Object msg = inbox.receive(Duration.create(1, TimeUnit.SECONDS));
			if(msg==MyWorker.Msg.CLOSE) {
				System.out.println("My worker is Closing");
			}else if(msg instanceof Terminated) {
				System.out.println("My worker is dead");
				system.shutdown();
				break;
			}else{
				System.out.println(msg);
			}
		}
	}

}
