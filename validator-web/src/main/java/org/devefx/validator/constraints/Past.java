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

package org.devefx.validator.constraints;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.devefx.validator.ConstraintValidator;
import org.devefx.validator.constraints.annotation.InitParam;
import org.devefx.validator.script.annotation.Script;

@Script
public class Past implements ConstraintValidator {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    private SimpleDateFormat sdf;
    @InitParam
    private String pattern;
    
    public Past() {
        this(DEFAULT_PATTERN);
    }
    
    public Past(String pattern) {
        this.pattern = pattern;
        this.sdf = new SimpleDateFormat(this.pattern);
    }
    
    @Override
    public boolean isValid(Object value) {
        //null values are valid
        if (value == null) {
            return true;
        }
        try {
            Date date = sdf.parse(value.toString());
            return date.before(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

}
