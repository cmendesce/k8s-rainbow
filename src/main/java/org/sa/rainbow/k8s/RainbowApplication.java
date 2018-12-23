package org.sa.rainbow.k8s;

import org.sa.rainbow.k8s.translator.probes.ProbeLoader;
import org.sa.rainbow.core.RainbowDelegate;
import org.sa.rainbow.core.RainbowMaster;
import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.gui.RainbowGUI;

public class RainbowApplication {
  public static void main(String[] args) {
    System.out.println("running application in docker and java 11");
    try {
      RainbowMaster master = new RainbowMaster(null, null, new ProbeLoader());
      master.initialize ();
      master.start ();

      RainbowDelegate delegate = new RainbowDelegate();
      delegate.initialize();
      delegate.start();

      RainbowGUI gui = new RainbowGUI(master);
      gui.display();
    } catch (RainbowException e) {
      e.printStackTrace();
    }
  }
}
