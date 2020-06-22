package io.github.erudo.eac.protocol.packet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.erudo.eac.protocol.api.channel.reflections.WrappedClass;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedMethod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Getter
@RequiredArgsConstructor
public class GeneralWrapper {

	private List<GeneralField> fields = new ArrayList<>();
    private List<WrappedMethod> methods;
    private final WrappedClass objectClass;

    public GeneralWrapper(Object object) {
        objectClass = new WrappedClass(object.getClass());
        methods = objectClass.getMethods();

        objectClass.getFields().forEach(field -> fields.add(new GeneralField(field, field.get(object))));
    }

    public <T> T build(Object... args) {
        val classes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);

        return objectClass.getConstructor(classes).newInstance(args);
    }

}
