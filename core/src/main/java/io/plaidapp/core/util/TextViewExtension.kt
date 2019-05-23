/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.plaidapp.core.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * Add an action which will be invoked before the text changed
 */
fun TextView.doBeforeTextChanged(
    action: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit
) {
    addTextChangedListener(action, null, null)
}

/**
 * Add an action which will be invoked when the text is changing
 */
fun TextView.doOnTextChanged(
    action: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit
) {
    addTextChangedListener(null, action, null)
}

/**
 * Add an action which will be invoked after the text changed
 */
fun TextView.doAfterTextChanged(
    action: (text: Editable?) -> Unit
) {
    addTextChangedListener(null, null, action)
}

/**
 * Add a text changed listener to this TextView using the provided actions
 */
fun TextView.addTextChangedListener(
    beforeChanged: ((
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit)? = null,
    onChanged: ((
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit)? = null,
    afterChanged: ((text: Editable?) -> Unit)? = null
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (afterChanged != null) {
                afterChanged(s)
            }
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            if (beforeChanged != null) {
                beforeChanged(text, start, count, after)
            }
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if (onChanged != null) {
                onChanged(text, start, before, count)
            }
        }
    })
}
