package cz.kodytek.shop.domain.common.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named
@ApplicationScoped
public class ArrayUtil {

    public List<Integer> createRange(int from, int to) {
        System.out.println("Creating range from " + from + " to " + to);
        return IntStream.range(from, to).boxed().collect(Collectors.toList());
    }

}
