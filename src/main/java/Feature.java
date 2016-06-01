/**
 * Created by boris on 04.05.16.
 */
public class Feature {
    private String string;
    private double pWriter;
    private double pScientist;

    public double getpScientist() {
        return pScientist;
    }

    public void setpScientist(double pScientist) {
        this.pScientist = pScientist;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public double getpWriter() {
        return pWriter;
    }

    public void setpWriter(double pWriter) {
        this.pWriter = pWriter;
    }

    public Feature(String string) {
        this.string = string;
    }
}
