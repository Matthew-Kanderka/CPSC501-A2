import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

public class InspectorTest {

    private static final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outStream));
    }

    @After
    public void restoreStreams() {
        outStream.reset();
    }

    @Test
    public void testGetSuperClassInfo_hasSuperClass() {
        new Inspector().getSuperClassInfo(String.class, "", false, 0 );
        String expected = "Super Class Name: java.lang.Object";
        assert(outStream.toString().contains(expected));
    }

    @Test
    public void testGetSuperClassInfo_ObjectClass() {
        new Inspector().getSuperClassInfo(Object.class, new Object(), false, 0 );
        String expected = "";
        assert(outStream.toString().equals(expected));
    }

    @Test
    public void testGetSuperClassInfo_noSuperClass() {
        new Inspector().getSuperClassInfo(Serializable.class, null, false, 0 );
        String expected = "No super class";
        assert(outStream.toString().contains(expected));
    }

    @Test
    public void testGetInterfaceInfo() {
        new Inspector().getInterfaceInfo(String.class, ""  ,false, 0);
        String expected = "Implements Interface: java.io.Serializable";
        assert(outStream.toString().contains(expected));
    }

    @Test
    public void testGetInterfaceInfo_noInterface() {
        new Inspector().getInterfaceInfo(Object.class, new Object()  ,false, 0);
        String expected = "";
        assert(outStream.toString().equals(expected));
    }

    @Test
    public void testGetConstructorInfo_withConstructor() {
        new Inspector().getConstructorInfo(String.class, "", 0);
        String expected = "Constructor Name: java.lang.String";
        assert (outStream.toString().contains(expected));

        expected = "Parameter Types: int";
        assert (outStream.toString().contains(expected));

        expected = "Modifiers: public";
        assert (outStream.toString().contains(expected));
    }

    @Test
    public void testGetMethodInfo() {
        new Inspector().getMethodInfo(String.class, "", 0);

        String expected = "Method Name: length";
        assert (outStream.toString().contains(expected));

        expected = "Return Type: int";
        assert (outStream.toString().contains(expected));

        expected = "Modifiers: public";
        assert (outStream.toString().contains(expected));

        expected = "Exceptions: java.io.UnsupportedEncodingException";
        assert (outStream.toString().contains(expected));

        expected = "Parameter Types: int";
        assert (outStream.toString().contains(expected));
    }

    @Test
    public void testGetFieldInfo() {
        new Inspector().getFieldInfo(String.class, "", false, 0);

        String expected = "Field Name: hash";
        assert (outStream.toString().contains(expected));

        expected = "Type: int";
        assert (outStream.toString().contains(expected));

        expected = "Modifiers: private";
        assert (outStream.toString().contains(expected));

        expected = "Value: 0";
        assert (outStream.toString().contains(expected));
    }

    @Test
    public void testInspectArray() {

        new Inspector().inspect("test", false);

        String expected = "Component Type: char";
        assert (outStream.toString().contains(expected));

        expected = "Array Length: 4";
        assert (outStream.toString().contains(expected));

        expected = "java.lang.Character";
        assert (outStream.toString().contains(expected));
    }

    @Test
    public void testFormatPrint_noTabs() {
        new Inspector().formattedPrint("test zero tab", 0);
        String expected = "test zero tab\r\n";
        assert (outStream.toString().equals(expected));
    }

    @Test
    public void testFormatPrint_oneTab() {
        new Inspector().formattedPrint("test one tab", 1);
        String expected = "\ttest one tab\r\n";
        assert (outStream.toString().equals(expected));
    }

    @Test
    public void testFormatPrint_twoTabs() {
        new Inspector().formattedPrint("test two tabs", 2);
        String expected = "\t\ttest two tabs\r\n";
        assert (outStream.toString().equals(expected));
    }

    @Test(expected = NullPointerException.class)
    public void testNullInspect() {
        new Inspector().inspect(null, true);
    }
}
