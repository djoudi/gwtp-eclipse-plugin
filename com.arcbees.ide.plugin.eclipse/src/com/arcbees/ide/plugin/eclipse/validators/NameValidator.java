/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.ide.plugin.eclipse.validators;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

/**
 * Name validator.
 * 
 * Validates: Start with capital letter and then letters, spaces and numbers can
 * follow.
 */
public class NameValidator implements IValidator {
    @Override
    public IStatus validate(Object value) {
        if (value instanceof String) {
            String name = ((String) value).trim();
            boolean passes = name.matches("[A-Z][a-zA-Z\0400-9]+");
            if (passes) {
                return ValidationStatus.ok();
            } else {
                String message = "";
                if (name.isEmpty()) {
                    message = "Name is empty.";
                } else if (name.matches("[a-z].*")) {
                    message = "Name must start with a capital letter.";
                } else {
                    message = "Name must start with a capital letter. "
                            + "After the capital, it can contain letters, spaces and numbers.";
                }
                return ValidationStatus.error(message);
            }
        }
        return ValidationStatus.error("Name is not a String.");
    }
}
