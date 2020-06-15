package cn.org.orion.concurrent.program.Chapter07.Actor;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class HelloMainSimple {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("samplehello.conf"));
		ActorRef a = system.actorOf(Props.create(HelloWorld.class), "HelloWorld");
		System.out.println("HelloWorld Actor Path:" + a.path());
	}
}
