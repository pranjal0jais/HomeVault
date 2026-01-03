package com.pranjal.asset_service.exception;

public class InvalidVendorException extends RuntimeException{
    public InvalidVendorException(String message){
        super(message);
    }
}
