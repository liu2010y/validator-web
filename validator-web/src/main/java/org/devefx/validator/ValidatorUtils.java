/*
 * Copyright 2016-2017, Youqian Yue (devefx@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devefx.validator;

import org.devefx.validator.internal.util.ThreadContext;

public class ValidatorUtils {

    private static Validator validator;

    public static void setValidator(Validator validator) {
        ValidatorUtils.validator = validator;
    }

    public static Validator getValidator() throws UnavailableValidatorException {
        Validator validator = ThreadContext.getValidator();
        if (validator == null) {
            validator = ValidatorUtils.validator;
        }
        if (validator == null) {
            String msg = "No Validator accessible to the calling code, either bound to the " +
                    WebContext.class.getName() + " or as a vm static singleton.  This is an invalid application " +
                    "configuration.";
            throw new UnavailableValidatorException(msg);
        }
        return validator;
    }
}
