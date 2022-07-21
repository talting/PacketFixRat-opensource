package studios.mcmodule.czf233;

public class CzfClassLoader extends ClassLoader
{
    public Class<?> getClass(final byte[] buff) {
        return super.defineClass(buff, 0, buff.length);
    }
}
