package com.example.projekt1.Exceptions;

public class StorageException extends RuntimeException{

    public StorageException(String message) { super(message); }

    public  StorageException(String message, Throwable cause){ super(message, cause);}
}
