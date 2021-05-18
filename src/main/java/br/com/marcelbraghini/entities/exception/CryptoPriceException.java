package br.com.marcelbraghini.entities.exception;

public class CryptoPriceException extends RuntimeException {

    public CryptoPriceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
