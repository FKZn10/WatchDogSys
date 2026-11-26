import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.projectProducer.annotations.ClassInfo;
import org.assertj.core.internal.bytebuddy.description.annotation.AnnotationDescription;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

public class TestDemo {
    @Test
    public void test() throws ClassNotFoundException {
        Field[] fields = Class.forName(PreProjectRequest.class.getName()).getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(annotation);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }

            }
        }


    }
}
