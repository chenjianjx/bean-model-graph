package forDoc.dot;

import forDoc.model.One;
import forDoc.model.OneTwoThreePackageAnchor;
import org.beanmodelgraph.constructor.BeanModelGraphConstructor;
import org.beanmodelgraph.constructor.model.BmgGraph;
import org.beanmodelgraph.drawer.dot.BeanModelGraphDotDrawer;
import org.beanmodelgraph.drawer.dot.render.impl.DefaultDotGraphRenderer;

import java.util.Arrays;
import java.util.HashSet;

public class DotDrawer123Main {

    public static void main(String[] args) {
        BeanModelGraphConstructor graphConstructor = new BeanModelGraphConstructor(One.class,
                Arrays.asList(OneTwoThreePackageAnchor.class.getPackage().getName()),
                new HashSet<>());
        BeanModelGraphDotDrawer dotDrawer = new BeanModelGraphDotDrawer(new DefaultDotGraphRenderer());

        BmgGraph bmgGraph = graphConstructor.construct();
        String dotScript = dotDrawer.toDotScript(bmgGraph);
        System.out.println("The result is ================================================\n\n");
        System.out.println(dotScript);
    }


}
