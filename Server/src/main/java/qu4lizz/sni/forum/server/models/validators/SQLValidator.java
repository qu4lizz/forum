package qu4lizz.sni.forum.server.models.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLValidator implements ConstraintValidator<SQLValid, String> {

    @Override
    public void initialize(SQLValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isSqlInjectionSafe(value);
    }

    private static final String SQL_TYPES =
            "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY" +
                    "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, " +
                    "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR" +
                    "SEQUENCE, RESTORE POINT, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, " +
                    "COLUMN, FIELD, OPERATOR";

    private static final String[] SQL_REGEXPS = {
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+(true|false)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+(\\w)(\\s)*(\\=)(\\s)*(\\w)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+(equals|not equals)(\\s)+(true|false)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\!\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\<\\>)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)(.*)",
            "(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)",
            "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
            "(.*)(/\\*|\\*/|;){1,}(.*)",
            "(.*)(-){2,}(.*)",

    };


    private static final List<Pattern> validationPatterns = buildPatterns();

    public static boolean isSqlInjectionSafe(String dataString){
        if(isEmpty(dataString)){
            return true;
        }

        for(Pattern pattern : validationPatterns){
            if(matches(pattern, dataString)){
                return false;
            }
        }
        return true;
    }

    private static boolean matches(Pattern pattern, String dataString){
        Matcher matcher = pattern.matcher(dataString);
        return matcher.matches();
    }

    private static List<Pattern> buildPatterns(){
        List<Pattern> patterns = new ArrayList<>();
        for(String expression : SQLValidator.SQL_REGEXPS){
            patterns.add(getPattern(expression));
        }
        return patterns;
    }


    private static Pattern getPattern(String regEx){
        return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.isEmpty();
    }
}
