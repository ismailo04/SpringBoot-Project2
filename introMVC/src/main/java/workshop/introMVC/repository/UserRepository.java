package workshop.introMVC.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import workshop.introMVC.model.Portfolio;
import workshop.introMVC.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

    public User findUserByUserId(int userId);

    public User findUserByEmail(String email);

    public Portfolio findPortfolioByUserId(int userId);

}
