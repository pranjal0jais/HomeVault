package com.pranjal.vendor_service.exception;

public class VendorNotFoundException extends RuntimeException{
    public VendorNotFoundException(String message){
        super(message);
    }
}
