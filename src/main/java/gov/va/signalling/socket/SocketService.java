package gov.va.signalling.socket;

import com.corundumstudio.socketio.SocketIOClient;
import gov.va.signalling.Message;
import gov.va.signalling.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SocketService {

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String calleeId, String sdpOffer) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.SERVER, calleeId, sdpOffer, room));
            }
        }
    }

}