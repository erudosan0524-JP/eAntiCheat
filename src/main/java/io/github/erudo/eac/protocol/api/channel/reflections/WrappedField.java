package io.github.erudo.eac.protocol.api.channel.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lombok.Getter;

@Getter
public class WrappedField {

	private final WrappedClass parent;
	private final Field field;
	private final Class<?> type; //フィールドが持っている型

	public WrappedField(WrappedClass parent, Field field) {
		this.parent = parent;
		this.field = field;
		this.type = field.getType();
		this.field.setAccessible(true);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object parent) {
		try {
			return (T) this.field.get(parent);
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return null;

		}
	}

	public void set(Object parent, Object value) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(this.field, this.field.getModifiers() & ~Modifier.FINAL);
            this.field.setAccessible(true);
            this.field.set(parent, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annClass) {
        return field.isAnnotationPresent(annClass);
    }

    @SuppressWarnings("unchecked")
	public <T> T getAnnotation(Class<? extends Annotation> annClass) {
        return (T) field.getAnnotation(annClass);
    }

    public int getModifiers() {
        return this.field.getModifiers();
    }

}
