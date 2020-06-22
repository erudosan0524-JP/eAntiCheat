package io.github.erudo.eac.protocol.packet.types;

import com.github.jp.erudo.eanticheat.utils.boundingbox.box.ReflectionUtils;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedChatMessage extends NMSObject {
    private static String type = Type.CHATMESSAGE;

    private String chatMessage;
    private Object[] objects;

    private static FieldAccessor<String> messageField = fetchField(type, String.class, 0);
    private static FieldAccessor<Object[]> objectsField = fetchField(type, Object[].class, 0);

    public WrappedChatMessage(String chatMessage, Object... object) {
        this.chatMessage = chatMessage;
        this.objects = object;
    }

    public WrappedChatMessage(String chatMessage) {
        this(chatMessage, new Object[]{});
    }

    public void setPacket(String packet, Object... args) {
        Class<?> chatMsgClass = ReflectionUtils.getClass(type);

        Object o = ReflectionUtils.newInstance(chatMsgClass, packet, args);

        setObject(o);
    }

    public WrappedChatMessage(Object object) {
        super(object);

        chatMessage = fetch(messageField);
        objects = fetch(objectsField);
    }

    @Override
    public void updateObject() {

    }
}
