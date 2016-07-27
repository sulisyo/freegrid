package com.example.annotationprocessor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.example.annotationprocessor.checkFlag.java")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class CustomAnnotationProcessor extends AbstractProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        StringBuilder builder = new StringBuilder()
                .append("package com.example.annotationprocessor.generated;\n\n")
                .append("public class GeneratedClass {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");


        // for each javax.lang.model.element.Element annotated with the CustomAnnotation
        for (Element element : roundEnv.getElementsAnnotatedWith(CustomAnnotation.class)) {
            String objectType = element.getSimpleName().toString();



            // this is appending to the return statement
            builder.append(objectType).append(" says hello!\\n");
        }


        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class



        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.example.annotationprocessor.generated.GeneratedClass");


            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }


        return true;
    }
}
