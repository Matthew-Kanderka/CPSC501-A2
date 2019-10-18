import java.lang.reflect.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {

        formattedPrint("Class Name: " + c.getName(), depth);

        getSuperClassInfo(c, obj, recursive, depth);
        getInterfaceInfo(c, obj, recursive, depth);
        getConstructorInfo(c, obj, depth);
        getMethodInfo(c, obj, depth);
        getFieldInfo(c, obj, recursive, depth);
    }

    private void getSuperClassInfo(Class c, Object obj, boolean recursive, int depth) {

        if (c.equals(Object.class)) {
            return;
        }

        Class superClass = c.getSuperclass();

        if (superClass != null) {
            formattedPrint("Super Class Name: " + superClass.getName(), depth);
            inspectClass(superClass, obj, recursive, depth + 1);
        } else {
            formattedPrint("No super class", depth);
        }
    }

    private void getInterfaceInfo(Class c, Object obj, boolean recursive, int depth) {

        Class[] interfaces = c.getInterfaces();

        for (Class interf: interfaces) {
            formattedPrint("Implements Interface: " + interf.getName(), depth);
            inspectClass(interf, obj, recursive, depth + 1);
        }
    }

    private void getConstructorInfo(Class c, Object obj, int depth) {

        Constructor[] constructors = c.getDeclaredConstructors();

        for (Constructor constructor: constructors) {
            formattedPrint("Constructor Name: " + constructor.getName(), depth);

            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class pt: parameterTypes) {
                formattedPrint("Parameter Types: " + pt.getName(), depth+1);
            }

            formattedPrint("Modifiers: " + Modifier.toString(constructor.getModifiers()), depth+1);
        }
    }

    private void getMethodInfo(Class c, Object obj, int depth) {

        Method[] methods = c.getDeclaredMethods();

        for (Method m: methods) {
            formattedPrint("Method Name: " + m.getName(), depth);

            Class[] exceptions = m.getExceptionTypes();
            for (Class ex: exceptions) {
                formattedPrint("Exceptions Thrown: " + ex.getName(), depth+1);
            }

            Class[] parameterTypes = m.getParameterTypes();
            for (Class pt: parameterTypes) {
                formattedPrint("Parameter Types: " + pt.getName(), depth+1);
            }

            formattedPrint("Return Type: " + m.getReturnType().getName(), depth+1);
            formattedPrint("Modifiers: " + Modifier.toString(m.getModifiers()), depth+1);
        }
    }

    private void getFieldInfo(Class c, Object obj, boolean recursive, int depth) {

        Field[] fields = c.getDeclaredFields();

        for (Field f: fields) {
            formattedPrint("Field Name: " + f.getName(), depth);
            formattedPrint("Type: " + f.getType().getName(), depth+1);
            formattedPrint("Modifiers: " + Modifier.toString(f.getModifiers()), depth+1);

            f.setAccessible(true);

            Object value = null;
            try {
                value = f.get(obj);
            } catch (IllegalAccessException e) {
                System.out.println("Cannot Access Field");
            }

            if (f.getType().isArray()) {
                inspectArray(f.getType(), value, recursive, depth);
            } else if (f.getType().isPrimitive()) {
                formattedPrint("Value: " + value, depth+1);
            } else {
                if (recursive) {
                    inspectClass(f.getType(), value, recursive, depth + 1);
                } else {
                    formattedPrint("Reference Value: " + f.getType().getName() + "@" + f.hashCode(), depth);
                }
            }
        }
    }

    private void inspectArray(Class c, Object obj, boolean recursive, int depth) {

        Class componentType = c.getComponentType();
        formattedPrint("Component Type: " + componentType.getName(), depth);

        int arrayLength = Array.getLength(obj);
        formattedPrint("Array Length: " + arrayLength, depth);

        for (int i = 0; i < arrayLength; i++) {
            Object object = Array.get(obj, i);

            if (componentType.isPrimitive()) {
                formattedPrint(object.getClass().getName(), depth);
            } else if (componentType.isArray()) {
                inspectArray(object.getClass(), object, recursive, 1);
            } else {
                if (recursive) {
                    inspectClass(object.getClass(), object, recursive, depth + 1);
                } else {
                    formattedPrint("Reference Value: " + object.getClass().getName() + "@" + object.hashCode(), depth);
                }
            }
        }
    }

    private void formattedPrint(String message, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.println(message);
    }
}
