package org.beanmodelgraph.drawer.integrationtest.cases;

import lombok.SneakyThrows;
import org.beanmodelgraph.constructor.BeanModelGraphConstructor;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.drawer.BeanModelGraphDrawer;
import org.beanmodelgraph.drawer.renderer.DefaultDotGraphRenderer;
import org.junit.jupiter.api.Test;

import java.util.List;

public abstract class BmgBuildAndDrawITCaseBase { //TODO: this can be pulled up to bmg-test-common


    public abstract Class<?> getRootBeanClass();

    public abstract List<String> getSubTypeScanBasePackages();

    @Test
    @SneakyThrows
    public void buildAndTabulize() {
        BeanModelGraphConstructor graphConstructor = new BeanModelGraphConstructor(getRootBeanClass(),
                getSubTypeScanBasePackages());
        BeanModelGraphDrawer graphDrawer = new BeanModelGraphDrawer(new DefaultDotGraphRenderer());

        BmgNode rootNode = graphConstructor.construct();
        String dotScript = graphDrawer.toDotScript(rootNode);
        System.out.println("The result is ================================================\n\n");
        System.out.println(dotScript);
    }


}
