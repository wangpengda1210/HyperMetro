import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 Get list of public fields the object declares (inherited fields should be skipped).
 */
class FieldGetter {

    public String[] getPublicFields(Object object) {
        // Add implementation here
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .map(Field::getName)
                .toArray(String[]::new);
    }

}