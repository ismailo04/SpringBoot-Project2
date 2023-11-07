package workshop.introMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workshop.introMVC.exception.IncorrectDataException;
import workshop.introMVC.model.Stock;
import workshop.introMVC.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
    
    @Autowired
    private StockService stockService;

    @GetMapping("/view")
    public ResponseEntity<Stock> viewStock(@RequestParam String ticker) {
        Stock stock = stockService.viewStock(ticker);
        if (stock == null) {
            throw new IncorrectDataException("Stock with ticker " + ticker + " does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Stock>(stock, HttpStatus.OK);
        }
    }
}
