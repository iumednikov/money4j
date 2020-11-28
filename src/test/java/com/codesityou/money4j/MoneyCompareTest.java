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

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class MoneyCompareTest {

    private final static Currency eur = Currency.of("EUR");
    private final static Currency usd = Currency.of("USD");
    
    @Test
    void isLessThanTest(){
        Money m1 = Money.of(999.99, eur);
        Money m2 = Money.of(1000, eur);
        boolean result = m1.isLessThan(m2);
        Assertions.assertThat(result).isTrue();

        // Money entities should be of the same currency
        Money m3 = Money.of(1000, usd);
        Assertions.assertThatCode(() -> m3.isLessThan(m1))
                .isInstanceOf(CurrenciesDontMatchException.class);
    }

    @Test
    void isGreaterThanTest(){
        Money m1 = Money.of(999.99, eur);
        Money m2 = Money.of(1000, eur);
        boolean result = m2.isGreaterThan(m1);
        Assertions.assertThat(result).isTrue();

        // Money entities should be of the same currency
        Money m3 = Money.of(1000, usd);
        Assertions.assertThatCode(() -> m3.isGreaterThan(m1))
                .isInstanceOf(CurrenciesDontMatchException.class);
    }

    @Test
    void compareToTest(){
        Money m1 = Money.of(1500, eur);
        Money m2 = Money.of(1000, eur);
        Money m3 = Money.of(1000, eur);
        Assertions.assertThat(m1.compareTo(m2)).isEqualTo(1);
        Assertions.assertThat(m2.compareTo(m1)).isEqualTo(-1);
        Assertions.assertThat(m2.compareTo(m3)).isZero();

        // Money entities should be of the same currency
        Money m4 = Money.of(1000, usd);
        Assertions.assertThatCode(() -> m2.compareTo(m4))
                .isInstanceOf(CurrenciesDontMatchException.class);
    }

    @Test
    void equalsTest(){
        Money m1 = Money.of(2000, eur);
        Money m2 = Money.of(2000, eur);
        Money m3 = Money.of(2000, usd);
        Assertions.assertThat(m1).isNotEqualTo(m3);
        Assertions.assertThat(m1).isEqualTo(m2);
    }
}
