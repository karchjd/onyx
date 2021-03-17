package bayes.gui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import bayes.Chain;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.graphics.Orientation;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class PosteriorPlotWindow extends JFrame {

	public PosteriorPlotWindow(Chain chain) {
		this(chain, null);
	}
	
	public PosteriorPlotWindow(Chain chain, String[] parameterNames)
	{
		 
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setSize(800, 600);
		    
		 int numParams = chain.get(0).size();
		 
		 for (int j=0; j < numParams; j++) {
		  
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (int x =0; x < chain.getNumSamples(); x++) {
		    double y = chain.get(x).getValue(j);
		    if (y<min) min=y;
		    if (y>max) max=y;
		}
		
		int numBins = 20;
		
		double steps = (max-min)/numBins;
		
		int[] bins = new int[20];
		for (int x =0; x < chain.getNumSamples(); x++) {
		    double y = chain.get(x).getValue(j);
		    int binid = (int) Math.floor( (y-min)/steps);
		    bins[binid] = bins[binid] + 1;
		}
		
		DataTable data = new DataTable(Integer.class, Double.class);
		for (int i=0; i < bins.length; i++) {
			data.add(i, bins[i]);
		}
		
		BarPlot plot = new BarPlot(data);
		
		//LineRenderer lines = new DefaultLineRenderer2D();
		//plot.setLineRenderers(data, lines);
		
	/*	if (parameterNames==null) {
			plot.getTitle().setText("Parameter "+(j+1));
		} else {
			plot.getTitle().setText(parameterNames[j]);
		}
*/
	

	//	plot.getLineRenderers(data).get(0).setColor(Color.BLUE);
	//	plot.getPointRenderers(data).get(0).setColor(Color.BLUE);
		
		getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(new InteractivePanel(plot));
		//getContentPane().add(plot);
		
		 }
	}
}
