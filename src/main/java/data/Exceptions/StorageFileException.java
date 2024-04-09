package data.Exceptions;

public class StorageFileException extends Exception{

    public StorageFileException(String errorMessage) {
        super(errorMessage);
    }

    public static void checkFileReadable(boolean canRead) throws StorageFileException {
        if (!canRead) {
            throw new StorageFileException("File is not readable!");
        }
    }
}
