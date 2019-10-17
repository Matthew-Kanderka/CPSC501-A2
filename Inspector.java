import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {

        System.out.println("Class Name: " + c.getName());

        getSuperClassInfo(c, obj, recursive, depth);
        getInterfaceInfo(c, obj, recursive, depth);
        getConstructorInfo(c, obj, depth);
        getMethodInfo(c, obj, depth);
        getFieldInfo(c, obj, depth);
    }

    private void getSuperClassInfo(Class c, Object obj, boolean recursive, int depth) {

        if (c.equals(Object.class)) {
            return;
        }

        Class superClass = c.getSuperclass();

        if (superClass != null) {
            System.out.println("Super Class Name: " + superClass.getName());

            inspectClass(superClass, obj, recursive, depth + 1);
        } else {
            System.out.println("No super class");
        }
    }

    private void getInterfaceInfo(Class c, Object obj, boolean recursive, int depth) {

        Class[] interfaces = c.getInterfaces();

        for (Class interf: interfaces) {

            System.out.println("Implements Interface: " + interf.getName());
            inspectClass(interf, obj, recursive, depth + 1);
        }
    }

    private void getConstructorInfo(Class c, Object obj, int depth) {

        Constructor[] constructors = c.getConstructors();

        for (Constructor constructor: constructors) {
            System.out.println("Constructor Name: " + constructor.getName());

            System.out.println("Constructor Parameter Types: ");
            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class pt: parameterTypes) {
                System.out.print(pt.getName());
            }

            System.out.println("Constructor Modifiers: " + constructor.getModifiers());
        }
    }

    private void getMethodInfo(Class c, Object obj, int depth) {

        Method[] methods = c.getMethods();

        for (Method m: methods) {
            System.out.println("Method Name: " + m.getName());

            System.out.println("Exceptions Thrown:");
            Class[] exceptions = m.getExceptionTypes();
            for (Class ex: exceptions) {
                System.out.print(ex.getName());
            }

            System.out.println("Parameter Types:");
            Class[] parameterTypes = m.getParameterTypes();
            for (Class pt: parameterTypes) {
                System.out.print(pt.getName());
            }

            System.out.println("Return Type: " + m.getReturnType().getName());

            System.out.println("Modifiers: " + m.getModifiers());
        }
    }

    private void getFieldInfo(Class c, Object obj, int depth) {

    }
}
