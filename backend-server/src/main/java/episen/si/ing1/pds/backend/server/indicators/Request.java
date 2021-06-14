package episen.si.ing1.pds.backend.server.indicators;

import com.fasterxml.jackson.databind.node.ObjectNode;


public class Request {
    private String requestEvent;
    private ObjectNode requestBody;

    public String getRequestEvent() {
        return requestEvent;
    }

    public void setRequestEvent(String requestEvent) {
        this.requestEvent = requestEvent;
    }

    public ObjectNode getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(ObjectNode requestBody) {
        this.requestBody = requestBody;
    }
}
