package carddb.deserializers;

import java.io.IOException;

class DeserializeException extends IOException {
    public DeserializeException(String message) {
        super(message);
    }
}