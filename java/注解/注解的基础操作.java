package po;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;

/**
 * 注解的使用
 * 1.注解一个类  在声明类的上面
 * 2.注解一个field  在field的上面
 * 3.注解一个方法,  在声明方法的上面
 * 4.注解一个局部变量  在局部变量的上面
 * 5.注解一个参数,  将参数的类型写成注解
 * @Inherited 表示注解是可被继承的
 * 
 * @RetenTion的属性
 *	RetentionPolicy.SOURCE : 注解只存在于源码中，不会存在于.class文件中，在编译时会被忽略掉
	RetentionPolicy.CLASS：注解只存在于.class文件中，在编译期有效，但是在运行期会被忽略掉，这也是默认范围
	RetentionPolicy.RUNTIME：在运行期有效，JVM在运行期通过反射获得注解信息
 * 
 * @Target的属性
 * 	ElementType.ANNOTATION_TYPE 注解一个注解类的
    ElementType.CONSTRUCTOR  注解一个构造的类
    ElementType.FIELD 注解一个属性的
    ElementType.LOCAL_VARIABLE	注解一个全局变量的
    ElementType.METHOD 注解一个方法的
    ElementType.PACKAGE 注解一个包的
    ElementType.PARAMETER 注解一个参数类型之前
    ElementType.TYPE  注解任意类型
 * @author raymond-du
 *
 */

@Optional("类的注解")
class Test{
	@Optional("field的注解")
	private String strTest;
	
	@Optional("构造函数的注解")
	public Test() {
		strTest="woaini";
	}
	
	@Optional("方法的注解")
	void setStrTest(@Optional("参数的注解") String strTest) {
		this.strTest=strTest;
	}
	
	//压制clz的警告
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		//加载并获取类信息
		Class clz= Test.class;
		//获取类的注解
		Optional clzAnno =(Optional) clz.getAnnotation(Optional.class);
		System.out.println(clzAnno.value());
		
		//获取filed的注解
		Field field=Test.class.getDeclaredField("strTest");
		Optional fieldAnno=field.getAnnotation(Optional.class);
		System.out.println(fieldAnno.value());
		
		//其他的同上
	}
}

//根据上面的ElementType选择注解的类型
@Retention(RetentionPolicy.RUNTIME)
@interface Optional{
	String value() default "Annotation";
}
