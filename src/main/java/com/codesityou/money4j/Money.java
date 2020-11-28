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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

/**
 * The class specifies a Money object, and contains information,
 * about currency and value.
 * 
 * The value is stored in minor currency units (e.g. cents, but an actual name depends on the currency itself).
 * The class is immutable - arithmetic operations return new instance.
 * 
 * @author Iurii Mednikov
 * @since 0.1
 */
public final class Money implements Comparable<Money>, Serializable {
    
    private final BigInteger value;
    private final Currency currency;
    
    /**
     * Private constructor. Instead, use static factory methods of() and zero()
     * 
     * @param value
     * @param currency
     */
    private Money(BigInteger value, Currency currency){
        this.value = value;
        this.currency = currency;
    }
    
    /**
     * The static factory method, which creates a new instance of Money object, using currency and BigDecimal value
     * 
     * @param bdValue BigDecimal instance, which represents a numeric value
     * @param currency Currency object
     * @return new Money instance
     */
    public static Money of (BigDecimal bdValue, Currency currency){
        int factor = currency.getFactor();
        BigInteger value = bdValue.multiply(BigDecimal.valueOf(factor)).toBigInteger();
        return new Money(value, currency);
    }
    
    /**
     * The static factory method, which creates a new instance of Money object, using currency and double value.
     * The actual double value is converted to BigDecimal.
     * 
     * @param dValue Double, which represents a numeric value 
     * @param currency Currency object
     * @return new Money instance
     */
    public static Money of (double dValue, Currency currency){
        return of(BigDecimal.valueOf(dValue), currency);
    }
    
    /**
     * The static factory method, which creates a new Instance with zero numeric value (e.g. EUR 0)
     * 
     * @param currency Currency object
     * @return new Money instance
     */
    public static Money zero (Currency currency){
        BigInteger value = BigInteger.ZERO;
        return new Money(value, currency);
    }
    
    /**
     * This method, checks if two Money instances belong to the same currency
     * 
     * @param other The other Money instance to compare with
     * @return true if both instances are of the same currency, false if currencies are different
     */
    public boolean isSameCurrency (Money other){
        return this.currency.isSameCurrency(other.getCurrency());
    }
    
    /**
     * Adds two Money instances and returns a new instance, which contains a sum of both.
     * Both Money objects should have same currency, otherwise an exception will be thrown
     * 
     * @param other The other Money instance
     * @return a new Money object, which represents a sum of values of two Money instances
     * @throws CurrenciesDontMatchException if other.currency != this.currency
     */
    public Money plus(Money other) throws CurrenciesDontMatchException{
        if (!this.isSameCurrency(other)) throw new CurrenciesDontMatchException();
        BigInteger result = this.value.add(other.value);
        return new Money(result, this.currency);
    }
    
    /**
     * Adds two Money instances and returns a new instance, which contains a difference of both.
     * Both Money objects should have same currency, otherwise an exception will be thrown
     * 
     * @param other The other Money instance
     * @return a new Money object, which represents a difference of values of two Money instances
     * @throws CurrenciesDontMatchException if other.currency != this.currency
     */
    public Money minus (Money other) throws CurrenciesDontMatchException{
        if (!this.isSameCurrency(other)) throw new CurrenciesDontMatchException();
        BigInteger result = this.value.subtract(other.value);
        return new Money(result, this.currency);
    }
    
    /**
     * Divides the value of the given Money object by the long value
     * @param ln Long value, which represents a divider
     * @return a new Money object, which represents a result of a division
     * @throws IllegalArgumentException if a long value is 0
     */
    public Money divide (long ln) {
        if (ln == 0) throw new IllegalArgumentException();
        BigInteger result = this.value.divide(BigInteger.valueOf(ln));
        return new Money(result, this.currency);
    }
    
    /**
     * Multiplies the value of the given Money object by the long value
     * @param ln Long value, which represents a multiplier
     * @return a new Money object, which represents a result of multiplication
     */
    public Money multiply (long ln){
        BigInteger result = this.value.multiply(BigInteger.valueOf(ln));
        return new Money(result, this.currency);
    }
    
    /**
     * Checks if the given Money object is less than the argument
     * @param other Money object to compare with
     * @return true if the given Money object is less than Other, false if not
     * @throws CurrenciesDontMatchException if Other has a different currency
     */
    public boolean isLessThan (Money other) throws CurrenciesDontMatchException{
        if (!this.currency.isSameCurrency(other.getCurrency())) throw new CurrenciesDontMatchException();
        return this.value.compareTo(other.value) == -1;
    }
    
    /**
     * Checks if the given Money object is greater than the argument
     * @param other Money object to compare with
     * @return true if the given Money object is greater than Other, false if not
     * @throws CurrenciesDontMatchException if Other has a different currency
     */
    public boolean isGreaterThan(Money other) throws CurrenciesDontMatchException{
        if (!this.currency.isSameCurrency(other.getCurrency())) throw new CurrenciesDontMatchException();
        return this.value.compareTo(other.value) == 1;
    }
    
    /**
     * Returns a String representation of the Money object. The output is "pretty" formatted,
     * using the locale rules of the currency. 
     * The expected format is CCC XXX.DD, where
     * - CCC - ISO currency name (e.g. EUR, USD etc)
     * - XXX - major currency units (separated, if required)
     * - DD - minor currency units (if exist)
     * 
     * Example output of Money.of(1000, Currency.of("EUR")) is "EUR 1000.00"
     * 
     * @return a "beautifed" String representation of the Money object
     */
    public String beautify() {
        String symbol = this.currency.getSymbol();
        BigDecimal bd = this.toBigDecimal();
        NumberFormat nf = this.currency.getCurrencyFormat();
        String result = symbol + " " + nf.format(bd);
        return result;
    }
    
    /**
     * Returns the currency value of the given Money object
     * @return currency value
     */
    public Currency getCurrency(){
        return this.currency;
    }
    
    /**
     * Returns if the given Money object has a negative numeric value
     * @return true if the value is negative, false if the value if positive
     */
    public boolean isNegative(){
        return this.value.signum() == -1;
    }
    
    /**
     * Returns a BigDecimal representation of the value
     * @return BigDecimal value
     */
    public BigDecimal toBigDecimal() {
        if (this.value.intValue() == 0) return BigDecimal.ZERO;
        BigDecimal val = new BigDecimal(this.value);
        BigDecimal div = val.divide(BigDecimal.valueOf(this.currency.getFactor()));
        return div;
    }
    
    /**
     * Overriden version of compareTo() method. Checks values, if both instances have same currency
     * More information here: https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
     * 
     * @throws CurrenciesDontMatchException if Money objects have different currency values
     */
    @Override
    public int compareTo(Money other) throws CurrenciesDontMatchException{
        if (!this.currency.isSameCurrency(other.getCurrency())) throw new CurrenciesDontMatchException();
        BigInteger otherValue = other.value;
        return this.value.compareTo(otherValue);
    }

    /**
     * Overriden version of equals() method
     * 
     * Two money objects are equal if:
     * 1. Have same currency
     * 2. Have equal numeric value
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Money == false) return false;
        Money m = (Money) obj;
        return m.value.equals(this.value) && m.getCurrency().equals(this.currency);
    }
    
}
