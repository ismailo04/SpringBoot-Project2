package workshop.introMVC.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import workshop.introMVC.model.Stock;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer>{

    public Stock findStockByTicker(String ticker);
}
