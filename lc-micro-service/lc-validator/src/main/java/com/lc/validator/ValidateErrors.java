package com.lc.validator;


import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全局参数校验异常信息
 *
 * @author liucheng
 * @since 2019-12-15
 */
@SuppressWarnings("unused")
@Data
@NoArgsConstructor
public class ValidateErrors implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全局错误
     */
    private List<String> globalErrors;

    /**
     * 字段错误
     */
    private Map<String, String> fieldErrors;

    public static Builder builder() {
        return new Builder ();
    }

    public static class Builder {
        private Errors errors;

        private List<String> globalErrors;

        private Map<String, String> fieldErrors;

        public Builder errors(Errors errors) {
            this.errors = errors;
            return this;
        }

        public Builder globalErrors(List<String> globalErrors) {
            this.globalErrors = globalErrors;
            return this;
        }

        public Builder fieldErrors(Map<String, String> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }

        public ValidateErrors build() {
            ValidateErrors validateErrors = new ValidateErrors ();

            if (errors != null) {
                if (errors.hasGlobalErrors ()) {
                    validateErrors.setGlobalErrors (new ArrayList<> (errors.getGlobalErrors ().size ()));
                    for (ObjectError error : errors.getGlobalErrors ()) {
                        String message = error.getDefaultMessage ();
                        validateErrors.getGlobalErrors ().add (message);
                    }
                }

                if (errors.hasFieldErrors ()) {
                    validateErrors.setFieldErrors (Maps.newHashMap ());
                    for (FieldError error : errors.getFieldErrors ()) {
                        String message = error.getDefaultMessage ();
                        validateErrors.getFieldErrors ().put (error.getField (), message);
                    }
                }
            }

            if (fieldErrors != null) {
                validateErrors.fieldErrors = fieldErrors;
            }

            if (globalErrors != null) {
                validateErrors.globalErrors = globalErrors;
            }

            return validateErrors;
        }

    }

}