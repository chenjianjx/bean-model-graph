package org.beanmodelgraph.testcommon.model.parent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Beta {
    private String someString;
}
