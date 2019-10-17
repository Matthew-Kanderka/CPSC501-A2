public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {

        System.out.println("Class Name: " + c.getName());

        getSuperClassInfo(c, obj, recursive, depth);
        getInterfaceInfo(c, obj, recursive, depth);
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

}
