package com.example.kirmi.ks1807;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Functions used by the entire application
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
}
