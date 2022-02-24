package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	};

	public static void main(String[] args) {

		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//clasesAnteriores();
		saveUserInDatabase();
		getInformationJpqlFromUser();
		saveWithErrorTransational();
	}

	private void getInformationJpqlFromUser(){
		/*LOGGER.info("Usuario con el metodo findByUserEmail" + userRepository.findByUserEmail("julie@domain.com").orElseThrow(()-> new RuntimeException("No se encontro el usuario")));
		userRepository.findAndSort("Test", Sort.by("id").ascending()).stream().forEach(user -> LOGGER.info("usuario con metodo sort " + user));

		userRepository.findByName("John").stream().forEach(user -> LOGGER.info("usuario con query method: " + user));
		userRepository.findByNameAndEmail("John","john@domain.com" ).stream().forEach(user -> LOGGER.info("usuario con query method: " + user));

		userRepository.findByNameLike("%Test%").stream().forEach(user -> LOGGER.info("Usuarios like: " + user));

		userRepository.findByNameOrEmail("","john@domain.com" ).stream().forEach(user -> LOGGER.info("Usuarios OR: " + user));*/

//		userRepository.findByBirthDateBetween(LocalDate.of(2021,03,01),LocalDate.of(2021,04,02) ).stream().forEach(user -> LOGGER.info("Usuarios BEETWEN: " + user));
//		userRepository.findByNameContainingOrderByIdDesc("John").stream().forEach(user -> LOGGER.info("User sort: " + user));
//		LOGGER.info("El usuario a partir del named Parameter + " +userRepository.getAllByBirthDateAndEmail(
//				LocalDate.of(2021, 03, 25), "daniela@domain.com")
//				.orElseThrow(() ->
//						new RuntimeException()));
	}

	private void saveWithErrorTransational(){
		User test1 = new User("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "TestTransactional1@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);

		try{
			userService.saveTransactional(users);
		}catch (Exception ex){
			LOGGER.error("Error durante el insert transactional");
		}
		userService.getAllUsers().forEach(user -> LOGGER.info("user transactional :" + user));
	}

	private void saveUserInDatabase(){
		User user1 = new User("John", "john@domain.com", LocalDate.of(2021, 03, 15));
		User user2 = new User("Julie", "julie@domain.com", LocalDate.of(2021, 03, 20));
		User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2021, 03, 25));
		User user4 = new User("Oscar", "oscar@domain.com", LocalDate.now());
		User user5 = new User("Test1", "Test1@domain.com", LocalDate.now());
		User user6 = new User("Test2", "Test2@domain.com", LocalDate.now());
		User user7 = new User("Test3", "Test3@domain.com", LocalDate.now());
		User user8 = new User("Test4", "Test4@domain.com", LocalDate.now());
		User user9 = new User("Test5", "Test5@domain.com", LocalDate.now());
		User user10 = new User("Test6", "Test6@domain.com", LocalDate.now());
		User user11 = new User("Test7", "Test7@domain.com", LocalDate.now());
		User user12 = new User("Test8", "Test8@domain.com", LocalDate.now());
		User user13 = new User("Test9", "Test9@domain.com", LocalDate.now());
		List<User> list = Arrays.asList(user4, user1, user3, user2, user5, user6, user7, user8, user9, user10, user11, user12, user13);
		list.forEach(userRepository::save);
	}



	private void clasesAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
		try{
			int value = 41/0;
			LOGGER.debug("Mi valor : " + value);
		}catch (Exception ex){
			LOGGER.error("Esto es un error al dividir por 0"+ ex);
		}
	}
}
