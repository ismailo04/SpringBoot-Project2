package workshop.introMVC.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import workshop.introMVC.model.Portfolio;
import workshop.introMVC.model.Stock;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {

    public Portfolio findPortfolioByPortfolioId(int portfolioId);

    public List<Stock> findAllStockByPortfolioId(int portfolioId);
}
