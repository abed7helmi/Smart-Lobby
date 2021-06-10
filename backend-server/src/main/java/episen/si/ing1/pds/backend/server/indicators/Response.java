package episen.si.ing1.pds.backend.server.indicators;

public class Response {
    private String responseEvent;
    private Object responseBody;


    public String getResponseEvent() {
        return responseEvent;
    }

    public void setResponseEvent(String responseEvent) {
        this.responseEvent = responseEvent;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
}
