package episen.si.ing1.pds.backend.server;
public class ServerCoreConfig {

    private int listenPort;
    private int timeOut;

    public int getListenPort() {
        return listenPort;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return "ServerCoreConfig{" +
                "listenPort=" + listenPort +
                ", timeOut=" + timeOut +
                '}';
    }
}