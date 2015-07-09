package main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Grafico extends ApplicationFrame{

	private static final long serialVersionUID = 1L;

   public static void dibujar(String applicationTitle, String chartTitle, Double[] x, Double[] y) {
      Grafico chart = new Grafico( applicationTitle, chartTitle, x, y);

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
	
	private Grafico(String applicationTitle, String chartTitle, Double[] x, Double[] y) {

		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createLineChart(chartTitle,
				"Variable X", "Variable Y", createDataset(x, y),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

   private DefaultCategoryDataset createDataset( Double[] x, Double[] y ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      for( int i = 0 ; i < x.length ; i++ ){
    	  dataset.addValue( x[i] , "X&Y" , y[i] );
      }
      return dataset;
   }
}

