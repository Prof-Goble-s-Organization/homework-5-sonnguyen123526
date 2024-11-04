package structures.generic;

/**
 * A generic container that can store an object of any type. Compare this class
 * to the ObjectBox class in the linear.object package to see how the same
 * functionality is accomplished using Object references, and the issues that
 * can result.
 * 
 * @author William Goble
 * @author Dickinson College
 * @version Feb 12, 2024
 */

/*
 * The <T> is a "Type Parameter". It will take on a specified type when an
 * object is created. For example, the line:
 * 
 * GenericBox<String> gp = new GenericBox<String>();
 * 
 * results in T taking on the type String.
 */
public class GenericBox<T extends Number> {
    private T value;
    
    public GenericBox(T initValue) {
        value = initValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T newValue) {
        value = newValue;
    }

    public static void main(String[] args) {
        // Create a GenericBox that can hold a String object
        GenericBox<String> string_box = new GenericBox<String>("hello world");

        // Create a GenericBoc that can hold a Double object
        GenericBox<Double> double_box = new GenericBox<Double>(3.14159);

        /*
         * Since the compiler knows what type the generic object operates on 
         * type casts are not required when retrieving values.
         */
        String str = string_box.getValue();
        Double dbl = double_box.getValue();

        System.out.println(String.format("This is from my string box %s", str));
        System.out.println(String.format("This is from my double box: %f", dbl));

        /*
         * Similarly, the compiler is able to detect and flag many type errors
         * making them easier to diagnose and fix.
         * 
         * uncomment to see error
         */
        //string_box.setValue(new Double("3.17"));
        //double_box.setValue("999.99");

        //Double dbl2 = (Double) string_box.getValue();
        //String str2 = (String) dobule_box.getValue()
    }
} 