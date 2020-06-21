package com.github.jp.erudo.eanticheat.utils;

import lombok.Getter;

//複雑化されたクラスの中身を取得するためのクラス
//様々な機能が簡略化されたメソッドが内包されている
@Getter
public class WrappedClass {

	private final Class<?> parent;

	public WrappedClass(Class<?> parent) {
		this.parent = parent;
	}
}
