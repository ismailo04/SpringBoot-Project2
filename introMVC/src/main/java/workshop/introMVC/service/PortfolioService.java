package workshop.introMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import workshop.introMVC.model.Portfolio;
import workshop.introMVC.model.Stock;
import workshop.introMVC.model.User;
import workshop.introMVC.repository.PortfolioRepository;
import workshop.introMVC.repository.UserRepository;

@Service
public class PortfolioService {
    
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    public Portfolio getPortfolio(int id) {
        return portfolioRepository.findPortfolioByPortfolioId(id);
    }

    public List<Stock> getPortfolioStocks(int id) {
        return portfolioRepository.findAllStockByPortfolioId(id);
    }

    public void deletePortfolio(User user) {
        Portfolio portfolio = user.getPortfolio();
        user.setPortfolio(null);
        userRepository.save(user);
        portfolioRepository.delete(portfolio);

    }

    public Portfolio addStock(int id, Stock stock) {
        Portfolio portfolio = getPortfolio(id);
        portfolio.getStocks().add(stock);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio deleteStock(int id, String ticker) {
        Portfolio portfolio = getPortfolio(id);
        portfolio.getStocks().removeIf(stock -> stock.getTicker().equals(ticker));
        return portfolioRepository.save(portfolio);
    }
}
