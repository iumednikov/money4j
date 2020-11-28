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

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class MoneyArithmeticsTest {
    
    private static final Currency currency = Currency.of("EUR");
    
    @Test
    void plus_addTwoEntitiesWithDecimal_test(){
        Money m1 = Money.of(100.32, currency);
        Money m2 = Money.of(250.99, currency);
        Money result = m1.plus(m2);
        Assertions.assertThat(result.toBigDecimal()).isEqualByComparingTo(new BigDecimal("351.31"));
    }
    
    @Test
    void plus_addDecimalAndNonDecimal_test(){
        Money m1 = Money.of(452, currency);
        Money m2 = Money.of(119.60, currency);
        Money result = m1.plus(m2);
        Assertions.assertThat(result.toBigDecimal()).isEqualByComparingTo(new BigDecimal("571.60"));
    }
    
    @Test
    void plus_addDifferentCurrencies_test(){
        Currency usd = Currency.of("USD");
        Money moneyUsd = Money.of(1200.00, usd);
        Money moneyEur = Money.of(3450.99, currency);
        Assertions.assertThatCode(() -> moneyUsd.plus(moneyEur))
                .isInstanceOf(CurrenciesDontMatchException.class);
    }
    
    @Test
    void minus_subtractTwoEntitiesWithDecimal_test(){
        Money m1 = Money.of(5000.39, currency);
        Money m2 = Money.of(424.70, currency);
        Money result = m1.minus(m2);
        Assertions.assertThat(result.toBigDecimal()).isEqualByComparingTo(new BigDecimal("4575.69"));
    }
    
    @Test
    void minus_subtractDecimalAndNonDecimal_test(){
        Money m1 = Money.of(4950, currency);
        Money m2 = Money.of(823.45, currency);
        Money result = m1.minus(m2);
        Assertions.assertThat(result.toBigDecimal()).isEqualByComparingTo(new BigDecimal("4126.55"));
    }
    
    @Test
    void minus_subtractDifferentCurrencies_test(){
        Currency usd = Currency.of("USD");
        Money moneyUsd = Money.of(800, usd);
        Money moneyEur = Money.of(500, currency);
        Assertions.assertThatCode(() -> moneyUsd.plus(moneyEur))
                .isInstanceOf(CurrenciesDontMatchException.class);
    }
    
    @Test
    void minus_subtractWithNegativeResult_test(){
        Money m1 = Money.of(100, currency);
        Money m2 = Money.of(200, currency);
        Money result = m1.minus(m2);
        BigDecimal value = result.toBigDecimal();
        Assertions.assertThat(value).isNegative();
    }

    @Test
    void divide_test(){
        Money m = Money.of(481.73, currency);
        long factor = 16;
        Money result = m.multiply(factor);
        Assertions.assertThat(result.toBigDecimal()).isEqualByComparingTo(new BigDecimal("7707.68"));
    }

    @Test
    void multiply_test(){
        Money m = Money.of(100, currency);
        long factor = 8;
        Money result = m.divide(factor);
        Assertions.assertThat(result.toBigDecimal()).isEqualByComparingTo(new BigDecimal("12.50"));
    }
}
