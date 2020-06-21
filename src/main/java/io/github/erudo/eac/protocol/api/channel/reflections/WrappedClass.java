package io.github.erudo.eac.protocol.api.channel.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;

//複雑化されたクラスの中身を取得するためのクラス
//様々な機能が簡略化されたメソッドが内包されている
@Getter
public class WrappedClass {

	@SuppressWarnings("rawtypes")
	private final Class parent;

	public WrappedClass(@SuppressWarnings("rawtypes") Class parent) {
		this.parent = parent;
	}

	public WrappedField getFieldByName(String name) {
		Field tempField = null;
		for(Field field : this.parent.getDeclaredFields()) {
			if(field.getName().equals(name)) {
				tempField = field;
				break;
			}
		}

		if(tempField != null) {
			tempField.setAccessible(true);
			return new WrappedField(this, tempField);
		}

		return null;
	}


	@SuppressWarnings("unchecked")
	public <T> WrappedConstructor getConstructor(Class<T>... types) {
        try {
            return new WrappedConstructor(this, this.parent.getDeclaredConstructor(types));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<WrappedField> getFields(@SuppressWarnings("unchecked") Predicate<WrappedField>... parameters) {
        return getFields()
                .stream()
                .filter(field -> Arrays.stream(parameters).allMatch(param -> param.test(field)))
                .collect(Collectors.toList());
    }

    public List<WrappedMethod> getMethods(@SuppressWarnings("unchecked") Predicate<WrappedMethod>... parameters) {
        return getMethods()
                .stream()
                .filter(method -> Arrays.stream(parameters).allMatch(param -> param.test(method)))
                .collect(Collectors.toList());
    }

    public List<WrappedConstructor> getConstructors() {
        return Arrays.stream(this.parent.getConstructors())
                .map(construct -> new WrappedConstructor(this, construct))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("rawtypes")
	public WrappedConstructor getConstructor() {
        val optional = Arrays.stream(this.parent.getConstructors()).filter(cons -> cons.getParameterCount() == 0).findFirst();
        return optional.map(constructor -> new WrappedConstructor(this, constructor)).orElse(null);
    }

    public WrappedConstructor getConstructorAtIndex(int index) {
        return new WrappedConstructor(this, this.parent.getConstructors()[index]);
    }

    @SuppressWarnings("unchecked")
	public boolean isAnnotationPresent(Class<? extends Annotation> annClass) {
        return parent.isAnnotationPresent(annClass);
    }

	@SuppressWarnings("unchecked")
	public <T> T getAnnotation(Class<T> annClass) {
		for(Annotation anno : parent.getDeclaredAnnotations()) {
			if(anno.annotationType().equals(annClass)) {
				return (T) anno;
			}
		}
		return null;
    }

    public WrappedField getFieldByType(Class<?> type, int index) {
        for (Field field : this.parent.getDeclaredFields()) {
            if (field.getType().equals(type) && index-- <= 0) {
                return new WrappedField(this, field);
            }
        }
        throw new NullPointerException("Could not find field with type " + type.getSimpleName() + " at index " + index);
    }

    public WrappedField getFirstFieldByType(Class<?> type) {
        return this.getFieldByType(type, 0);
    }

    public WrappedMethod getMethod(String name, @SuppressWarnings("rawtypes") Class... parameters) {
        for (Method method : this.parent.getDeclaredMethods()) {
            if (!method.getName().equals(name) || parameters.length != method.getParameterTypes().length) {
                continue;
            }
            boolean same = true;
            for (int x = 0; x < method.getParameterTypes().length; x++) {
                if (method.getParameterTypes()[x] != parameters[x]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return new WrappedMethod(this, method);
            }
        }
        for (Method method : this.parent.getMethods()) {
            if (!method.getName().equals(name) || parameters.length != method.getParameterTypes().length) {
                continue;
            }
            boolean same = true;
            for (int x = 0; x < method.getParameterTypes().length; x++) {
                if (method.getParameterTypes()[x] != parameters[x]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return new WrappedMethod(this, method);
            }
        }
        return null;
    }

    public WrappedMethod getDeclaredMethodByType(Class<?> type, int index) {
        for (Method method : this.parent.getDeclaredMethods()) {
            if(method.getReturnType().equals(type) && index-- <= 0) {
                return new WrappedMethod(this, method);
            }
        }
        throw new NullPointerException("Could not find method with return type " + type.getSimpleName() + " at index " + index);
    }

    public WrappedMethod getMethodByType(Class<?> type, int index) throws NullPointerException {
        for (Method method : this.parent.getMethods()) {
            if(method.getReturnType().equals(type) && index-- <= 0) {
                return new WrappedMethod(this, method);
            }
        }
        System.out.println("Shit didnt get: " + type.getName());
        throw new NullPointerException("Could not find method with return type " + type.getSimpleName()
                + " at index " + index);
    }

    //We have a separate method instead of just calling WrappedClass#getMethods(boolean, boolean)
    //for performance reasons.
    public List<WrappedMethod> getMethods() {
        return Arrays.stream(parent.getMethods())
                .map(method -> new WrappedMethod(this, method))
                .collect(Collectors.toList());
    }

    public List<WrappedMethod> getMethods(boolean noStatic, boolean noFinal) {
        return Arrays.stream(parent.getMethods())
                .filter(method ->
                        (!noFinal || !Modifier.isFinal(method.getModifiers())
                                && (!noStatic || !Modifier.isStatic(method.getModifiers()))))
                .map(method -> new WrappedMethod(this, method))
                .collect(Collectors.toList());

    }

    public List<WrappedMethod> getMethods(boolean noStatic) {
        return getMethods(noStatic, false);
    }

    //We have a separate method instead of just calling WrappedClass#getFields(boolean, boolean)
    // or performance reasons.
    public List<WrappedField> getFields() {
        return Arrays.stream(parent.getDeclaredFields())
                .map(field -> new WrappedField(this, field))
                .collect(Collectors.toList());
    }

    public List<WrappedField> getFields(boolean noStatic, boolean noFinal) {
        return Arrays.stream(parent.getFields())
                .filter(field ->
                        (!noFinal || !Modifier.isFinal(field.getModifiers())
                                && (!noStatic || !Modifier.isStatic(field.getModifiers()))))
                .map(field -> new WrappedField(this, field))
                .collect(Collectors.toList());
    }

    public List<WrappedField> getFields(boolean noStatic) {
        return getFields(noStatic, false);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Enum getEnum(String name) {
        return Enum.valueOf(this.parent, name);
    }
}
