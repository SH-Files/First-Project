package service;

import java.util.HashSet;

public class CustomSet<E> extends HashSet<E>
{
    public E get(int hashCode)
    {
        for (E element : this)
        {
            if (element.hashCode() == hashCode)
            {
                return element;
            }
        }
        return null;
    }
}