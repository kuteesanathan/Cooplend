package com.tui.cooplend.commonerrors;

/*
 * Thrown when an action is requested against an entity that is not in the
 * correct state for that action
 * Eg approving an already-approved application.
 * Mapped to HTTP 409 Conflict - the request conflicts with
 * the current state of the resource.
 * */
public class InvalidStateTransitionException extends RuntimeException{
    public InvalidStateTransitionException(String message){
        super(message);
    }
}
