package sankaku.app.schedule;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


//javadoc コマンドなどで作成したドキュメントに反映させる為の設定
@Documented
//バリデーションを行うクラスを指定
@Constraint(validatedBy = {NotEmptyAnyValidator.class})
//アノテーションで付加された情報がどの段階まで保持されるかを定義
@Retention(RetentionPolicy.RUNTIME)
//アノテーションを付与できる対象を指定
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface NotEmptyAny {

    //エラー時に例外オブジェクトに設定されるメッセージ
    String message() default "Not Empty Any!!";


    //特定のバリデーショングループをカスタマイズ可能にする設定（※空の Class<?> 型で初期化）
    Class<?>[] groups() default {};
    //チェック対象のオブジェクトにメタ情報を与える為の宣言
    Class<? extends Payload>[] payload()  default {};

    @Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List{

    	NotEmptyAny[]value();
    }

}
