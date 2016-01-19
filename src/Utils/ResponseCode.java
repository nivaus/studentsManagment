package Utils;

/**
 * Created by oslander on 18/01/2016.
 */
public class ResponseCode {
    public enum ResponseCodeResult { SUCCESS, FAIL }

    public ResponseCodeResult result = ResponseCodeResult.SUCCESS;

    public String errorMessage;
    public Object data;

    public void Success (Object data)
    {
        this.result = ResponseCodeResult.SUCCESS;
        this.errorMessage = null;
        this.data = data;
    }

    public void Fail (String errorMessage)
    {
        this.result = ResponseCodeResult.FAIL;
        this.errorMessage = errorMessage;
        this.data = null;
    }
}
