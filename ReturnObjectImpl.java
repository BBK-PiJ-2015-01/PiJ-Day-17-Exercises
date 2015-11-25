/*
 * An implementation of the ReturnObject interface.
 *
 * @author Simon Baird
 */
public class ReturnObjectImpl implements ReturnObject {

    private final Object value;
    private final ErrorMessage message;

    /**
     * Create the ReturnObject from supplied arguments. If message is null then
     * this will be stored as NO_ERROR
     *
     * @param value any object, can be null
     * @param message an instance of an ErrorMessage. Can be null
     */
    public ReturnObjectImpl(Object value, ErrorMessage message) {
        this.value = value;
        this.message = message == null ? ErrorMessage.NO_ERROR : message;
    }

    @Override
    public boolean hasError() {

        return message != null && message != ErrorMessage.NO_ERROR;
    }

    @Override
    public ErrorMessage getError() {

        return hasError() ? message : ErrorMessage.NO_ERROR;
    }

    @Override
    public Object getReturnValue() {

        return hasError() ? null : value;
    }

}