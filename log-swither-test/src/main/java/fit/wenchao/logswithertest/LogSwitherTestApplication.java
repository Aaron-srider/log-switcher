package fit.wenchao.logswithertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class LogSwitherTestApplication {

	public static void main(String[] args) {
 log.debug("hello");
 log.info("ghlasjdkfajsfhljkas");
		TestClass test = new TestClass();
		SpringApplication.run(LogSwitherTestApplication.class, args);
	}

}
