package Service;

public class Validator
{

    public static boolean isInteger(String string)
    {
        try
        {
            Integer.parseInt(string);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

}