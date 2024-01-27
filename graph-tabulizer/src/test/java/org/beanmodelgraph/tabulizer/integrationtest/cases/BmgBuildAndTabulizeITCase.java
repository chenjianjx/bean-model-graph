package org.beanmodelgraph.tabulizer.integrationtest.cases;

import lombok.SneakyThrows;
import org.beanmodelgraph.constructor.BeanModelGraphConstructor;
import org.beanmodelgraph.constructor.model.BmgGraph;
import org.beanmodelgraph.tabulizer.BeanModelGraphTabulizer;
import org.beanmodelgraph.tabulizer.integrationtest.model.BmgRowViewObject;
import org.beanmodelgraph.tabulizer.model.BmgRow;
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

import static org.beanmodelgraph.testcommon.support.BmgITHelper.createResultFile;

class BmgBuildAndTabulizeITCase {

    static Stream<Arguments> paramProvider() {
        return Stream.of(
                Arguments.of(ConstructorTestParam.AbcTestParam),
                Arguments.of(ConstructorTestParam.PaypalTestParam)
        );
    }

    @ParameterizedTest
    @MethodSource("paramProvider")
    @SneakyThrows
    void buildAndTabulize(ConstructorTestParam param) {
        BeanModelGraphConstructor graphConstructor = new BeanModelGraphConstructor(param.getRootBeanClass(),
                param.getSubTypeScanBasePackages(), param.getAdditionalAtomicTypes());
        BeanModelGraphTabulizer graphTabulizer = new BeanModelGraphTabulizer();

        BmgGraph bmgGraph = graphConstructor.construct();
        List<BmgRow> rows = graphTabulizer.toRows(bmgGraph);

        List<BmgRowViewObject> viewObjects = rows.stream().map(BmgRowViewObject::fromBmgRow).collect(Collectors.toList());

        File tableFile = createResultFile(this.getClass().getSimpleName(), "html");
        try (OutputStream outputStream = Files.newOutputStream(tableFile.toPath())) {
            HtmlTableSsioTemplate.defaultInstance().toHtmlPage(viewObjects, BmgRowViewObject.class, outputStream, "utf8", false);
        }
    }




}
