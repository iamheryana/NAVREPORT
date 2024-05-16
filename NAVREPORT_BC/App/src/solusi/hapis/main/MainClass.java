package solusi.hapis.main;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
	
	public static void main(String[] args){
		System.out.println("test-start");
		testDao();
		System.out.println("test-end");
	}
	
	
	public static void testDao(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("test-config.xml");
//		M08hcabDAO dao = (M08hcabDAO) ctx.getBean("m08hcabDAO");
		
	}

}
