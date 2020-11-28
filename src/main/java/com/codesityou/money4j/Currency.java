/**
 * Copyright 2020 Iurii Mednikov @ https://www.iuriimednikov.com
 * 
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

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * The class represents an information about the Currency object
 * 
 * @author Iurii Mednikov
 * @since 0.1
 */
public final class Currency implements Serializable {

    private final String code;
    private final int decimalParts;
    private final int factor;
    private final Locale locale;
    
    /**
     * Private constructor, use of() static factory method in order to create a new instance
     * @param code Currency ISO-4217 three letter code
     * @param decimalParts a number of decimal digits
     * @param factor a division factor
     * @param locale a default currency locale
     */
    private Currency(String code, int decimalParts, int factor, Locale locale){
        this.code = code;
        this.decimalParts = decimalParts;
        this.factor = factor;
        this.locale = locale;
    }
    
    /**
     * Returns a currency ISO 4217 three letter code (e.g. EUR, USD etc)
     * @return String representation of code
     */
    public String getCode() {
        return this.code;
    }
    
    /**
     * Returns a currency symbol, used by Money.beautify()
     * @return String
     */
    String getSymbol(){
        return this.code;
    }
    
    /**
     * Returns a number of decimal digits. Used by Money class
     * @return number of decimal digits
     */
    int getDecimalParts(){
        return this.decimalParts;
    }
    
    /**
     * Returns a currency multiplication factor. Used by Money class
     * @return factor
     */
    int getFactor(){
        return this.factor;
    }
    
    /**
     * Returns a currency NumberFormat. Used by Money.beautify()
     * @return Number format
     */
    NumberFormat getCurrencyFormat() {
        NumberFormat nf = NumberFormat.getInstance(this.locale);
        nf.setMinimumFractionDigits(this.decimalParts);
        nf.setMaximumFractionDigits(this.decimalParts);
        return nf;
    }
    
    /**
     * A static factory method which creates a new instance of the Currency class with
     * the ISO-4217 currency code. Refer to https://www.iban.com/currency-codes.
     * 
     * Warning! Not all currency codes are yet supported by the library!
     * 
     * @param currencyCode Three letter upper case ISO-4217 code (e.g. EUR, USD)
     * @return a new Currency instance
     * @throws UnknownCurrencyException if the currency code is not registered
     */
    public static Currency of(String currencyCode) throws UnknownCurrencyException {
        switch (currencyCode){
            case "EUR":
                return new Currency("EUR", 2, 100, Locale.GERMANY);
            case "USD":
                return new Currency("USD", 2, 100, Locale.US);
            case "GBP":
                return new Currency("GBP", 2, 100, Locale.UK);
            default:
                throw new UnknownCurrencyException(currencyCode);
        }        
    }
    
    /**
     * Checks if two Currency objects are same. 
     * Two Currency objects are same, if they have same currency code
     * 
     * @param currency other currency object to compare
     * @return true if both are same
     */
    public boolean isSameCurrency (Currency currency){
        return this.code.equalsIgnoreCase(currency.getCode());
    }

    /**
     * An overriden version of the equals() method.
     * a == b if a.isSameCurrency(b)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Currency == false) return false;
        Currency c = (Currency) obj;
        return this.isSameCurrency(c);
    }
}
