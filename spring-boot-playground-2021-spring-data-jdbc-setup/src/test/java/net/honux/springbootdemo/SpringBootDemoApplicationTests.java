package net.honux.springbootdemo;

import org.apache.catalina.session.PersistentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SpringBootDemoApplicationTests {

	@Autowired
	private ApplicationContext ctx;
	private Logger logger = LoggerFactory.getLogger(SpringBootDemoApplicationTests.class);
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("IoC컨테이너 정상 동작 확")
	void contextLoads() {
		assertThat(logger).isNotNull();
		assertThat(ctx).isNotNull();
	}

	private Team soccerTeam;

	@BeforeEach
	void initTeam(){
		soccerTeam = new Team("Tottenham");
		teamRepository.save(soccerTeam);
	}

	@Test
	void readUser() {

		User son = new User("Son");
		User cain = new User("Cain");
		User lloris = new User("lloris");
		userRepository.save(son);
		userRepository.save(cain);
		userRepository.save(lloris);

		Agent sonAgent = new Agent(son.getId());
		Agent cainAgent = new Agent(cain.getId());
		Agent llorisAgent = new Agent(lloris.getId());
		soccerTeam.addAgent(sonAgent);
		soccerTeam.addAgent(cainAgent);
		soccerTeam.addAgent(llorisAgent);

		teamRepository.save(soccerTeam);
	}

	@AfterEach
	void findAll(){
		Iterable<Team> allTeam = teamRepository.findAll();
		assertThat(allTeam).isNotNull();
		for (Team team : allTeam) {
			System.out.println("Team ID 1: "+team);
		}
	}


}
