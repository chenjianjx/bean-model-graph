package org.beanmodelgraph.tabulizer.integrationtest.cases;

import lombok.SneakyThrows;
import org.beanmodelgraph.constructor.BeanModelGraphConstructor;
import org.beanmodelgraph.constructor.model.BmgGraph;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.tabulizer.BeanModelGraphTabulizer;
import org.beanmodelgraph.tabulizer.integrationtest.model.BmgRowViewObject;
import org.beanmodelgraph.tabulizer.model.BmgRow;
import org.beanmodelgraph.tabulizer.render.BmgRowPathRenderer;
import org.beanmodelgraph.tabulizer.render.DefaultBmgRowPathRenderer;
import org.beanmodelgraph.testcommon.testdata.ConstructorTestParam;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ssio.api.interfaces.htmltable.HtmlTableSsioTemplate;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.beanmodelgraph.testcommon.support.BmgITHelper.createSpreadsheetFile;

public class BmgBuildAndTabulizeITCase {

    BmgRowPathRenderer rowPathRenderer = new DefaultBmgRowPathRenderer();


    static Stream<Arguments> paramProvider() {
        return Stream.of(
                Arguments.of(ConstructorTestParam.AbcTestParam),
                Arguments.of(ConstructorTestParam.PaypalTestParam)
        );
    }

    @ParameterizedTest
    @MethodSource("paramProvider")
    @SneakyThrows
    public void buildAndTabulize(ConstructorTestParam param) {
        BeanModelGraphConstructor graphConstructor = new BeanModelGraphConstructor(param.getRootBeanClass(),
                param.getSubTypeScanBasePackages(), param.getAdditionalAtomicTypes());
        BeanModelGraphTabulizer graphTabulizer = new BeanModelGraphTabulizer();

        BmgGraph bmgGraph = graphConstructor.construct();
        List<BmgRow> rows = graphTabulizer.toRows(bmgGraph);

        List<BmgRowViewObject> viewObjects = rows.stream().map(this::fromBizObject).collect(Collectors.toList());

        File tableFile = createSpreadsheetFile(this.getClass().getSimpleName(), "html");
        try (OutputStream outputStream = Files.newOutputStream(tableFile.toPath())) {
            HtmlTableSsioTemplate.defaultInstance().toHtmlPage(viewObjects, BmgRowViewObject.class, outputStream, "utf8", false);
        }
    }


    private BmgRowViewObject fromBizObject(BmgRow bo) {
        return BmgRowViewObject.builder()
                .propertyPath(rowPathRenderer.render(bo.getPath()))
                .typeSimpleName(bo.getType().getSimpleName()).build();
    }

}
