package workshop.introMVC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workshop.introMVC.exception.IncorrectDataException;
import workshop.introMVC.model.Portfolio;
import workshop.introMVC.model.Stock;
import workshop.introMVC.model.User;
import workshop.introMVC.service.PortfolioService;
import workshop.introMVC.service.StockService;
import workshop.introMVC.service.UserService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @GetMapping("/getPortfolio")
    public ResponseEntity<Portfolio> getPortfolio(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            if (!userService.hasPortfolio(email)) {
                throw new IncorrectDataException("User with email " + email + " does not have a portfolio", HttpStatus.NOT_FOUND);
            } else {
                Portfolio portfolio = user.getPortfolio();
                return new ResponseEntity<Portfolio>(portfolio, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/getPortfolioStocks")
    public ResponseEntity<List<Stock>> getPortfolioStocks(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            if (!userService.hasPortfolio(email)) {
                throw new IncorrectDataException("User with email " + email + " does not have a portfolio", HttpStatus.NOT_FOUND);
            } else {
                List<Stock> stocks = user.getPortfolio().getStocks();
                if (stocks == null) {
                    throw new IncorrectDataException("No stocks found for portfolio " + user.getPortfolio().getPortfolioId(), HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<List<Stock>>(stocks, HttpStatus.OK);
                }
            }
        }
    }

    @PostMapping("/createPortfolio")
    public ResponseEntity<String> createPortfolio(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            if (userService.hasPortfolio(email)) {
                throw new IncorrectDataException("User with email " + email + " already has a portfolio", HttpStatus.CONFLICT);
            } else {
                userService.assignPortfolio(email);
                return new ResponseEntity<String>("Portfolio created", HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("/deletePortfolio")
    public ResponseEntity<String> deletePortfolio(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            if (!userService.hasPortfolio(email)) {
                throw new IncorrectDataException("User with email " + email + " does not have a portfolio", HttpStatus.NOT_FOUND);
            } else {
                portfolioService.deletePortfolio(user);
                return new ResponseEntity<String>("Portfolio deleted", HttpStatus.OK);
            }
        }
    }

    @PostMapping("/addStock")
    public ResponseEntity<String> addStock(@RequestParam String email, @RequestParam String ticker) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        }
        if (!userService.hasPortfolio(email)) {
            throw new IncorrectDataException("User with email " + email + " does not have a portfolio", HttpStatus.NOT_FOUND);
        }
        Stock stock = stockService.viewStock(ticker);
        if ( stock == null) {
            throw new IncorrectDataException("Stock with ticker " + ticker + " does not exist", HttpStatus.NOT_FOUND);
        }
        portfolioService.addStock(user.getPortfolio().getPortfolioId(), stock);
        return new ResponseEntity<String>("Stock " + ticker + " has been added to portfolio", HttpStatus.OK);
    }

    @DeleteMapping("/deleteStock")
    public ResponseEntity<String> deleteStock(@RequestParam String email, @RequestParam String ticker) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new IncorrectDataException("User with email " + email + " does not exist", HttpStatus.NOT_FOUND);
        }
        if (!userService.hasPortfolio(email)) {
            throw new IncorrectDataException("User with email " + email + " does not have a portfolio", HttpStatus.NOT_FOUND);
        }
        Stock stock = stockService.viewStock(ticker);
        if ( stock == null) {
            throw new IncorrectDataException("Stock with ticker " + ticker + " does not exist", HttpStatus.NOT_FOUND);
        }
        if (portfolioService.deleteStock(user.getPortfolio().getPortfolioId(), ticker) == null) {
            throw new IncorrectDataException("Stock " + ticker + " does not exist in portfolio", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("Stock " + ticker + " has been deleted from portfolio", HttpStatus.OK);
    }

}
