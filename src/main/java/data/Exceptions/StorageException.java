package data.Exceptions;

public class StorageException extends Exception{

    public StorageException(String errorMessage) {
        super(errorMessage);
    }

    public static void checkFileReadable(boolean canRead) throws StorageException {
        if (!canRead) {
            throw new StorageException("File is not readable!");
        }
    }

}
