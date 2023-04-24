import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CircularBuffer<T> {
    private static final String TRIED_TO_WRITE_TO_FULL_BUFFER =
            "Tried to write to full buffer";
    private static final String TRIED_TO_READ_FROM_EMPTY_BUFFER =
            "Tried to read from empty buffer";
    private final LinkedList<T> buffer;
    private final int size;

    public CircularBuffer(int size) {
        this.size = size;
        buffer = new LinkedList<>();
    }

    public T read() throws BufferIOException {
        try {
            return buffer.remove();
        } catch (NoSuchElementException e) {
            throw new BufferIOException(TRIED_TO_READ_FROM_EMPTY_BUFFER);
        }
    }

    public void write(T t) throws BufferIOException {
        try {
            if (!full()) {
                buffer.add(t);
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new BufferIOException(TRIED_TO_WRITE_TO_FULL_BUFFER);
        }
    }

    public void clear() {
        buffer.clear();
    }

    private boolean full() {
        return buffer.size() == size;
    }

    public void overwrite(T t) throws BufferIOException {
        if (full()) {
            read();
        }
        write(t);
    }
}