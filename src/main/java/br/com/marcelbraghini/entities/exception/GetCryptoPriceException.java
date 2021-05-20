package br.com.marcelbraghini.entities.exception;

public class GetCryptoPriceException extends RuntimeException {

    public GetCryptoPriceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
