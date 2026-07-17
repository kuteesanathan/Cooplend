package com.tui.cooplend.commonerrors;

/*
* Thrown when a uniqueness constraint would be violated
* Such as duplicate member number, nationalID, etc
* Mapped to HTTP 409 Conflict.
* */
public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message){
        super(message);
    }
}
