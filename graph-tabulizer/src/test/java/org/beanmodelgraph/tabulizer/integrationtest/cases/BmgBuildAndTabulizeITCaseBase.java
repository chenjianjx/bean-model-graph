package org.beanmodelgraph.tabulizer.integrationtest.cases;

import lombok.SneakyThrows;
import org.beanmodelgraph.constructor.BeanModelGraphConstructor;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.tabulizer.BeanModelGraphTabulizer;
import org.beanmodelgraph.tabulizer.integrationtest.model.BmgRowViewObject;
import org.beanmodelgraph.tabulizer.model.BmgRow;
import org.beanmodelgraph.tabulizer.render.BmgRowPathRenderer;
import org.beanmodelgraph.tabulizer.render.DefaultBmgRowPathRenderer;
import org.junit.jupiter.api.Test;
import org.ssio.api.interfaces.htmltable.HtmlTableSsioTemplate;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.beanmodelgraph.testcommon.support.BmgITHelper.createSpreadsheetFile;

public abstract class BmgBuildAndTabulizeITCaseBase {

    BmgRowPathRenderer rowPathRenderer = new DefaultBmgRowPathRenderer();


    public abstract Class<?> getRootBeanClass();

    public abstract List<String> getSubTypeScanBasePackages();

    @Test
    @SneakyThrows
    public void buildAndTabulize() {
        BeanModelGraphConstructor graphConstructor = new BeanModelGraphConstructor(getRootBeanClass(),
                getSubTypeScanBasePackages(), new HashSet<>());
        BeanModelGraphTabulizer graphTabulizer = new BeanModelGraphTabulizer();

        BmgNode rootNode = graphConstructor.construct();
        List<BmgRow> rows = graphTabulizer.toRows(rootNode);

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
