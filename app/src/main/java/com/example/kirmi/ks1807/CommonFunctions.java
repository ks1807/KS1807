package com.example.kirmi.ks1807;

//Functions used by the entire application
public class CommonFunctions
{
    public String getEmojiByUnicode(int unicode)
    {
        return new String(Character.toChars(unicode));
    }
}
