package episen.si.ing1.pds.backend.server;


/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import java.util.logging.Logger;

public class BackendServer {
	
    /*private final static Logger logger = LoggerFactory.getLogger(BackendServer.class.getName());*/
	private final static Logger logserv = Logger.getLogger("Server Logger");
	
    public static void main(String[] args) {
        /*logger.info("backend marche");*/
    	logserv.info("server working");
    }
}
