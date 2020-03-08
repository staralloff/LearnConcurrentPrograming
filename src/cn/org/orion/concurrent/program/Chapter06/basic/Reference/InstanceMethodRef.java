package cn.org.orion.concurrent.program.Chapter06.basic.Reference;

import java.util.ArrayList;
import java.util.List;

public class InstanceMethodRef {
	
	static class User {
		private int id;
		private String name;
		
		public User(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public Integer getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
        public String toString(){
            return "User [id=" + id + ", name=" + name + "]";
        }
		
	}
	
	public static void main(String[] args) {
		List<User> users = new ArrayList<User>();
		for(int i=1;i<25;i++) {
			users.add(new User(i,"billy"+Integer.toString(i)));
		}
		users.stream().map(User::getName).forEach(System.out::println);
	}

}
