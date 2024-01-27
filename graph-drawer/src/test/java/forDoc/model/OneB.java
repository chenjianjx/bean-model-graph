package forDoc.model;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class OneB implements One {
    private List<Integer> someInts;
}
