package org.bloomberg.io.clustereddatawarehouse.exceptions;

public class InvalidDealRequestException extends RuntimeException{
    public InvalidDealRequestException(String message) {
        super(message);
    }
}
