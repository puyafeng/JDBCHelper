package pyf.java.db.convert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

import pyf.java.db.annotation.Column;

public class Map2Bean {
	
	/**
	 * map转bean（优先转注解的name值）
	 * @param map
	 * @param cls
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T parseWithAnnotation(Map<?, ?> map, Class<T> cls)
			throws InstantiationException, IllegalAccessException {
		T t = null;
		if (hasNoParamConstructor(cls)) {
			t = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				setValue(t, field, map.get(getAnnoNameOrFieldName(field)));
			}
		} else {
			throw new RuntimeException(cls.getName() + "缺少无参构造器");
		}
		return t;
	}

	/**
	 * 根据指定的字符串找到符合该字符串的字段（先用注解匹配，如果不匹配改用字段名匹配）
	 * 
	 * @param field
	 * @return
	 */
	public static String getAnnoNameOrFieldName(Field field) {
		Column t = field.getAnnotation(Column.class);
		String name = field.getName();
		if (t != null) {
			name = t.name();
			if ("".equals(name)) {
				name = field.getName();
			}
			return name;
		}
		return name;
	}

	/**
	 * 为对象的字段赋值
	 * 
	 * @param instance
	 * @param field
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValue(Object instance, Field field, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		try {
			if(value != null){
				field.set(instance, value);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 是否有无参构造函数
	 * 
	 * @param cls
	 * @return
	 */
	public static boolean hasNoParamConstructor(Class<?> cls) {
		boolean isOK = false;
		Constructor<?>[] cons = cls.getConstructors();
		for (Constructor<?> con : cons) {
			int count = con.getParameterCount();
			if (count == 0) {
				isOK = true;
				break;
			}
		}
		return isOK;
	}
}
