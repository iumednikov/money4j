/**
 * Copyright 2020 Iurii Mednikov @ https://www.iuriimednikov.com  
 * Licensed under the GPL v3 License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the license at:
 * https://www.gnu.org/licenses/gpl-3.0
 * 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codesityou.money4j;

/**
 * An exception, which is thrown by the Currency.of() static factory method,
 * if the supplied three letter currency code is not available
 * 
 * @author Iurii Mednikov
 * @since 0.1
 */
public final class UnknownCurrencyException extends IllegalArgumentException {
    
    UnknownCurrencyException(String code) {
        super("Currency with code " + code + " is unknown for Money4j. "
                + "Please check the list of available currencies.");
    }
}
