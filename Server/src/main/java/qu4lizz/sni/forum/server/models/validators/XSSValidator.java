package qu4lizz.sni.forum.server.models.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class XSSValidator implements ConstraintValidator<XSSValid, String> {

    private static final Pattern XSS_PATTERN = Pattern.compile(
            "(?i)<script>|</script>|eval\\((.*?)\\)|expression\\((.*?)\\)|javascript:|vbscript:",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public void initialize(XSSValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !XSS_PATTERN.matcher(value).find();
    }
}
