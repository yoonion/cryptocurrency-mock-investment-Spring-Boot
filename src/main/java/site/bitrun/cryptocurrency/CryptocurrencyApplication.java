package site.bitrun.cryptocurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.bitrun.cryptocurrency.service.CryptoServiceImpl;

@SpringBootApplication
public class CryptocurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptocurrencyApplication.class, args);
	}

}
