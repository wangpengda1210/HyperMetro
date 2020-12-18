import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 Get sorted list of private fields the object declares (inherited fields should be skipped).
 */
class FieldGetter {

    public List<String> getPrivateFields(Object object) {
        // Add implementation here
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()))
                .map(Field::getName)
                .sorted()
                .collect(Collectors.toList());
    }

}