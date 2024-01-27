package org.beanmodelgraph.drawer.integrationtest.dot.cases;

import forDoc.model.One;
import forDoc.model.OneTwoThreePackageAnchor;
import lombok.SneakyThrows;
import org.beanmodelgraph.constructor.BeanModelGraphConstructor;
import org.beanmodelgraph.constructor.model.BmgGraph;
import org.beanmodelgraph.drawer.dot.BeanModelGraphDotDrawer;
import org.beanmodelgraph.drawer.dot.render.impl.DefaultDotGraphRenderer;
import org.beanmodelgraph.testcommon.testdata.ConstructorTestParam;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.beanmodelgraph.testcommon.support.BmgITHelper.createSpreadsheetFile;

public class BmgBuildAndDotDrawITCase {

    BeanModelGraphDotDrawer dotDrawer = new BeanModelGraphDotDrawer(new DefaultDotGraphRenderer());


    static Stream<Arguments> paramProvider() {
        return Stream.of(
                Arguments.of(ConstructorTestParam.AbcTestParam),
                Arguments.of(ConstructorTestParam.PaypalTestParam),
                Arguments.of(ConstructorTestParam.builder()
                        .rootBeanClass(One.class)
                        .subTypeScanBasePackages(Arrays.asList(OneTwoThreePackageAnchor.class.getPackage().getName()))
                        .additionalAtomicTypes(new HashSet<>()).build())
        );
    }


    @ParameterizedTest
    @MethodSource("paramProvider")
    @SneakyThrows
    public void buildAndDotDraw(ConstructorTestParam param) {
        BeanModelGraphConstructor graphConstructor = new BeanModelGraphConstructor(param.getRootBeanClass(),
                param.getSubTypeScanBasePackages(), param.getAdditionalAtomicTypes());


        BmgGraph bmgGraph = graphConstructor.construct();
        String dotScript = dotDrawer.toDotScript(bmgGraph);

        File dotFile = createSpreadsheetFile(this.getClass().getSimpleName(), "dot");
        Files.write(dotFile.toPath(), dotScript.getBytes("utf8"));
    }

}
