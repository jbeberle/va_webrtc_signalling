package gov.va.signalling;

import lombok.Data;

@Data
public class Message {
    private MessageType type;
    private String calleeId;
    private String sdpOffer;

    private String room;

    public Message() {
    }

    public Message(MessageType type, String calleeId, String sdpOffer, String room) {
        this.type = type;
        this.calleeId = calleeId;
        this.sdpOffer = sdpOffer;
        this.room = room;
    }
}
