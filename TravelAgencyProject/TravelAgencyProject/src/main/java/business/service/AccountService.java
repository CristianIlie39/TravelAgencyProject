package business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.AccountDAO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AccountService {

    @Autowired
    AccountDAO accountDAO;

    public String cryptPassword(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, messageDigest.digest(password.getBytes()));
        String cryptedPassword = bigInteger.toString();
        return cryptedPassword;
    }

    public int deleteAccountByUsername(String username) {
        return accountDAO.deleteAccountByUsername(username);
    }

    public int updateLogInUser(boolean loggedIn, String username) {
        return accountDAO.updateLogInUser(loggedIn, username);
    }

    public int changeUsername(String newUsername, String currentUsername) {
        return accountDAO.changeUsername(newUsername, currentUsername);
    }

}
