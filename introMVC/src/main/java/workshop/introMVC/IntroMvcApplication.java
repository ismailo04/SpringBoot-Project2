package workshop.introMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import workshop.introMVC.model.Stock;
import workshop.introMVC.repository.StockRepository;

@SpringBootApplication
public class IntroMvcApplication {

	@Autowired
	StockRepository stockRepository;

	public static void main(String[] args) {
		SpringApplication.run(IntroMvcApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner () throws InterruptedException {
		return args -> {
			makeStocks();
		};
	}

	public void makeStocks() throws InterruptedException {
		Stock stock1 = new Stock();
		stock1.setName("Apple Inc.");
		stock1.setTicker("AAPL");
		stock1.setPrice(172.88);
		stock1.setVolume(59970000);
		stock1.setOpen(175.31);
		stockRepository.save(stock1);
	}
}
