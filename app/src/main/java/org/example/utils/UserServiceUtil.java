package org.example.utils;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

    public String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword,BCrypt.gensalt(10));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword,hashedPassword);
    }
}
