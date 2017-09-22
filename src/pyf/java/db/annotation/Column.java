package pyf.java.db.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author puyafeng 注解bean的字段
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 对应数据库中表的字段名
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 设置字段长度
	 * 
	 * @return
	 */
	String size() default "";

	/**
	 * 是否为主键
	 * 
	 * @return
	 */
	boolean isId() default false;

	/**
	 * 是否允许为空
	 * 
	 * @return
	 */
	boolean nullable() default true;

	/**
	 * 是否唯一
	 * 
	 * @return
	 */
	boolean unique() default false;

	/**
	 * 主键生成方式
	 * 
	 * @return
	 */
	GenType genId() default GenType.AUTO_INC;

	/**
	 * 指定数据库字段类型
	 * 
	 * @return
	 */
	JdbcType type() default JdbcType.AUTO;

	/**
	 * 主键生成方式
	 * 
	 * @author puyafeng
	 *
	 */
	public enum GenType {
		/**
		 * 用户控制
		 */
		AUTO,
		/**
		 * 自动增长
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
