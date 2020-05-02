import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;

public class EcouteurScroll implements AdjustmentListener {

	private JFrame hamecon;

	public EcouteurScroll(JFrame hamecon) {
		this.hamecon = hamecon;
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		hamecon.repaint();
	}
}