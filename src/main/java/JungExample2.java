/*import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JFrame;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;

public class JungExample2 {
    public static void main(int[][]matrix,List<String> requests) {

        UndirectedSparseGraph<String, String> g = new UndirectedSparseGraph<String, String>();

        char letter = 'a';
        for(int i=0;i<matrix.length;i++) {
            for(int j=0;j<matrix.length - i;j++) {
                if(matrix[i][j]>0){
                    g.addVertex(requests.get(i));
                    g.addEdge("" + letter, requests.get(i), requests.get(j));
                    letter++;
                }else {
                    g.addVertex(requests.get(i));
                }
            }
        }


        VisualizationViewer<String, String> vv =
                new VisualizationViewer<String, String>(new CircleLayout<String, String>(g), new Dimension(400, 400));

        vv.getRenderContext().setVertexLabelTransformer(new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        });
        vv.getRenderContext().setEdgeLabelTransformer(new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        });

        vv.getRenderer().setVertexRenderer(new MyRenderer());

        final DefaultModalGraphMouse<String, Number> graphMouse = new DefaultModalGraphMouse<String, Number>();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(graphMouse);

        JFrame frame = new JFrame();
        frame.getContentPane().add(vv);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    static class MyRenderer implements Renderer.Vertex<String, String> {
        @Override
        public void paintVertex(RenderContext<String, String> rc, Layout<String, String> layout, String vertex) {
            GraphicsDecorator graphicsContext = rc.getGraphicsContext();
            Point2D center = layout.transform(vertex);
            Shape shape = null;
            Color color = null;
            if(vertex.equals("Square")) {
                shape = new Rectangle((int) center.getX() - 10, (int) center.getY() - 10, 20, 20);
                color = new Color(127, 127, 0);
            } else if(vertex.equals("Rectangle")) {
                shape = new Rectangle((int) center.getX() - 10, (int) center.getY() - 20, 20, 40);
                color = new Color(127, 0, 127);
            } else {
                shape = new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20);
                color = new Color(0, 127, 127);
            }
            graphicsContext.setPaint(color);
            graphicsContext.fill(shape);
        }

    }
}
*/