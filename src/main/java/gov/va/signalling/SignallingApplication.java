package gov.va.signalling;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.corundumstudio.socketio.SocketIOServer;


@SpringBootApplication
//public class SignallingApplication implements CommandLineRunner {
public class SignallingApplication {
//    private final SocketIOServer server;
//
//    public SignallingApplication(SocketIOServer server) {
//        this.server = server;
//    }

    public static void main(String[] args) {
         SpringApplication.run(SignallingApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        server.start();
//    }


}

