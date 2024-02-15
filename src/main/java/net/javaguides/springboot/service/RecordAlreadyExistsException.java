package net.javaguides.springboot.service;

public class RecordAlreadyExistsException extends RuntimeException
{
	public RecordAlreadyExistsException(String message) {
        super(message);
    }
}
