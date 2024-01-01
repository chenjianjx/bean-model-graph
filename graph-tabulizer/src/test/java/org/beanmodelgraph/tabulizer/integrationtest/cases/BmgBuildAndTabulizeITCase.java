package org.beanmodelgraph.tabulizer.integrationtest.cases;

import lombok.SneakyThrows;
import org.beanmodelgraph.builder.BeanModelGraphBuilder;
import org.beanmodelgraph.builder.model.BmgNode;
import org.beanmodelgraph.tabulizer.BeanModelGraphTabulizer;
import org.beanmodelgraph.tabulizer.integrationtest.model.BmgRowViewObject;
import org.beanmodelgraph.tabulizer.model.BmgRow;
import org.beanmodelgraph.testcommon.model.A;
import org.junit.jupiter.api.Test;
import org.ssio.api.interfaces.htmltable.HtmlTableSsioTemplate;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.beanmodelgraph.testcommon.support.BmgITHelper.createSpreadsheetFile;

public class BmgBuildAndTabulizeITCase {


    @SneakyThrows
    @Test
    public void buildAndTabulize() {

        BeanModelGraphBuilder graphBuilder = new BeanModelGraphBuilder();
        BeanModelGraphTabulizer graphTabulizer = new BeanModelGraphTabulizer();


        BmgNode rootNode = graphBuilder.buildFromBeanClass(A.class);
        List<BmgRow> rows = graphTabulizer.toRows(rootNode);

        List<BmgRowViewObject> viewObjects = rows.stream().map(BmgRowViewObject::fromBizObject).collect(Collectors.toList());

        File tableFile = createSpreadsheetFile(this.getClass().getSimpleName(), "html");
        try (OutputStream outputStream = Files.newOutputStream(tableFile.toPath())) {
            HtmlTableSsioTemplate.defaultInstance().toHtmlPage(viewObjects, BmgRowViewObject.class, outputStream, "utf8", false);
        }
    }

}