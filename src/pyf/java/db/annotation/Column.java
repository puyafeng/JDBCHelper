package pyf.java.db.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author puyafeng ע��bean���ֶ�
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * ��Ӧ���ݿ��б���ֶ���
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * �����ֶγ���
	 * 
	 * @return
	 */
	String size() default "";

	/**
	 * �Ƿ�Ϊ����
	 * 
	 * @return
	 */
	boolean isId() default false;

	/**
	 * �Ƿ�����Ϊ��
	 * 
	 * @return
	 */
	boolean nullable() default true;

	/**
	 * �Ƿ�Ψһ
	 * 
	 * @return
	 */
	boolean unique() default false;

	/**
	 * �������ɷ�ʽ
	 * 
	 * @return
	 */
	GenType genId() default GenType.AUTO_INC;

	/**
	 * ָ�����ݿ��ֶ�����
	 * 
	 * @return
	 */
	JdbcType type() default JdbcType.AUTO;

	/**
	 * �������ɷ�ʽ
	 * 
	 * @author puyafeng
	 *
	 */
	public enum GenType {
		/**
		 * �û�����
		 */
		AUTO,
		/**
		 * �Զ�����
		 */
		AUTO_INC,
		/**
		 * UUID
		 */
		UUID
	}

	public enum JdbcType {
		AUTO, VARCHAR, INTEGER, DECIMAL, BOOLEAN, TIME_STAMP
	}
}
