package org.bloomberg.io.clustereddatawarehouse.exceptions;

public class DealNotFoundException extends RuntimeException{
    public DealNotFoundException(String message) {
        super(message);
    }

}
