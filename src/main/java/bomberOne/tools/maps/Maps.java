package bomberOne.tools.maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum Maps {

    /**
     * 
     */
    MAP1("mappa.csv");

    private static final String RES_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    private List<List<String>> list = new ArrayList<>();
    private String fileName;

    Maps(final String string) {
        this.fileName = string;
    }

    public void setList(final List<List<String>> list) {
        this.list = list;
    }

    public List<List<String>> getList() {
        return this.list;
    }

    public String getFilePath() {
        return RES_PATH + this.fileName;
    }
}