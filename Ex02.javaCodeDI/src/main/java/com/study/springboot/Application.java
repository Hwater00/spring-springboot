package com.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.study.springboot.been.Config;
import com.study.springboot.been.Member;
import com.study.springboot.been.Printer;
import com.study.springboot.been.PrinterB;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		//SpringApplication.run(Ex02JavaCodeDiApplication.class,args);
		
				//1.IoC컨테이너 생성
				ApplicationContext context =
						new AnnotationConfigApplicationContext(Config.class);
				
				//2. Member Bean 가져오기
				Member member1 = (Member)context.getBean("member1");
				member1.print();
				
				Member member2 = context.getBean("hello",Member.class);
				member2.print();
				
				//4. PrinterB Bean 가져오기
				Printer printer = context.getBean("printerB",PrinterB.class);
				member1.setPrinter(printer);
				member1.print();
				
				//5. 싱글톤인지 확인
				if(member1 == member2) {
					System.out.println("같은 객체입니다");
				}else {
					System.out.println("서로 다른 객체입니다.");
				}
				
				
	}


}
