package gov.va.signalling.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import gov.va.signalling.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SocketModule {

    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        System.out.println("Constructor called");
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("makeCall", Message.class, onMakeCall());
    }

    private DataListener<Message> onMakeCall() {
        return (senderClient, data, ackSender) -> {
            System.out.println("Making call...");
            log.info(data.toString());
            senderClient.getNamespace().getBroadcastOperations().sendEvent("newCall", data.getCalleeId());
               var calleeId = data.getCalleeId();
               var sdpOffer = data.getSdpOffer();
               socketService.sendMessage(data.getRoom(), "newCall", senderClient, calleeId, sdpOffer);
        };
    }

    private DataListener<Message> onNewCall() {
//        return (senderClient, data, ackSender) -> {
//            log.info(data.toString());
//            senderClient.getNamespace().getBroadcastOperations().sendEvent("get_message", data.getMessage());
//
//        };
        System.out.println("onNewCall");
        return (client, data, ackSender) -> {
            client.joinRoom(data.getCalleeId());
            System.out.println("onNewCall: " + client.getSessionId().toString());
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };

    }

    private ConnectListener onConnected() {
//        return (client) -> {
//            System.out.println("Socket ID[{}] Connected to socket" + client.getSessionId().toString());
//            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
//        };
        System.out.println("Connected to socket" );
//        return (client) -> {
//            System.out.println("client");
//            System.out.println("Socket ID[{}] Connected to socket" + client.getSessionId().toString());
//            String destCallerId = client.getHandshakeData().getSingleUrlParam("destCallerId");
//            String room = client.getHandshakeData().getSingleUrlParam("room");
//            Map<String, List<String>> params =  client.getHandshakeData().getUrlParams();
//            params.forEach((key, value) -> System.out.println("key="+key+", value="+value));
//            System.out.println("callerId="+room);
//            //client.joinRoom(room);
//            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
//        };

        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            System.out.println("room=" + room);
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());

        };
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.sendMessage(data.getRoom(),"get_message", senderClient, data.getSdpOffer(), data.getCalleeId());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            System.out.println("Socket Disconnected");
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}
