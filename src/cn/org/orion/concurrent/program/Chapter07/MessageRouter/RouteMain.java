package cn.org.orion.concurrent.program.Chapter07.MessageRouter;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cn.org.orion.concurrent.program.Chapter07.ActorLifeCycle.MyWorker;

public class RouteMain {
	public static Agent<Boolean> flag = Agent.create(true, ExcutionContexts.global());
	public static void main(String[] args) throws InterruptedException {
		ActorSystem system = ActorSystem.create("route", ConfigFactory.load("samplehello.conf"));
		ActorRef w = system.actorOf(Props.create(WatchActor.class), "watcher");
		int i=1;
		while(flag.get()) {
			w.tell(MyWorker.Msg.WORKING, ActorRef.noSender());
			if(i%10==0)w.tell(MyWorker.Msg.CLOSE, ActorRef.noSender());
			i++;
			Thread.sleep(100);
		}
	}

}
