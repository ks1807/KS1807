package com.example.kirmi.ks1807;
import java.util.regex.Pattern;

//Functions used by the entire application.
public class CommonFunctions
{
    public String getEmojiByUnicode(int unicode)
    {
        return new String(Character.toChars(unicode));
    }

    //Checks if a string is a number or not.
    public boolean isNumeric(String TheString)
    {
        try
        {
            int Number = Integer.parseInt(TheString);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    //Code adapted from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
    public boolean IsEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
        {
            return false;
        }
        return pat.matcher(email).matches();
    }

    /*Password must be at least 8 characters, have at least one upper and lower case letter
    and a special character.
    Code adapted from https://stackoverflow.com/questions/36574183/how-to-validate-password-field-in-android*/
    public boolean ValidPassword(String password)
    {
        if (password.length() < 8)
        {
            return false;
        }

        Pattern pat;
        String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pat = Pattern.compile(PASSWORD_REGEX);

        return pat.matcher(password).matches();
    }
}
