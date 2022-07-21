package studios.mcmodule.czf233;

public class Base64
{
    private String lineSeparator;
    private int lineLength;
    private static final char[] ENCODE;
    private static final int[] DECODE;
    private static final int IGNORE = -1;
    private static final int PAD = -2;
    private static final Base64 BASE64;
    
    public Base64() {
        this.lineSeparator = System.getProperty("line.separator");
        this.lineLength = 0;
    }
    
    public String encode(final byte[] bin) {
        return this.encode(bin, 0, bin.length);
    }
    
    public String encode(final byte[] bin, final int str, final int len) {
        int ol = (len + 2) / 3 * 4;
        if (this.lineLength != 0) {
            final int lines = (ol + this.lineLength - 1) / this.lineLength - 1;
            if (lines > 0) {
                ol += lines * this.lineSeparator.length();
            }
        }
        final StringBuffer sb = new StringBuffer(ol);
        int lp = 0;
        final int el = len / 3 * 3;
        final int ll = len - el;
        for (int xa = 0; xa < el; xa += 3) {
            if (this.lineLength != 0) {
                lp += 4;
                if (lp > this.lineLength) {
                    sb.append(this.lineSeparator);
                    lp = 4;
                }
            }
            int cv = bin[xa + str + 0] & 0xFF;
            cv <<= 8;
            cv |= (bin[xa + str + 1] & 0xFF);
            cv <<= 8;
            cv |= (bin[xa + str + 2] & 0xFF);
            final int c3 = cv & 0x3F;
            cv >>>= 6;
            final int c4 = cv & 0x3F;
            cv >>>= 6;
            final int c5 = cv & 0x3F;
            cv >>>= 6;
            final int c6 = cv & 0x3F;
            sb.append(Base64.ENCODE[c6]);
            sb.append(Base64.ENCODE[c5]);
            sb.append(Base64.ENCODE[c4]);
            sb.append(Base64.ENCODE[c3]);
        }
        if (this.lineLength != 0 && ll > 0) {
            lp += 4;
            if (lp > this.lineLength) {
                sb.append(this.lineSeparator);
                lp = 4;
            }
        }
        if (ll == 1) {
            sb.append(this.encode(new byte[] { bin[el + str], 0, 0 }).substring(0, 2)).append("==");
        }
        else if (ll == 2) {
            sb.append(this.encode(new byte[] { bin[el + str], bin[el + str + 1], 0 }).substring(0, 3)).append("=");
        }
        if (ol != sb.length()) {
            throw new RuntimeException("Error in studios.mcmodule.czf233.Base64 encoding method: Calculated output length of " + ol + " did not match actual length of " + sb.length());
        }
        return sb.toString();
    }
    
    public byte[] decode(final String b64) {
        return this.decode(b64, 0, b64.length());
    }
    
    public byte[] decode(final String b64, final int str, final int len) {
        byte[] ba = new byte[len / 4 * 3];
        int dc = 0;
        int rv = 0;
        int ol = 0;
        int pc = 0;
        for (int xa = 0; xa < len; ++xa) {
            final int ch = b64.charAt(xa + str);
            int value = (ch <= 255) ? Base64.DECODE[ch] : -1;
            if (value != -1) {
                if (value == -2) {
                    value = 0;
                    ++pc;
                }
                switch (dc) {
                    case 0: {
                        rv = value;
                        dc = 1;
                        break;
                    }
                    case 1: {
                        rv <<= 6;
                        rv |= value;
                        dc = 2;
                        break;
                    }
                    case 2: {
                        rv <<= 6;
                        rv |= value;
                        dc = 3;
                        break;
                    }
                    case 3: {
                        rv <<= 6;
                        rv |= value;
                        ba[ol + 2] = (byte)rv;
                        rv >>>= 8;
                        ba[ol + 1] = (byte)rv;
                        rv >>>= 8;
                        ba[ol] = (byte)rv;
                        ol += 3;
                        dc = 0;
                        break;
                    }
                }
            }
        }
        if (dc != 0) {
            throw new ArrayIndexOutOfBoundsException("studios.mcmodule.czf233.Base64 data given as input was not an even multiple of 4 characters (should be padded with '=' characters).");
        }
        ol -= pc;
        if (ba.length != ol) {
            final byte[] b65 = new byte[ol];
            System.arraycopy(ba, 0, b65, 0, ol);
            ba = b65;
        }
        return ba;
    }
    
    public void setLineLength(final int len) {
        this.lineLength = len / 4 * 4;
    }
    
    public void setLineSeparator(final String linsep) {
        this.lineSeparator = linsep;
    }
    
    public static String toString(final byte[] dta) {
        return Base64.BASE64.encode(dta);
    }
    
    public static String toString(final byte[] dta, final int str, final int len) {
        return Base64.BASE64.encode(dta, str, len);
    }
    
    public static byte[] toBytes(final String b64) {
        return Base64.BASE64.decode(b64);
    }
    
    public static byte[] toBytes(final String b64, final int str, final int len) {
        return Base64.BASE64.decode(b64, str, len);
    }
    
    static {
        ENCODE = new char[64];
        DECODE = new int[256];
        BASE64 = new Base64();
        for (int xa = 0; xa <= 25; ++xa) {
            Base64.ENCODE[xa] = (char)(65 + xa);
        }
        for (int xa = 0; xa <= 25; ++xa) {
            Base64.ENCODE[xa + 26] = (char)(97 + xa);
        }
        for (int xa = 0; xa <= 9; ++xa) {
            Base64.ENCODE[xa + 52] = (char)(48 + xa);
        }
        Base64.ENCODE[62] = '+';
        Base64.ENCODE[63] = '/';
        for (int xa = 0; xa < 256; ++xa) {
            Base64.DECODE[xa] = -1;
        }
        for (int xa = 0; xa < 64; ++xa) {
            Base64.DECODE[Base64.ENCODE[xa]] = xa;
        }
        Base64.DECODE[61] = -2;
    }
}
