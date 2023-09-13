package midik.Singletons;

import javax.swing.JList;

public class FrontendSingleton {
    private static FrontendSingleton instance;
    private JList<String> pistasJL;
    
    public static FrontendSingleton getInstance() {
        if (instance == null) {
            instance = new FrontendSingleton();
        }
        return instance;
    }

    public void setJlist(JList<String> jlist) {
        this.pistasJL = jlist;
    }

    public JList<String> getJlist() {
        return pistasJL;
    }

}
