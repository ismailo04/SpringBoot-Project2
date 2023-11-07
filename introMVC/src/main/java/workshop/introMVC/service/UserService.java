package workshop.introMVC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import workshop.introMVC.model.Portfolio;
import workshop.introMVC.model.User;
import workshop.introMVC.repository.PortfolioRepository;
import workshop.introMVC.repository.UserRepository;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;
    
    public User updateEmail(String oldEmail, String newEmail) {
        User user = userRepository.findUserByEmail(oldEmail);
        if (user == null) {
            return null;
        } else {
            user.setEmail(newEmail);
            userRepository.save(user);
            return user;
        }
    }

    public User updatePassword(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        } else {
            user.setPassword(password);
            userRepository.save(user);
            return user;
        }
    }

    public boolean hasPortfolio(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return false;
        } else {
            if (user.getPortfolio() != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    public User assignPortfolio(String email) {
        // User user = userRepository.getUser(email);
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        } else {
            Portfolio portfolio = new Portfolio();
            user.setPortfolio(portfolio);
            portfolioRepository.save(portfolio);
            userRepository.save(user);
            return user;
        }
    }

    public User addUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);
        return user;
    }

    public User getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email);
        userRepository.delete(user);
    }
}
