import org.jdom2.Element;
import util.ElementUtils;

public class Main {

    public static void main(String[] args) {
        String originPath = args[0];
        String findInPath = args[1];

        int complexity = 1;
        if (args.length == 3) {
            complexity = Integer.parseInt(args[2]);
        }

        Element elementById = ElementUtils.getElementById(originPath, "make-everything-ok-button");

        Element byFilter = ElementUtils.getElementByAttributeFilters(findInPath, elementById.getAttributes(), complexity);

        System.out.println(ElementUtils.getElementPath(byFilter));
    }


}
