package securityservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import securityservice.repository.UserRepos;
@SpringBootTest
class SecurityServiceApplicationTests {
	@Autowired
	private UserRepos repos;

	@Test
	void contextLoads() {
		Integer rsl = repos.countAllByUsernameAndIdNot("user_read", 5);
		System.out.println(rsl);
	}
}
