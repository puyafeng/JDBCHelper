package pyf.java.db.convert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

import pyf.java.db.annotation.Column;

public class Map2Bean {
	
	/**
	 * mapתbean������תע���nameֵ��
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
			throw new RuntimeException(cls.getName() + "ȱ���޲ι�����");
		}
		return t;
	}

	/**
	 * ����ָ�����ַ����ҵ����ϸ��ַ������ֶΣ�����ע��ƥ�䣬�����ƥ������ֶ���ƥ�䣩
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
	 * Ϊ������ֶθ�ֵ
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
	 * �Ƿ����޲ι��캯��
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
