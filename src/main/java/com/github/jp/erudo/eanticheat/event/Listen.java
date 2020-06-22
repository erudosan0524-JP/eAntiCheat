package com.github.jp.erudo.eanticheat.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//ListenerPriorityを実際にListenするときに適応させるためのアノテーションインターフェイス
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Listen {
	ListenerPriority priority() default ListenerPriority.NORMAL;
	boolean ignoreCancelled() default false;

}
