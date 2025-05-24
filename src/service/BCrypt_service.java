package service;

import org.mindrot.jbcrypt.BCrypt;

public class BCrypt_service {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashesPassowrd) {
        return BCrypt.checkpw(plainPassword, hashesPassowrd);
    }
}
